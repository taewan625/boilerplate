package com.exporum.admin.domain.exhibitor.service;

import com.exporum.admin.common.service.CommonService;
import com.exporum.admin.domain.attendee.model.AttendeeExcel;
import com.exporum.admin.domain.exhibition.model.InvitationSetting;
import com.exporum.admin.domain.exhibition.service.InvitationSettingService;
import com.exporum.admin.domain.exhibitor.mapper.AddInvitationMapper;
import com.exporum.admin.domain.exhibitor.mapper.ExhibitorManageMapper;
import com.exporum.admin.domain.exhibitor.mapper.InvitationSendMapper;
import com.exporum.admin.domain.exhibitor.model.*;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.excel.ExcelGenerator;
import com.exporum.core.domain.excel.OneSheetExcelGenerator;
import com.exporum.core.domain.mail.service.NCPMailer;
import com.exporum.core.domain.mail.template.InvitationTemplate;
import com.exporum.core.domain.mail.template.RemindInvitationTemplate;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.enums.InvitationType;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class ExhibitorManageService {

    private final ExhibitorManageMapper exhibitorManageMapper;

    private final AddInvitationMapper addInvitationMapper;

    private final InvitationSendMapper invitationSendMapper;

    private final InvitationSettingService invitationSettingService;

    private final CommonService commonService;

    private final StorageService storageService;

    @Value("${ncp.object-storage.path.badge}")
    private String badgePath;

    @Value("${resource.storage.url}")
    private String storageUrl;


    public void remindInvitation() {

        List<InvitationDTO> invitations = invitationSendMapper.getRemindInvitation();


        for(InvitationDTO invitation : invitations){
            invitation.setBarcodePath(storageUrl+invitation.getBarcodePath());
            RemindInvitationTemplate remindInvitationTemplate = this.getRemindInvitationTemplate(invitation);


            NCPMailer.getInstance().enqueueMail(remindInvitationTemplate);
        }

    }


    public void getExhibitorList(PageableExhibitor pageable) {
        long recordsTotal = exhibitorManageMapper.getExhibitorCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        List<ExhibitorList> list = exhibitorManageMapper.getExhibitorList(pageable);

        for(ExhibitorList exhibitor : list){
            exhibitor.setInvitation(this.getInvitations(exhibitor.getId(),exhibitor.getExhibitionId(), exhibitor.getBoothCount()));
        }

        pageable.setData(list);

    }

    public void getInvitationList(PageableInvitation pageable) {
        long recordsTotal = invitationSendMapper.getInvitationCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(invitationSendMapper.getInvitationList(pageable));
    }


    public ResponseEntity<StreamingResponseBody> excelDownload(long exhibitionId, HttpServletResponse response) throws IOException {

        List<ExhibitorExcel> data = exhibitorManageMapper.getExhibitorExcel(exhibitionId);


        for(ExhibitorExcel exhibitorExcel : data){
            InvitationCount invitationCount = this.getInvitations(exhibitorExcel.getId(), exhibitionId, exhibitorExcel.getBoothCount());

            exhibitorExcel.setSendBadgeCount(invitationCount.getSendBadgeCount());
            exhibitorExcel.setSendInvitationCount(invitationCount.getSendInvitationCount());
            exhibitorExcel.setBadgeCount(invitationCount.getBadgeCount());
            exhibitorExcel.setInvitationCount(invitationCount.getInvitationCount());

        }


        ExcelGenerator<ExhibitorExcel> excelFile = new OneSheetExcelGenerator<>(data, ExhibitorExcel.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "exhibitor.xlsx")
                .body(responseBody);
    }

    public ResponseEntity<StreamingResponseBody> invitationExcelDownload(long exhibitionId, HttpServletResponse response) throws IOException {

        List<InvitationExcel> data = invitationSendMapper.invitationExcel(exhibitionId);

        ExcelGenerator<InvitationExcel> excelFile = new OneSheetExcelGenerator<>(data, InvitationExcel.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "invitation.xlsx")
                .body(responseBody);
    }



//    public void invitationExcelDownload(long exhibitionId, HttpServletResponse response) throws IOException {
//
//        List<InvitationExcel> data = invitationSendMapper.invitationExcel(exhibitionId);
//
//        ExcelGenerator<InvitationExcel> excelFile = new OneSheetExcelGenerator<>(data, InvitationExcel.class);
//
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''invitation.xlsx");
//
//        try (OutputStream out = response.getOutputStream()) {
//            excelFile.write(out);
//        } catch (IOException e) {
//            throw new UncheckedIOException("Excel 다운로드 중 오류 발생", e);
//        }
//    }



    public Exhibitor getExhibitor(long id){
        Exhibitor exhibitor = Optional.ofNullable(exhibitorManageMapper.getExhibitor(id)).orElseThrow(DataNotFoundException::new);
        exhibitor.setInvitation(this.getInvitations(exhibitor.getId(),exhibitor.getExhibitionId(), exhibitor.getBoothCount()));
        return exhibitor;
    }






    @Transactional
    public void sendInvitationProcess(long id, List<InvitationDTO> invitations) {

        Exhibitor exhibitor = this.getExhibitor(id);

        InvitationCount invitationCount = exhibitor.getInvitation();

        for (InvitationDTO invitation : invitations) {
            long adminId = AuthenticationHelper.getAuthenticationUserId();
            String barcode;

            do{
               barcode = commonService.getBarcodeNumber();
            }while (invitationSendMapper.isBarcodeExists(barcode));

            File barcodeFile = commonService.getBarcode(barcode);
            FileDTO file = storageService.ncpUpload(barcodeFile, badgePath);

            invitation.setAdminId(adminId);
            invitation.setFileId(file.getId());
            invitation.setBarcode(barcode);

            this.insertInvitation(id, invitation);

            invitation.setBarcodePath(storageUrl+file.getFilePath());
        }


        for (InvitationDTO invitation : invitations) {
            InvitationTemplate invitationTemplate = this.getInvitationTemplate(invitation, invitationCount);
            sendInvitationMail(invitationTemplate);
        }

    }

    @Transactional
    public void updateInvitation(long id, InvitationDTO invitation) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        invitation.setAdminId(adminId);

        if(!(invitationSendMapper.updateInvitation(id,invitation)>0)){
            throw new OperationFailException("failed to update invitation");
        }
    }

    private void insertInvitation(long id, InvitationDTO invitation){
        if (!(invitationSendMapper.insertInvitation(id, invitation) > 0)) {
            throw new OperationFailException("failed to insert invitation");
        }
    }


    private InvitationCount getInvitations(long id, long exhibitionId, int boothCount) {
        List<InvitationSetting> invitationSettings = invitationSettingService.getInvitationSetting(exhibitionId);
        AddInvitation addInvitation = Optional.ofNullable(addInvitationMapper.getTotalAddInvitation(id)).orElse(new AddInvitation());
        List<SendInvitation> sendInvitations = invitationSendMapper.getSendInvitationCount(id);


        int badgeCount = addInvitation.getBadgeAddCount();
        int invitationCount = addInvitation.getInvitationAddCount();

        int sendBadgeCount = 0;
        int sendInvitationCount = 0;


        for(SendInvitation sendInvitation: sendInvitations){

            if(sendInvitation.getType().equals(InvitationType.BADGE.getType())){
                sendBadgeCount = sendInvitation.getCount();
            }

            if(sendInvitation.getType().equals(InvitationType.INVITATION.getType())){
                sendInvitationCount = sendInvitation.getCount();
            }
        }

        for (InvitationSetting invitationSetting : invitationSettings) {

            int maxBoothSize = invitationSetting.getMaxBoothSize();

            if (maxBoothSize == 0) {
                maxBoothSize = 999;
            }

            if((boothCount >= invitationSetting.getMinBoothSize())
                    && (boothCount <= maxBoothSize)){

                badgeCount += invitationSetting.getBadgeCount();
                invitationCount += invitationSetting.getInvitationCount();

            }
        }

        return InvitationCount.builder()
                .sendBadgeCount(sendBadgeCount)
                .badgeCount(badgeCount)
                .sendInvitationCount(sendInvitationCount)
                .invitationCount(invitationCount)
                .build();
    }


    @Transactional
    public void insertProcess(ExhibitorDTO exhibitor) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        exhibitor.setAdminId(adminId);
        this.insertExhibitor(exhibitor);
    }

    @Transactional
    public void updateProcess(long id, ExhibitorDTO exhibitor) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        exhibitor.setAdminId(adminId);
        this.updateExhibitor(id, exhibitor);
    }


    @Transactional
    public void deleteExhibitor(long id) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        if(!(exhibitorManageMapper.deleteExhibitor(id, adminId)>0)){
            throw new OperationFailException();
        }
    }


    private void insertExhibitor(ExhibitorDTO exhibitor) {
        if(!(exhibitorManageMapper.createExhibitor(exhibitor)>0)){
            throw new OperationFailException();
        }
    }
    private void updateExhibitor(long id, ExhibitorDTO exhibitor) {
        if(!(exhibitorManageMapper.updateExhibitor(id, exhibitor)>0)){
            throw new OperationFailException();
        }
    }

    @Transactional
    public void addInvitation(AddInvitationDTO addInvitation) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        addInvitation.setAdminId(adminId);

        if(!(addInvitationMapper.addInvitation(addInvitation)>0)){
            throw new OperationFailException();
        }
    }

    @Transactional
    public void deleteInvitation(long id) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        if(!(addInvitationMapper.deleteAddInvitation(id, adminId)>0)){
            throw new OperationFailException();
        }
    }

    @Transactional
    public void updateBooth(long id, ExhibitorDTO exhibitor) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        exhibitor.setAdminId(adminId);
        if(!(exhibitorManageMapper.updateBooth(id,exhibitor)>0)){
            throw new OperationFailException();
        }
    }

    public List<AddInvitation> getAddInvitationList(long exhibitorId) {
        return addInvitationMapper.getAddInvitation(exhibitorId);
    }

    private InvitationTemplate getInvitationTemplate(InvitationDTO invitation, InvitationCount invitationCount) {

        if(invitation.getType().equals(InvitationType.BADGE.getType())){
            invitationCount.setSendBadgeCount(invitationCount.getSendBadgeCount()+1);
        }

        String count = STR."(\{invitationCount.getSendBadgeCount()}/\{invitationCount.getBadgeCount()})";

        return InvitationTemplate.builder()
                .count(count)
                .barcodePath(invitation.getBarcodePath())
                .mailType(invitation.getType())
                .email(invitation.getEmail())
                .name(invitation.getName())
                .company(invitation.getCompany())
                .jobTitle(invitation.getJobTitle())
                .country(invitation.getCountry())
                .city(invitation.getCity())
                .phoneNumber(invitation.getPhoneNumber())
                .receiver(invitation.getEmail())
                .build();

    }

    private RemindInvitationTemplate getRemindInvitationTemplate(InvitationDTO invitation) {

        return RemindInvitationTemplate.builder()
                .barcode(invitation.getBarcode())
                .barcodePath(invitation.getBarcodePath())
                .mailType(invitation.getType())
                .email(invitation.getEmail())
                .name(invitation.getName())
                .company(invitation.getCompany())
                .jobTitle(invitation.getJobTitle())
                .country(invitation.getCountry())
                .city(invitation.getCity())
                .phoneNumber(invitation.getPhoneNumber())
                .receiver(invitation.getEmail())
                .build();

    }

    private void sendInvitationMail(InvitationTemplate invitationTemplate){
        NCPMailer.getInstance().enqueueMail(invitationTemplate);
    }
}

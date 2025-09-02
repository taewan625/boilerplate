package com.exporum.admin.domain.exhibitor.controller;

import com.exporum.admin.domain.exhibitor.model.*;
import com.exporum.admin.domain.exhibitor.service.ExhibitorManageService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class ExhibitorManageRestController {

    private final ExhibitorManageService exhibitorManageService;

    @PostMapping("/exhibitor")
    public PageableExhibitor getExhibitors(PageableExhibitor search) {
        exhibitorManageService.getExhibitorList(search);
        return search;
    }

    @PostMapping("/exhibitor/add-invitation/{exhibitorId}")
    public ResponseEntity<OperationResponse> getAddInvitationList(@PathVariable long exhibitorId) {
        List<AddInvitation> result = exhibitorManageService.getAddInvitationList(exhibitorId);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, result));
    }


    @GetMapping("/exhibitor/{id}")
    public ResponseEntity<OperationResponse> getExhibitor(@PathVariable long id) {
        Exhibitor exhibitor = exhibitorManageService.getExhibitor(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, exhibitor));
    }

    @PostMapping("/exhibitor/register")
    public ResponseEntity<OperationResponse> createExhibitor(@RequestBody @Valid ExhibitorDTO exhibitor) {
        exhibitorManageService.insertProcess(exhibitor);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


    @PutMapping("/exhibitor/{id}")
    public ResponseEntity<OperationResponse> updateExhibitor(@PathVariable long id, @RequestBody @Valid ExhibitorDTO exhibitor) {
        exhibitorManageService.updateProcess(id, exhibitor);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/exhibitor/{id}")
    public ResponseEntity<OperationResponse> deletedExhibitor(@PathVariable long id) {
        exhibitorManageService.deleteExhibitor(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


    @PutMapping("/exhibitor/booth/{id}")
    public ResponseEntity<OperationResponse> updateBooth(@PathVariable long id, @RequestBody ExhibitorDTO exhibitor) {
        exhibitorManageService.updateBooth(id, exhibitor);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/exhibitor/add-invitation")
    public ResponseEntity<OperationResponse> addInvitation(@RequestBody AddInvitationDTO addInvitation) {
        exhibitorManageService.addInvitation(addInvitation);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/exhibitor/add-invitation/{id}")
    public ResponseEntity<OperationResponse> deletedAddInvitation(@PathVariable long id) {
        exhibitorManageService.deleteInvitation(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


    @PostMapping("/exhibitor/invitation")
    public PageableInvitation getExhibitors(PageableInvitation search) {
        exhibitorManageService.getInvitationList(search);
        return search;
    }

    @PutMapping("/exhibitor/invitation/{id}")
    public  ResponseEntity<OperationResponse> updateInvitation(@PathVariable long id, @RequestBody InvitationDTO invitation) {
        exhibitorManageService.updateInvitation(id, invitation);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/exhibitor/invitation/{id}")
    public  ResponseEntity<OperationResponse> sendInvitation(@PathVariable long id, @RequestBody List<InvitationDTO> invitations) {
        exhibitorManageService.sendInvitationProcess(id, invitations);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @GetMapping("/exhibitor/excel/{exhibitionId}")
    public Callable<ResponseEntity<StreamingResponseBody>> excelDownloadExhibitor(@PathVariable long exhibitionId, HttpServletResponse response) throws IOException {
        return () -> exhibitorManageService.excelDownload(exhibitionId,response);
    }


    @GetMapping("/exhibitor/invitation/excel/{exhibitionId}")
    public Callable<ResponseEntity<StreamingResponseBody>> invitationExcelDownload(@PathVariable long exhibitionId, HttpServletResponse response) throws IOException {
       return () -> exhibitorManageService.invitationExcelDownload(exhibitionId,response);
    }

//    @GetMapping("/exhibitor/invitation/excel/{exhibitionId}")
//    public void invitationExcelDownload(@PathVariable long exhibitionId, HttpServletResponse response) throws IOException {
//        exhibitorManageService.invitationExcelDownload(exhibitionId,response);
//    }

    @GetMapping("/exhibitor/invitation/remind")
    public Callable<ResponseEntity<OperationResponse>> remindInvitation(){
        exhibitorManageService.remindInvitation();
        return () -> ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

}

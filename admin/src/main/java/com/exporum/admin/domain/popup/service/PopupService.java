package com.exporum.admin.domain.popup.service;

import com.exporum.admin.domain.popup.mapper.PopupMapper;
import com.exporum.admin.domain.popup.model.PageablePopup;
import com.exporum.admin.domain.popup.model.PopupDTO;
import com.exporum.admin.domain.popup.model.PopupList;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 6.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupMapper popupMapper;

    private final StorageService storageService;

    @Value("${ncp.object-storage.path.popup}")
    private String ncpObjectStoragePath;

    @Value("${resource.storage.url}")
    private String resourceStorageUrl;


    public void getPageablePopup(PageablePopup pageable) {

        long recordsTotal = popupMapper.getPublishPopupCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        List<PopupList> list = popupMapper.getPublishPopup(pageable, resourceStorageUrl);

        pageable.setData(list);

    }


    @Transactional
    public void insertProcess(PopupDTO popup) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        popup.setAdminId(adminId);

        FileDTO file = storageService.ncpUpload(popup.getFile(), ncpObjectStoragePath);
        popup.setFileId(file.getId());

        List<PopupList> list = popupMapper.getCurrentPublishPopup(popup.getExhibitionId());
        popup.setDisplayOrder(list.size()+1);

        this.insertPopup(popup);
    }

    @Transactional
    public void updateOrderProcess(List<PopupDTO> popups) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        for(PopupDTO popup : popups){
            popup.setAdminId(adminId);
            this.updatePopupOrder(popup);
        }
    }

    @Transactional
    public void updateProcess(PopupDTO popup) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        boolean published = popupMapper.getCurrentPublished(popup.getId());

        popup.setAdminId(adminId);
        if(popup.getFile() != null){
            FileDTO file = storageService.ncpUpload(popup.getFile(), ncpObjectStoragePath);
            popup.setFileId(file.getId());
        }

        popup.setDisplayOrder(99);

        this.updatePopup(popup);

        List<PopupList> list = popupMapper.getCurrentPublishPopup(popup.getExhibitionId());

        IntStream.range(0, list.size()).forEach(i -> {
            PopupDTO dto = new PopupDTO();
            dto.setId(list.get(i).getId());
            dto.setDisplayOrder(i + 1);
            dto.setAdminId(adminId);
            this.updatePopupOrder(dto);
        });

    }

    private void updatePopup(PopupDTO popup) {
        if(!(popupMapper.updatePopup(popup)>0)){
            throw new OperationFailException();
        }
    }



    private void updatePopupOrder(PopupDTO popup) {
        if(!(popupMapper.updatePopupOrder(popup)>0)){
            throw new OperationFailException();
        }
    }

    private void insertPopup(PopupDTO popup) {
        if(!(popupMapper.insertPopup(popup)>0)){
            throw new OperationFailException();
        }
    }
}

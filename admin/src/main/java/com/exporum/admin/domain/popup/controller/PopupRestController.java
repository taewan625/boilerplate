package com.exporum.admin.domain.popup.controller;

import com.exporum.admin.domain.popup.model.PageablePopup;
import com.exporum.admin.domain.popup.model.PopupDTO;
import com.exporum.admin.domain.popup.service.PopupService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 6.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class PopupRestController {

    private final PopupService popupService;

    @PostMapping("/popup")
    public PageablePopup getPageablePayment(PageablePopup search) {
        popupService.getPageablePopup(search);
        return search;
    }


    @PostMapping("/popup/insert")
    public ResponseEntity<OperationResponse> insertPopup(@ModelAttribute PopupDTO popup) {
        popupService.insertProcess(popup);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


    @PutMapping("/popup")
    public ResponseEntity<OperationResponse> updatedPopup(@ModelAttribute PopupDTO popup) {
        popupService.updateProcess(popup);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }


    @PutMapping("/popup/order")
    public ResponseEntity<OperationResponse> updatePopupOrder(@RequestBody List<PopupDTO> popups) {
        popupService.updateOrderProcess(popups);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

}

package com.exporum.client.domain.home.controller;

import com.exporum.client.domain.home.model.Popup;
import com.exporum.client.domain.home.service.PopupService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class PopupRestController {


    private final PopupService popupService;

    @GetMapping("/popup")
    public ResponseEntity<OperationResponse> getPopup() {
        List<Popup> result = popupService.getPopup();
        return ResponseEntity.ok(new ContentsResponse<>(
                OperationStatus.SUCCESS,
                result)
        );
    }
}

package com.exporum.admin.domain.v1.exhibition.exhibition.controller;

import com.exporum.admin.domain.v1.exhibition.exhibition.model.ExhibitionCreateRequest;
import com.exporum.admin.domain.v1.exhibition.exhibition.service.ExhibitionService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.OperationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExhibitionRestController.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 7. 최초 작성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exhibition")
public class ExhibitionRestController {
    public final ExhibitionService exhibitionService;

    @PostMapping("/exhibition/create")
    public ResponseEntity<OperationResponse> createExhibition(@Valid @RequestBody ExhibitionCreateRequest exhibitionCreateRequest) {
        exhibitionService.createExhibition(exhibitionCreateRequest);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}

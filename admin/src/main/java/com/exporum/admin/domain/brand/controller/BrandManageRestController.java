package com.exporum.admin.domain.brand.controller;

import com.exporum.admin.domain.brand.model.Brand;
import com.exporum.admin.domain.brand.model.BrandDTO;
import com.exporum.admin.domain.brand.model.PageableBrand;
import com.exporum.admin.domain.brand.service.BrandManageService;
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
import java.util.concurrent.Callable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 12.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class BrandManageRestController {

    private final BrandManageService brandManageService;

    @PostMapping("/brand")
    public PageableBrand getPageableAttendee(PageableBrand search) {
        brandManageService.getPageableExhibitor(search);
        return search;
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<OperationResponse> getExhibitor(@PathVariable long id) {
        Brand brand = brandManageService.getExhibitor(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, brand));
    }

    @PutMapping("/brand/{id}")
    public ResponseEntity<OperationResponse> updateExhibitor(@PathVariable long id, @Valid @ModelAttribute BrandDTO exhibitor) {
        brandManageService.updateExhibitorProcess(id, exhibitor);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/brand/{id}/approve")
    public ResponseEntity<OperationResponse> updateApprove(@PathVariable long id, @RequestBody BrandDTO exhibitor) {
        brandManageService.updateApprove(id, exhibitor);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @GetMapping("/brand/excel/{exhibitionId}")
    public Callable<ResponseEntity<StreamingResponseBody>> excelDownloadExhibitor(@PathVariable long exhibitionId, HttpServletResponse response) throws IOException {
        return () -> brandManageService.excelDownload(exhibitionId,response);
    }

    @GetMapping("/brand/logo/download")
    public Callable<ResponseEntity<OperationResponse>> downloadLogo() {
        brandManageService.getLogoDownLoad();
        return ()-> ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}

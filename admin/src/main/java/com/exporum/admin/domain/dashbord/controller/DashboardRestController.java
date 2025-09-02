package com.exporum.admin.domain.dashbord.controller;

import com.exporum.admin.domain.dashbord.model.DashboardDTO;
import com.exporum.admin.domain.dashbord.service.DashboardService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class DashboardRestController {

    private final DashboardService dashboardService;

    @PutMapping("/dashboard/{exhibitionId}")
    public ResponseEntity<OperationResponse> getDashboard(@PathVariable Long exhibitionId, @RequestBody DashboardDTO dto) {
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, dashboardService.getDashboardData(exhibitionId, dto)));
    }
}

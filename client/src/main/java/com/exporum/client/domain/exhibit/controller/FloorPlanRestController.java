package com.exporum.client.domain.exhibit.controller;

import com.exporum.client.domain.exhibit.model.FloorPlan;
import com.exporum.client.domain.exhibit.service.FloorPlanService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 24.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class FloorPlanRestController {

    private final FloorPlanService floorPlanService;

    @GetMapping("/floor-plan")
    public ResponseEntity<OperationResponse> getFloorPlan() {
        FloorPlan result = floorPlanService.getFloorPlan();
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS ,result));
    }

}

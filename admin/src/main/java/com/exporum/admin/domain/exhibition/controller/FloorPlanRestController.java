package com.exporum.admin.domain.exhibition.controller;

import com.exporum.admin.domain.exhibition.model.FloorPlanDTO;
import com.exporum.admin.domain.exhibition.model.PageableFloorPlan;
import com.exporum.admin.domain.exhibition.service.FloorPlanService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class FloorPlanRestController {

    private final FloorPlanService floorPlanService;

    @PostMapping("/floor-plan")
    public PageableFloorPlan getPageableSubscribe(PageableFloorPlan search) {
        floorPlanService.getFloorPlanList(search);
        return search;
    }

    @PostMapping("/floor-plan/register")
    public ResponseEntity<OperationResponse> insertFloorPlan(@ModelAttribute FloorPlanDTO floorPlan) {
        floorPlanService.insertFloorPlanProcess(floorPlan);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/floor-plan/{id}")
    public ResponseEntity<OperationResponse> updateFloorPlan(@PathVariable long id, @RequestBody FloorPlanDTO floorPlan) {
        floorPlanService.updateFloorPlanProcess(id, floorPlan);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}

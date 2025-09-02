package com.exporum.client.domain.exhibit.service;

import com.exporum.client.domain.exhibit.model.Exhibition;
import com.exporum.client.domain.exhibit.mapper.FloorPlanMapper;
import com.exporum.client.domain.exhibit.model.FloorPlan;
import com.exporum.core.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 24.
 * @description :
 */


@Service
@RequiredArgsConstructor
public class FloorPlanService {
    private final FloorPlanMapper floorPlanMapper;

    private final ExhibitionService exhibitionService;

    @Value("${resource.storage.url}")
    public String storageUrl;

    public FloorPlan getFloorPlan() {
        Exhibition exhibition = exhibitionService.getCurrentExhibition();

        FloorPlan floorPlan = Optional.ofNullable(floorPlanMapper.getFloorPlan(exhibition.getId())).orElseThrow(DataNotFoundException::new);
        floorPlan.setPath(storageUrl + floorPlan.getPath());
        return floorPlan;
    }



}

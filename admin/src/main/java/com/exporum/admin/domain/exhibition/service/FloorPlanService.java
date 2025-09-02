package com.exporum.admin.domain.exhibition.service;

import com.exporum.admin.domain.exhibition.mapper.FloorPlanMapper;
import com.exporum.admin.domain.exhibition.model.FloorPlanDTO;
import com.exporum.admin.domain.exhibition.model.PageableFloorPlan;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class FloorPlanService {

    private final FloorPlanMapper floorPlanMapper;
    private final StorageService storageService;

    @Value("${resource.storage.url}")
    private String storageUrl;

    @Value("${ncp.object-storage.path.floor-plan}")
    private String objectStoragePath;

    public void getFloorPlanList(PageableFloorPlan pageable){
        long recordsTotal = floorPlanMapper.getFloorPlanCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(floorPlanMapper.getFloorPlanList(pageable, storageUrl));
    }

    @Transactional
    public void insertFloorPlanProcess(FloorPlanDTO floorPlan){
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        floorPlan.setAdminId(adminId);

        if(floorPlan.getFile() != null){
            FileDTO file = storageService.ncpUpload(floorPlan.getFile(), objectStoragePath);
            floorPlan.setFileId(file.getId());
        }else{
            throw new OperationFailException();
        }

        this.insertFloorPlan(floorPlan);
    }

    @Transactional
    public void updateFloorPlanProcess(long id, FloorPlanDTO floorPlan){
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        floorPlan.setAdminId(adminId);

        long currentPublishFloorPlanId = getCurrentPublishFloorPlanId(floorPlan.getExhibitionId());

        this.unpublishFloorPlan(currentPublishFloorPlanId, adminId);
        this.publishFloorPlan(id, adminId);
    }

    private void insertFloorPlan(FloorPlanDTO floorPlan) {
        if(!(floorPlanMapper.insertFloorPlan(floorPlan)>0)){
            throw new OperationFailException();
        }
    }

    private void publishFloorPlan(long id, long adminId){
        if(!(floorPlanMapper.publishFloorPlan(id, adminId)>0)){
            throw new OperationFailException();
        }
    }

    private void unpublishFloorPlan(long id, long adminId){
        if(!(floorPlanMapper.unpublishFloorPlan(id, adminId)>0)){
            throw new OperationFailException();
        }
    }

    private long getCurrentPublishFloorPlanId(long exhibitionId) {
        return floorPlanMapper.getPublishFloorPlanId(exhibitionId);
    }
}

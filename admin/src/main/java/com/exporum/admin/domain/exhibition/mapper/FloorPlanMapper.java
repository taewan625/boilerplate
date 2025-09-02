package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.FloorPlanDTO;
import com.exporum.admin.domain.exhibition.model.FloorPlanList;
import com.exporum.admin.domain.exhibition.model.PageableFloorPlan;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Mapper
public interface FloorPlanMapper {

    @SelectProvider(type = FloorPlanSqlProvider.class, method = "getFloorPlanList")
    List<FloorPlanList> getFloorPlanList(@Param("floorPlan") PageableFloorPlan floorPlan, @Param("path") String path);

    @SelectProvider(type = FloorPlanSqlProvider.class, method = "getFloorPlanCount")
    long getFloorPlanCount(@Param("floorPlan") PageableFloorPlan floorPlan);


    @InsertProvider(type = FloorPlanSqlProvider.class, method = "insertFloorPlan")
    int insertFloorPlan(@Param("floorPlan") FloorPlanDTO floorPlan);

    @UpdateProvider(type = FloorPlanSqlProvider.class, method = "publishFloorPlan")
    int publishFloorPlan(@Param("id") long id, @Param("adminId") long adminId);

    @UpdateProvider(type = FloorPlanSqlProvider.class, method = "unpublishFloorPlan")
    int unpublishFloorPlan(@Param("id") long id, @Param("adminId") long adminId);

    @SelectProvider(type = FloorPlanSqlProvider.class, method = "getPublishFloorPlanId")
    long getPublishFloorPlanId(@Param("exhibitionId") long exhibitionId);
}

package com.exporum.client.domain.exhibit.mapper;

import com.exporum.client.domain.exhibit.model.FloorPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 24.
 * @description :
 */

@Mapper
public interface FloorPlanMapper {

    @SelectProvider(type = FloorPlanSqlProvider.class ,method = "getFloorPlan")
    FloorPlan getFloorPlan(@Param("exhibitionId") long exhibitionId);

}

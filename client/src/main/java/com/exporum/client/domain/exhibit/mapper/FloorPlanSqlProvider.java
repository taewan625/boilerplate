package com.exporum.client.domain.exhibit.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 24.
 * @description :
 */
public class FloorPlanSqlProvider {


    public String getFloorPlan(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        bfp.id, f.id as file_id, f.origin_file_name, f.file_size,
                        f.mime_type_code as mime_type, f.file_path as path, f.uuid
                        """);
                FROM("exhibition_floor_plan as bfp");
                JOIN("exhibition as ex on ex.id = bfp.exhibition_id");
                JOIN("files as f on bfp.file_id = f.id");
                WHERE("bfp.is_deleted = 0");
                WHERE("bfp.exhibition_id = #{exhibitionId}");
            }
        }.toString();
    }
}

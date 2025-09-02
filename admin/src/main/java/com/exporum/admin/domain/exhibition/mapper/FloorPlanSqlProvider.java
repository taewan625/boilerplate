package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.FloorPlanDTO;
import com.exporum.admin.domain.exhibition.model.PageableFloorPlan;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */
public class FloorPlanSqlProvider {

    public String publishFloorPlan(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibition_floor_plan");
                SET("is_deleted = 0");
                SET("updated_at = sysdate()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String unpublishFloorPlan(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibition_floor_plan");
                SET("is_deleted = 1");
                SET("updated_at = sysdate()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String getPublishFloorPlanId(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        id
                        """);
                FROM("exhibition_floor_plan");
                WHERE("exhibition_id = #{exhibitionId}");
                WHERE("is_deleted = 0");

            }
        }.toString();
    }






    public String insertFloorPlan(FloorPlanDTO floorPlan) {
        return new SQL(){
            {
                INSERT_INTO("exhibition_floor_plan");
                VALUES("exhibition_id", "#{floorPlan.exhibitionId}");
                VALUES("file_id", "#{floorPlan.fileId}");
                VALUES("is_deleted", "1");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{floorPlan.adminId}");
            }
        }.toString();

    }


    public String getFloorPlanList(PageableFloorPlan floorPlan, String path) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY id asc) AS no,
                        efp.id, concat(#{path},f.file_path) as file_path, f.uuid,
                        f.origin_file_name as filename, efp.is_deleted as deleted,
                        efp.created_at
                        """);
                FROM("exhibition_floor_plan as efp");
                JOIN("files as f on efp.file_id = f.id ");
                WHERE("exhibition_id = #{floorPlan.exhibitionId}");
                ORDER_BY("efp.is_deleted asc, id desc");
                LIMIT(floorPlan.getLength());
                OFFSET(floorPlan.getStart());
            }
        }.toString();
    }

    public String getFloorPlanCount(PageableFloorPlan floorPlan) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("exhibition_floor_plan as efp");
                JOIN("files as f on efp.file_id = f.id ");
                WHERE("exhibition_id = #{floorPlan.exhibitionId}");
            }
        }.toString();
    }
}

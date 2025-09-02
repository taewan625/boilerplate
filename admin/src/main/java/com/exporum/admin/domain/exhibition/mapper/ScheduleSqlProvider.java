package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.*;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */
public class ScheduleSqlProvider {

    public String insertSchedule(ScheduleDTO schedule) {
        return new SQL(){
            {
                INSERT_INTO("exhibition_event");
                VALUES("exhibition_Id", "#{schedule.exhibitionId}");
                VALUES("event_code", "#{schedule.eventCode}");
                VALUES("event_date", "#{schedule.eventDate}");
                VALUES("organization", "#{schedule.organization}");
                VALUES("event_name", "#{schedule.eventName}");
                VALUES("event_description", "#{schedule.eventDescription}");
                VALUES("is_use", "0");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{schedule.adminId}");

            }
        }.toString();
    }


    public String insertEvent(EventDTO event) {
        return new SQL(){
            {
                INSERT_INTO("exhibition_event_detail");
                VALUES("event_id", "#{event.eventId}");
                VALUES("event_title", "#{event.eventTitle}");
                VALUES("event_location", "#{event.eventLocation}");
                VALUES("start_time", "#{event.startTime}");
                VALUES("end_time", "#{event.endTime}");
                VALUES("display_order", "#{event.order}");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{event.adminId}");

            }
        }.toString();
    }

    public String deleteEvent(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibition_event_detail");
                SET("is_use = 0");
                SET("is_deleted = 1");
                SET("deleted_at = sysdate()");
                SET("deleted_by = #{adminId}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String updateEventOrder(EventList event, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibition_event_detail");
                SET("display_order = #{event.order}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{event.id}");
            }
        }.toString();
    }

    public String getSchedule(long id){
        return new SQL(){
            {
                SELECT("""
                        ee.id, exhibition_name,
                        case when ee.is_use = 1 then 'PUBLISHED' else 'UNPUBLISHED' end as status,
                        ee.organization, ee.event_name, ee.event_description as description, ee.event_date
                        """);
                FROM("exhibition_event as ee");
                JOIN("exhibition as ex on ee.exhibition_id = ex.id");
                WHERE("ee.id = #{id} ");
            }
        }.toString();
    }

    public String updateSchedule(long id, ScheduleDTO schedule) {
        return new SQL(){
            {
                UPDATE("exhibition_event");
                SET("event_date = #{schedule.eventDate}");
                SET("organization = #{schedule.organization}");
                SET("event_name = #{schedule.eventName}");
                SET("event_description = #{schedule.eventDescription}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{schedule.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String updatedUseSchedule(long id, ScheduleDTO schedule){
        return new SQL(){
            {
                UPDATE("exhibition_event");
                SET("is_use = #{schedule.used}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{schedule.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String deleteSchedule(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibition_event");
                SET("is_use = 0");
                SET("is_deleted = 1");
                SET("deleted_at = sysdate()");
                SET("deleted_by = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String getEventList(PageableEvent event){
        return new SQL(){
            {
                SELECT("""
                        id, TO_CHAR(start_time, 'HH24:MI') as start_time, TO_CHAR(end_time, 'HH24:MI') as end_time, display_order as `order`,
                        event_title as title, event_location as location
                        """);
                FROM("exhibition_event_detail");
                WHERE("event_id = #{event.eventId}");
                WHERE("is_use = 1");
                WHERE("is_deleted = 0");
                ORDER_BY("display_order asc");
                //LIMIT(event.getLength());
                //OFFSET(event.getStart());
            }
        }.toString();
    }

    public String getEventCount(PageableEvent event){
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("exhibition_event_detail");
                WHERE("event_id = #{event.eventId}");
                WHERE("is_use = 1");
                WHERE("is_deleted = 0");
            }
        }.toString();
    }


    public String getScheduleList(PageableSchedule schedule) {
        return new SQL() {
            {
                SELECT("""
                        id, event_date, event_name, organization, is_use as used, created_at
                        """);
                FROM("exhibition_event");
                WHERE("exhibition_id = #{schedule.exhibitionId} ");
                WHERE("is_deleted = 0 ");
                ORDER_BY("event_date asc, id desc");
                LIMIT(schedule.getLength());
                OFFSET(schedule.getStart());
            }
        }.toString();
    }

    public String getScheduleCount(PageableSchedule schedule) {
        return new SQL() {
            {
                SELECT("""
                        count(*)
                        """);
                FROM("exhibition_event");
                WHERE("exhibition_id = #{schedule.exhibitionId} ");
                WHERE("is_deleted = 0 ");
            }
        }.toString();
    }
}

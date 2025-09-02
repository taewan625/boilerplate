package com.exporum.client.domain.exhibit.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */
public class EventSqlProvider {

    public String getEvents(int eventYear){
        return new SQL(){
            {

                SELECT("""
                        ex.id as id, c.country_name_en as country, ex.city as city,
                        ex.venue as venue, ex.start_date as start_date, ex.end_date as end_date
                        """);
                FROM("exhibition as ex");
                JOIN("country as c ON ex.country_id = c.id");
                WHERE("ex.year = #{eventYear}");

            }
        }.toString();
    }


    public String getEventList(long exhibitionId){
        return new SQL(){
            {

                SELECT("""
                        ee.id, e.year as eventYear,  ee.event_code, ee.event_date, ee.event_name, ee.event_description
                        """);
                FROM("exhibition_event as ee");
                JOIN("exhibition as e ON  e.id = ee.exhibition_id");
                WHERE("ee.exhibition_id = #{exhibitionId}");
                WHERE("ee.is_use = 1");
                WHERE("ee.is_deleted = 0");

            }
        }.toString();
    }


    public String getEventDetailList(long eventId){
        return new SQL(){
            {
                SELECT("""
                        event_title, event_location, event_description, display_order,
                        TO_CHAR(start_time, 'HH24:MI') as start_time, TO_CHAR(end_time, 'HH24:MI') as end_time
                        """);
                FROM("exhibition_event_detail");
                WHERE("event_id = #{eventId}");
                WHERE("is_use = 1");
                WHERE("is_deleted = 0");
            }
        }.toString();
    }






}

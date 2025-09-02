package com.exporum.client.domain.exhibit.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */
public class ExhibitionSqlProvider {

    //전시회 년도 목록
    public String getExhibitionYearList(){
        return new SQL(){
            {
                SELECT("""
                        year
                        """);
                FROM("exhibition");
                ORDER_BY("id desc");

            }
        }.toString();
    }


    public String getCurrentExhibition(){
        return new SQL(){
            {
                SELECT("""
                        ex.id as id,
                        c.country_name as country, c.country_name_en as country_en,
                        ex.year as year, ex.exhibition_name as exhibition,
                        ex.start_date as start_date, ex.end_date as end_date,
                        ex.city as city, ex.venue as venue
                        """);
                FROM("exhibition ex");
                JOIN("country c ON c.id=ex.country_id");
                WHERE("ex.is_use = 1");
            }
        }.toString();
    }

}

package com.exporum.admin.common.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 19.
 * @description :
 */

public class CommonSqlProvider {

    public String getExhibitionSelect(){
        return new SQL(){
            {
                SELECT("""
                        id, exhibition_name, is_use as used
                        """);
                FROM("exhibition");
                WHERE("is_deleted = 0");
                ORDER_BY("id desc");
            }
        }.toString();
    }

    public String getSelectOption(String parentCode){
        return new SQL(){
            {

                SELECT("""
                        code as value, code_name as text
                        """);
                FROM("code");
                WHERE("parent_code = #{parentCode}");
                WHERE("is_deleted = 0");
                WHERE("is_use = 1");
                ORDER_BY("display_order asc");
            }
        }.toString();
    }

    public String getTicketSelectOption(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        id as value, ticket_type_code as type, ticket_name as text
                        """);
                FROM("ticket");
                WHERE("exhibition_id = #{exhibitionId}");
                WHERE("is_deleted = 0");
                WHERE("is_buying = 1");
            }
        }.toString();
    }

    public String getExhibitionTimezone(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        c.time_zone
                        """);
                FROM("exhibition as e");
                JOIN("country as c on e.country_id = c.id");
                WHERE("e.id = #{exhibitionId}");
            }
        }.toString();
    }

    public String getCountryOption(){
        return new SQL(){
            {
                SELECT("""
                        MIN(id) AS id, country_name_en as country, MAX(is_mutual_tax_exemption) AS mutual_tax_exemption
                        """);
                FROM("country");
                WHERE("is_deleted = 0");
                WHERE("is_use = 1");
                GROUP_BY("country_name_en");
                ORDER_BY("country_name_en ASC");
            }
        }.toString();
    }

    public String getCallingCodeOption(){
        return new SQL(){
            {
                SELECT("""
                        country_name_en as country, calling_code
                        """);
                FROM("country");
                WHERE("is_deleted = 0");
                WHERE("is_use = 1");
                ORDER_BY("country_name_en ASC");
            }
        }.toString();
    }


}

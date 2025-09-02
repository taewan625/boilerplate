package com.exporum.core.domain.country.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 10.
 * @description :
 */
public class CountrySqlProvider {



    public String getCountry(long id){
        return new SQL(){
            {
                SELECT("""
                        id, alpha_2 as alpha_2_code, alpha_3 as alpha_3_code,
                        country_name, country_name_en, `numeric`, calling_code,
                        is_mutual_tax_exemption, mutual_tax_date
                        """);
                FROM("country");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String getCountryList(){
        return new SQL(){
            {
                SELECT("""
                        id, alpha_2 as alpha_2_code, country_name, country_name_en
                        """);
                FROM("country");
                WHERE("is_use = 1");
                WHERE("is_deleted = 0");
                GROUP_BY("alpha_2");
                ORDER_BY("alpha_2 ASC");
            }
        }.toString();
    }

    public String getCallingCodeList(){
        return new SQL(){
            {
                SELECT("""
                        id, alpha_2 as alpha_2_code, country_name, country_name_en, calling_code
                        """);
                FROM("country");
                WHERE("is_use = 1");
                WHERE("is_deleted = 0");
                ORDER_BY("alpha_2 ASC");
            }
        }.toString();

    }
}

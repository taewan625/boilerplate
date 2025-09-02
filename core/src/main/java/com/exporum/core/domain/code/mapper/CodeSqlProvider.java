package com.exporum.core.domain.code.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */
public class CodeSqlProvider {

    public String getCode(String code) {
        return new SQL(){
            {
                SELECT("""
                        code, code_name
                        """);
                FROM("code");
                WHERE("code = #{code}");
            }
        }.toString();

    }

    public String findByCode(String code) {
        return new SQL(){
            {
                SELECT("display_order, code_level, code, code_name");
                FROM("code");
                WHERE("code = #{code}");
                WHERE("is_use = 1");
                ORDER_BY("display_order ASC");
            }
        }.toString();
    }

    public String findByCodeIn(String codes) {
        return new SQL(){
            {
                SELECT("display_order, code_level, code, code_name");
                FROM("code");
                WHERE("code in (${codes})");
                WHERE("is_use = 1");
                ORDER_BY("display_order ASC");
            }
        }.toString();
    }


    public String findChildrenCodeByCode(String code) {
        return new SQL(){
            {
                SELECT("display_order, code_level, code, code_name");
                FROM("code");
                WHERE("parent_code = #{code}");
                WHERE("is_use = 1");
                ORDER_BY("display_order ASC");
            }
        }.toString();
    }


    public String findCodeSetBySetCode(String setCode) {
        return  new SQL(){
            {
                SELECT("set_code, set_name, codes, is_use");
                FROM("code_set");
                WHERE("set_code = #{setCode}");
            }
        }.toString();
    }


}


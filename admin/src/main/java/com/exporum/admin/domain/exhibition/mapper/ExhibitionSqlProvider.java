package com.exporum.admin.domain.exhibition.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */
public class ExhibitionSqlProvider {

    public String findAll(){
        return new SQL(){
            {
                SELECT("*");
                FROM("exhibition");
            }
        }.toString();
    }

}

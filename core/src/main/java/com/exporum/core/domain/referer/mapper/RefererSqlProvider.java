package com.exporum.core.domain.referer.mapper;

import com.exporum.core.domain.referer.model.ConnectionLog;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */


public class RefererSqlProvider {

    public String insertConnectionLog(ConnectionLog referer) {
        return new SQL(){
            {
                INSERT_INTO("connection_log");
                VALUES("referer", "#{referer.referer}");
                VALUES("url", "#{referer.url}");
                VALUES("parameter", "#{referer.parameter}");
                VALUES("language", "#{referer.language}");
                VALUES("user_agent", "#{referer.userAgent}");
                VALUES("ip", "#{referer.ip}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();


    }


}

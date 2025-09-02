package com.exporum.client.domain.home.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */
public class PopupSqlProvider {

    public String getPopup(long exhibitionId, String path) {
        return new SQL(){
            {
                SELECT("""
                        p.id, p.display_order, p.link_target_code as link_target, p.link,
                        concat(#{path},f.file_path) as path
                        """);
                FROM("popup as p");
                JOIN("files as f on p.file_id = f.id");
                WHERE("is_published = 1");
                WHERE("CURDATE() between TO_CHAR(start_date, 'YYYY-MM-DD') and TO_CHAR(end_date, 'YYYY-MM-DD')");
                ORDER_BY("p.display_order asc");
            }
        }.toString();
    }
}

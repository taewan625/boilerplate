package com.exporum.client.domain.notice.mapper;

import com.exporum.client.domain.notice.model.SearchBBS;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 2.
 * @description :
 */
public class BBSSqlProvider {

    public String getBBSList(SearchBBS searchBBS){
        return new SQL(){
            {

                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY created_at) AS no,
                        id, title, is_fixed, created_at
                        """);
                FROM("bulletin_board_system");
                WHERE("bbs_code = #{searchBBS.bbsType}");
                WHERE("is_disabled = 0");
                WHERE("is_deleted = 0");
                if (StringUtils.hasText(searchBBS.getSearchText())) {
                    WHERE("title LIKE CONCAT('%', #{searchBBS.searchText}, '%') " +
                            "OR content LIKE CONCAT('%', #{searchBBS.searchText}, '%')");
                }
                ORDER_BY("created_at DESC");
                LIMIT(searchBBS.getPageSize());
                OFFSET(searchBBS.getOffset());
            }
        }.toString();
    }


    public String getBBSCount(SearchBBS searchBBS){

        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("bulletin_board_system");
                WHERE("bbs_code = #{searchBBS.bbsType}");
                WHERE("is_disabled = 0");
                WHERE("is_deleted = 0");
                if (StringUtils.hasText(searchBBS.getSearchText())) {
                    WHERE("title LIKE CONCAT('%', #{searchBBS.searchText}, '%') " +
                            "OR content LIKE CONCAT('%', #{searchBBS.searchText}, '%')");
                }
            }
        }.toString();


    }

    public String getBBS(long id){
        return new SQL(){
            {
                SELECT("""
                        id, bbs_code, title, content, created_at
                        """);
                FROM("bulletin_board_system");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String getAttachFiles(long id){
        return new SQL(){
            {
                SELECT("""
                        f.uuid, f.file_path as path,
                        f.origin_file_name as file_original_name
                        """);
                FROM("bbs_attachment_file as baf");
                JOIN("files f on baf.file_id = f.id");
                WHERE("baf.bbs_id = #{id}");
                WHERE("baf.is_deleted = 0");
            }
        }.toString();
    }

    public String getPreBBS(long id, String bbsCode){
        return new SQL(){
            {
                SELECT("""
                        id, title, created_at
                        """);
                FROM("bulletin_board_system");
                WHERE("id < #{id}");
                WHERE("bbs_code = #{bbsCode}");
                WHERE("is_disabled = 0");
                WHERE("is_deleted = 0");
                ORDER_BY("id DESC");
                LIMIT(1);

            }
        }.toString();
    }

    public String getNextBBS(long id, String bbsCode){
        return new SQL(){
            {
                SELECT("""
                        id, title, created_at
                        """);
                FROM("bulletin_board_system");
                WHERE("id > #{id}");
                WHERE("bbs_code = #{bbsCode}");
                WHERE("is_disabled = 0");
                WHERE("is_deleted = 0");
                ORDER_BY("id ASC");
                LIMIT(1);

            }
        }.toString();

    }
}

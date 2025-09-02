package com.exporum.client.domain.notice.mapper;

import com.exporum.client.domain.notice.model.SearchNewsletter;
import com.exporum.client.domain.notice.model.SubscribeDTO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 3.
 * @description :
 */
public class NewsletterSqlProvider {

    public String insertSubscribe(SubscribeDTO subscribe) {
        return STR."\{new SQL() {
            {
                INSERT_INTO("subscribe");
                VALUES("first_name", "#{subscribe.firstName}");
                VALUES("last_name", "#{subscribe.lastName}");
                VALUES("email", "#{subscribe.email}");
                VALUES("created_at", "sysdate()");
            }
        }.toString()} ON DUPLICATE KEY UPDATE is_subscribe = 1, updated_at = sysdate()";
    }


    public String getNewsletterList(SearchNewsletter search, String storageUrl) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY n.issue_date DESC) AS no,
                        n.id,
                        n.title,
                        n.content,
                        n.issue_date,
                        n.is_enable as enable,
                        n.is_deleted as deleted,
                        n.logo_file_id,
                        n.html_file_id,
                        lf.origin_file_name as logo_file_name,
                        hf.origin_file_name as html_file_name,
                        concat(#{storageUrl},lf.file_path) as logo_file_path,
                        n.created_at,
                        n.updated_at,
                        ca.admin_name as created_by,
                        ua.admin_name as updated_by
                """);
                FROM("newsletter as n");
                LEFT_OUTER_JOIN("files as lf on n.logo_file_id = lf.id");
                LEFT_OUTER_JOIN("files as hf on n.html_file_id = hf.id");
                LEFT_OUTER_JOIN("admin as ca on ca.id = n.created_by");
                LEFT_OUTER_JOIN("admin as ua on ua.id = n.updated_by");
                WHERE("n.is_deleted = 0");
                WHERE("n.is_enable = 1");
                if(StringUtils.hasText(search.getSearchText())){
                    WHERE("n.title like concat('%',#{search.searchText},'%')");
                }
                ORDER_BY("n.issue_date DESC, n.updated_at DESC");
                LIMIT(search.getPageSize());
                OFFSET(search.getOffset());
            }
        }.toString();
    }

    public String getNewsletterCount(SearchNewsletter search){
        return new SQL(){
            {
                SELECT("""
                        count(*)
                """);
                FROM("newsletter as n");
                WHERE("n.is_deleted = 0");
                WHERE("n.is_enable = 1");
                if(StringUtils.hasText(search.getSearchText())){
                    WHERE("n.title like concat('%',#{search.searchText},'%')");
                }
            }
        }.toString();
    }


    public String getNewsletter(long id, String storageUrl){
        return new SQL(){
            {
                SELECT("""
                        n.id,
                        n.title,
                        n.content,
                        n.issue_date,
                        n.is_enable as enable,
                        n.is_deleted as deleted,
                        n.logo_file_id,
                        n.html_file_id,
                        lf.origin_file_name as logo_file_name,
                        hf.origin_file_name as html_file_name,
                        concat(#{storageUrl},lf.file_path) as logo_file_path,
                        n.created_at,
                        n.updated_at,
                        ca.admin_name as created_by,
                        ua.admin_name as updated_by
                        """);
                FROM("newsletter as n");
                LEFT_OUTER_JOIN("files as lf on n.logo_file_id = lf.id");
                LEFT_OUTER_JOIN("files as hf on n.html_file_id = hf.id");
                LEFT_OUTER_JOIN("admin as ca on ca.id = n.created_by");
                LEFT_OUTER_JOIN("admin as ua on ua.id = n.updated_by");
                WHERE("n.id = #{id}");
            }
        }.toString();
    }

}

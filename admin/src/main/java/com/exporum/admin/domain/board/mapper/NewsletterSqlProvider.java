package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.Newsletter;
import com.exporum.admin.domain.board.model.NewsletterDTO;
import com.exporum.admin.domain.board.model.PageableNewsletter;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */
public class NewsletterSqlProvider {

    public String getNewsletterList(PageableNewsletter search){
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY n.issue_date DESC, n.updated_at DESC) AS no,
                        n.id, n.title, n.content,
                        n.issue_date, n.is_enable as enable,
                        n.is_deleted as deleted,
                        n.logo_file_id, n.html_file_id,
                        lf.origin_file_name as logo_file_name,
                        hf.origin_file_name as html_file_name,
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
                if("E".equals(search.getStatus()) ){
                    WHERE("n.is_enable= 1");
                }
                if("D".equals(search.getStatus()) ){
                    WHERE("n.is_enable= 0");
                }
                if(StringUtils.hasText(search.getSearchText())){
                    WHERE("n.title like concat('%',#{search.searchText},'%')");
                }
                ORDER_BY("n.issue_date DESC, n.updated_at DESC");
                LIMIT(search.getLength());
                OFFSET(search.getStart());
            }
        }.toString();
    }

    public String getNewsletterCount(PageableNewsletter search){
        return new SQL(){
            {
                SELECT("""
                        count(*)
                """);
                FROM("newsletter as n");
                WHERE("n.is_deleted = 0");
                if("E".equals(search.getStatus()) ){
                    WHERE("n.is_enable= 1");
                }
                if("D".equals(search.getStatus()) ){
                    WHERE("n.is_enable= 0");
                }
                if(StringUtils.hasText(search.getSearchText())){
                    WHERE("n.title like concat('%',#{search.searchText},'%')");
                }
            }
        }.toString();
    }


    public String getNewsletter(long newsletterId, String resourceStorageUrl){
        return new SQL(){
            {
                SELECT("""
                        n.id, n.title, n.content,
                        n.issue_date, n.is_enable as enable,
                        n.is_deleted as deleted,
                        n.logo_file_id, n.html_file_id,
                        lf.origin_file_name as logo_file_name,
                        case when lf.origin_file_name is null then 0 else 1 end as logoAttached,
                        concat(#{resourceStorageUrl},lf.file_path) as logo_file_path,
                        lf.uuid as logoUuid,
                        hf.origin_file_name as html_file_name,
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
                WHERE("n.id = #{newsletterId}");
            }
        }.toString();
    }


    public String insertNewsletter(NewsletterDTO newsletter, long adminId) {
        return new SQL(){
            {
                INSERT_INTO("newsletter");
                VALUES("title", "#{newsletter.title}");
                VALUES("content", "#{newsletter.content}");
                VALUES("issue_date", "#{newsletter.issueDate}");

                if(newsletter.getLogoFileId() > 0){
                    VALUES("logo_file_id", "#{newsletter.logoFileId}");
                }
                if(newsletter.getHtmlFileId() > 0){
                    VALUES("html_file_id", "#{newsletter.htmlFileId}");
                }

                VALUES("created_at", "current_timestamp()");
                VALUES("created_by", "#{adminId}");
            }
        }.toString();
    }


    public String updateNewsletter(long newsletterId, NewsletterDTO newsletter, long adminId){
        return new SQL(){
            {
                UPDATE("newsletter");
                SET("title = #{newsletter.title}");
                SET("content = #{newsletter.content}");
                if(newsletter.getIssueDate() != null){
                    SET("issue_date = #{newsletter.issueDate}");
                }
                if(newsletter.getLogoFile() != null){
                    SET("logo_file_id = #{newsletter.logoFileId}");
                }
                if(newsletter.getHtmlFile() != null){
                    SET("html_file_id = #{newsletter.htmlFileId}");
                }
                SET("updated_at = current_timestamp()");
                SET("updated_by = #{adminId}");
                WHERE("id = #{newsletterId}");
            }
        }.toString();
    }

    public String updateNewsletterEnabled(long newsletterId, Newsletter newsletter, long adminId) {
        return new SQL() {{
            UPDATE("newsletter");
            SET("is_enable = #{newsletter.enable}");
            SET("updated_at = current_timestamp()");
            SET("updated_by = #{adminId}");

            WHERE("id = #{newsletterId}");
        }}.toString();
    }

    public String deleteNewsletter(long newsletterId, long adminId){
        return new SQL(){
            {
                UPDATE("newsletter");
                SET("is_deleted = 1");
                SET("deleted_at = current_timestamp()");
                SET("deleted_by = #{adminId}");
                WHERE("id = #{newsletterId}");
            }
        }.toString();
    }
}

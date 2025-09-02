package com.exporum.admin.domain.popup.mapper;

import com.exporum.admin.domain.popup.model.PageablePopup;
import com.exporum.admin.domain.popup.model.PopupDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 6.
 * @description :
 */
public class PopupSqlProvider {

    public String insertPopup(PopupDTO popup) {
        return new SQL(){
            {
                INSERT_INTO("popup");
                VALUES("exhibition_id", "#{popup.exhibitionId}");
                VALUES("device_code", "#{popup.deviceCode}");
                VALUES("popup_type", "#{popup.popupType}");
                VALUES("link_target_code", "#{popup.linkTargetCode}");
                VALUES("file_id", "#{popup.fileId}");
                VALUES("start_date", "#{popup.startDate}");
                VALUES("end_date", "#{popup.endDate}");
                VALUES("display_order", "#{popup.displayOrder}");
                VALUES("title", "#{popup.title}");
                VALUES("link", "#{popup.link}");
                VALUES("is_published", "#{popup.publish}");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{popup.adminId}");
            }
        }.toString();
    }

    public String getCurrentPublished(long id) {
        return new SQL(){
            {
                SELECT("""
                        is_published
                        """);
                FROM("popup");
                WHERE("id=#{id}");
            }
        }.toString();
    }


    public String getCurrentPublishPopup(long exhibitionId) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY p.created_at asc) AS no,
                        p.id, p.link_target_code as linkType, p.title,
                        concat(TO_CHAR(p.start_date, 'YYYY-MM-DD'), ' ~ ', TO_CHAR(p.end_date, 'YYYY-MM-DD')) as publication_period,
                        TO_CHAR(p.start_date, 'YYYY-MM-DD') as start_date,
                        TO_CHAR(p.end_date, 'YYYY-MM-DD') as end_date,
                        p.link, p.display_order, p.is_published as published
                        """);
                FROM("popup as p");
                WHERE("p.exhibition_id = #{exhibitionId}");
                WHERE("p.is_published = 1");
                ORDER_BY("p.display_order asc");

            }
        }.toString();
    }

    public String getPublishPopup(PageablePopup search, String path) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY p.created_at asc) AS no,
                        p.id, p.link_target_code as linkType, p.title,
                        concat(TO_CHAR(p.start_date, 'YYYY-MM-DD'), ' ~ ', TO_CHAR(p.end_date, 'YYYY-MM-DD')) as publication_period,
                        TO_CHAR(p.start_date, 'YYYY-MM-DD') as start_date,
                        TO_CHAR(p.end_date, 'YYYY-MM-DD') as end_date,
                        p.link, f.id as file_id, concat(#{path},f.file_path) as path, p.display_order, p.is_published as published
                        """);
                FROM("popup as p");
                JOIN("files as f on p.file_id = f.id");
                WHERE("p.exhibition_id = #{search.exhibitionId}");
                WHERE("p.is_published = #{search.published}");
                if(search.isPublished()){
                    ORDER_BY("p.display_order asc, p.start_date desc, p.end_date desc");
                }else{
                    ORDER_BY("p.created_at desc");
                }
                if(!search.isPublished()){
                    LIMIT(search.getLength());
                    OFFSET(search.getStart());
                }
            }
        }.toString();
    }


    public String getPublishPopupCount(PageablePopup search) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("popup as p");
                WHERE("p.exhibition_id = #{search.exhibitionId}");
                WHERE("p.is_published = #{search.published}");
            }
        }.toString();
    }

    public String updatePopup(PopupDTO popup) {
        return new SQL(){
            {
                UPDATE("popup");
                SET("title = #{popup.title}");
                SET("start_date = #{popup.startDate}");
                SET("end_date = #{popup.endDate}");
                SET("link_target_code = #{popup.linkTargetCode}");
                SET("link = #{popup.link}");
                SET("file_id = #{popup.fileId}");
                SET("is_published = #{popup.publish}");
                SET("display_order = #{popup.displayOrder}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{popup.adminId}");
                WHERE("id = #{popup.id}");
            }
        }.toString();
    }

    public String updatePopupOrder(PopupDTO popup) {
        return new SQL(){
            {
                UPDATE("popup");
                SET("display_order = #{popup.displayOrder}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{popup.adminId}");
                WHERE("id = #{popup.id}");
            }
        }.toString();
    }
}

package com.exporum.admin.domain.exhibitor.mapper;

import com.exporum.admin.domain.exhibitor.model.InvitationDTO;
import com.exporum.admin.domain.exhibitor.model.PageableInvitation;
import com.exporum.core.enums.InvitationType;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 19.
 * @description :
 */
public class InvitationSendSqlProvider {
    public String getRemindInvitation(){
        return new SQL(){
            {
                SELECT("""
                        h.send_type as type,
                        h.email, h.name, h.company, h.job_title,
                        h.country, h.city, h.phone_number,
                        h.barcode, f.file_path as barcode_path
                        """);
                FROM("invitation_send_history as h");
                JOIN("files as f on h.file_id = f.id");
                JOIN("exhibitor_company as e on h.exhibitor_company_id = e.id");
                WHERE("h.is_cancelled = 0");
                WHERE("e.is_deleted = 0");
                WHERE("e.exhibition_id = 2");
            }
        }.toString();
    }



    public String invitationExcel(long exhibitionId) {
        return new SQL(){
            {
                SELECT("""
                            ROW_NUMBER() OVER (ORDER BY ec.id asc, ish.send_type asc, ish.id desc) AS no,
                            concat('https://kr.object.ncloudstorage.com/bucket-exporum-prod/',f.file_path) as barcodeImage,
                            ish.barcode, ic.code_name as invitation_type,
                            ish.email as receiver_email, ish.name as receiver_name, ish.company as receiver_company,
                            ish.job_title as receiver_job_title,
                            ish.country, ish.city, ish.phone_number as receiver_phone_number,
                            ac.code_name as type, ec.company_name, ec.brand_name, ec.booth_number,
                            ec.contact_name, ec.email, concat(ec.calling_number, ' ', ec.phone_number) as phone_number
                        """);
                FROM("exhibitor_company as ec");
                JOIN("code as ac on ec.application_type = ac.code");
                JOIN("invitation_send_history as ish on ec.id = ish.exhibitor_company_id");
                JOIN("code as ic on ish.send_type = ic.code");
                LEFT_OUTER_JOIN("files as f on ish.file_id = f.id");
                WHERE("ec.exhibition_id = #{exhibitionId}");
                WHERE("ec.is_deleted = 0");
                WHERE("ish.is_cancelled = 0");
                ORDER_BY("ec.id asc, ish.send_type asc, ish.id desc");
            }
        }.toString();
    }


    public String isBarcodeExists(String barcode){
        return new SQL(){
            {
                SELECT("""
                        count(*) > 0
                        """);
                FROM("invitation_send_history");
                WHERE("barcode = #{barcode}");
                WHERE("is_cancelled = 0");
            }
        }.toString();
    }

    public String insertInvitation(long id, InvitationDTO invitation) {
        return new SQL(){
            {
                INSERT_INTO("invitation_send_history");
                VALUES("exhibitor_company_id", "#{id}");
                VALUES("send_type", "#{invitation.type}");
                VALUES("file_id", "#{invitation.fileId}");
                VALUES("barcode", "#{invitation.barcode}");
                VALUES("email", "#{invitation.email}");
                VALUES("name", "#{invitation.name}");
                VALUES("company", "#{invitation.company}");
                VALUES("job_title", "#{invitation.jobTitle}");
                VALUES("country", "#{invitation.country}");
                VALUES("city", "#{invitation.city}");
                VALUES("phone_number", "#{invitation.phoneNumber}");
                VALUES("is_cancelled", "0");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{invitation.adminId}");
            }
        }.toString();
    }


    public String getSendInvitationCount(long exhibitorId) {
        return new SQL(){
            {
                SELECT("""
                        send_type as type, count(send_type) AS count
                        """);
                FROM("invitation_send_history");
                WHERE("exhibitor_company_id = #{exhibitorId}");
                WHERE("is_cancelled = 0");
                GROUP_BY("send_type");
            }
        }.toString();
    }


    public String getInvitationList(PageableInvitation search) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY id asc) AS no,
                        id, exhibitor_company_id as exhibitor_id, send_type as type,
                        file_id as file_id, barcode, email, name, company, job_title,
                        country, city, phone_number, is_cancelled as cancelled, cancel_reason, created_at
                        """);
                FROM("invitation_send_history");
                WHERE("exhibitor_company_id = #{search.exhibitorId}");
                if(InvitationType.BADGE.getType().equals(search.getBadeType())){
                    WHERE("send_type = #{search.badgeType}");
                }
                if(InvitationType.INVITATION.getType().equals(search.getBadeType())){
                    WHERE("send_type = #{search.badgeType}");
                }
                if("S".equals(search.getState())){
                    WHERE("is_cancelled = 0");
                }
                if("C".equals(search.getState())){
                    WHERE("is_cancelled = 1");
                }
                if("E".equals(search.getType())){
                    WHERE("email like concat('%',#{search.searchText},'%')");
                }
                if("N".equals(search.getType())){
                    WHERE("name like concat('%',#{search.searchText},'%')");
                }
                ORDER_BY("id DESC");
                LIMIT(search.getLength());
                OFFSET(search.getStart());
            }
        }.toString();
    }


    public String getInvitationCount(PageableInvitation search) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("invitation_send_history");
                WHERE("exhibitor_company_id = #{search.exhibitorId}");
                if(InvitationType.BADGE.getType().equals(search.getBadeType())){
                    WHERE("send_type = #{search.badgeType}");
                }
                if(InvitationType.INVITATION.getType().equals(search.getBadeType())){
                    WHERE("send_type = #{search.badgeType}");
                }
                if("S".equals(search.getState())){
                    WHERE("is_cancelled = 0");
                }
                if("C".equals(search.getState())){
                    WHERE("is_cancelled = 1");
                }
                if("E".equals(search.getType())){
                    WHERE("email like concat('%',#{search.searchText},'%')");
                }
                if("N".equals(search.getType())){
                    WHERE("name like concat('%',#{search.searchText},'%')");
                }
            }
        }.toString();
    }


    public String updateInvitation(long id, InvitationDTO invitation) {
        return new SQL(){
            {
                UPDATE("invitation_send_history");
                SET("name = #{invitation.name}");
                SET("company = #{invitation.company}");
                SET("job_title = #{invitation.jobTitle}");
                SET("country = #{invitation.country}");
                SET("city = #{invitation.city}");
                SET("phone_number = #{invitation.phoneNumber}");
                SET("is_cancelled = #{invitation.cancelled}");
                SET("cancel_reason = #{invitation.cancelReason}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{invitation.adminId}");
                WHERE("id = #{id}");

            }
        }.toString();
    }
}

package com.exporum.admin.domain.exhibitor.mapper;

import com.exporum.admin.domain.exhibitor.model.AddInvitationDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */
public class AddInvitationSqlProvider {


    public String addInvitation(AddInvitationDTO add) {
        return new SQL(){
            {
                INSERT_INTO("exhibitor_add_invitation");
                VALUES("exhibitor_company_id", "#{add.exhibitorId}");
                VALUES("badge_add_count", "#{add.badgeAddCount}");
                VALUES("invitation_add_count", "#{add.invitationAddCount}");
                VALUES("reason", "#{add.reason}");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{add.adminId}");
            }
        }.toString();
    }

    public String getAddInvitation(long exhibitorId) {
        return new SQL(){
            {
                SELECT("""
                        id, badge_add_count, invitation_add_count, reason, created_at
                        """);
                FROM("exhibitor_add_invitation");
                WHERE("exhibitor_company_id=#{exhibitorId}");
                WHERE("is_deleted=0");
                ORDER_BY("created_at DESC");
            }
        }.toString();
    }

    public String getTotalAddInvitation(long exhibitorId) {
        return new SQL(){
            {
                SELECT("""
                            SUM(badge_add_count) AS badge_add_count,
                            SUM(invitation_add_count) AS invitation_add_count
                        """);
                FROM("exhibitor_add_invitation");
                WHERE("exhibitor_company_id=#{exhibitorId}");
                WHERE("is_deleted=0");
                GROUP_BY("exhibitor_company_id");
            }
        }.toString();
    }

    public String deleteAddInvitation(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("exhibitor_add_invitation");
                SET("is_deleted=1");
                SET("deleted_at=sysdate()");
                SET("deleted_by=#{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

}

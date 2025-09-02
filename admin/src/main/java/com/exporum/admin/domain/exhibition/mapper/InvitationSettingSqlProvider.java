package com.exporum.admin.domain.exhibition.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */
public class InvitationSettingSqlProvider {
    public String getInvitationSetting(long exhibitionId) {
        return new SQL() {
            {
                SELECT("""
                        min_booth_size, max_booth_size, badge_count, invitation_count
                        """);
                FROM("exhibitor_badge_invitation_setting");
                WHERE("exhibition_id=#{exhibitionId}");
                WHERE("is_deleted=0");
                ORDER_BY("min_booth_size asc");
            }
        }.toString();
    }

}

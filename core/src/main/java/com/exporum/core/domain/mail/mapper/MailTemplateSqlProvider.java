package com.exporum.core.domain.mail.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */



public class MailTemplateSqlProvider {

    public String getBadgeTemplateData(String merchantUid){
        return new SQL(){
            {
                SELECT("""
                       tio.merchant_uid as order_number, u.first_name, u.last_name, u.company,
                       con.country_name_en as country, u.city, u.country_calling_number as calling_code, u.job_title,
                       u.mobile_number, u.email, concat(pay.paid_at, '(UTC+9)') as paid_at, pay.currency, pay.paid_amount as amount, u.email as receiver,
                       ex.venue as exhibition_venue, ex.city as exhibition_city, t.ticket_name as badge_name,
                       ti.issue_number, f.file_path as barcodePath
                       """);
                FROM("payment as pay");;
                JOIN("ticket_order tio  on pay.merchant_uid = tio.merchant_uid");
                JOIN("user as u on tio.user_id = u.id");
                JOIN("country as con on con.id = u.country_id");
                JOIN("ticket as t on t.id = tio.ticket_id");
                JOIN("exhibition as ex on ex.id = t.exhibition_id");
                JOIN("ticket_issue as ti on ti.order_id = tio.merchant_uid");
                JOIN("files as f on ti.file_id = f.id");
                WHERE("pay.merchant_uid = #{merchantUid}");

            }
        }.toString();
    }
}

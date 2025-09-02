package com.exporum.core.domain.order.mapper;

import com.exporum.core.domain.order.model.Order;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */
public class OrderSqlProvider {

    public String insertOrder(Order order) {
        return new SQL(){
            {
                INSERT_INTO("ticket_order");
                VALUES("merchant_uid", "#{order.merchantUid}");
                VALUES("user_id", "#{order.userId}");
                VALUES("ticket_id", "#{order.ticketId}");
                VALUES("currency_code", "#{order.currencyCode}");
                VALUES("verify_code", "#{order.verifyCode}");
                VALUES("order_status_code", "#{order.orderStatus}");
                VALUES("quantity", "#{order.quantity}");
                VALUES("amount", "#{order.amount}");
                VALUES("ip", "#{order.ip}");
                VALUES("referer", "#{order.referer}");
                VALUES("user_agent", "#{order.userAgent}");
                VALUES("first_name", "#{order.firstName}");
                VALUES("last_name", "#{order.lastName}");
                VALUES("company", "#{order.company}");
                VALUES("job_title", "#{order.jobTitle}");
                VALUES("country_id", "#{order.countryId}");
                VALUES("calling_code", "#{order.callingCode}");
                VALUES("mobile_number", "#{order.mobileNumber}");

            }
        }.toString();
    }

    public String getOrder(String merchantUid){
        return new SQL(){
            {
                SELECT("""
                        merchant_uid, user_id, ticket_id, currency_code, verify_code, order_status_code,
                        quantity, amount, ip, referer, user_agent, first_name, last_name, company, job_title, country_id, calling_code, mobile_number
                        """);
                FROM("ticket_order");
                WHERE("merchant_uid = #{merchantUid}");
            }
        }.toString();
    }

    public String updateOrderStatus(String merchantUid, String orderStatusCode){
        return new SQL(){
            {
                UPDATE("ticket_order");
                SET("order_status_code = #{orderStatusCode}");
                SET("updated_at = sysdate()");
                WHERE("merchant_uid = #{merchantUid}");
            }
        }.toString();

    }
}

package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.*;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */
public class TicketManageSqlProvider {

    public String getTicketList(PageableTicket ticket) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY id asc) AS no,
                        t.id, tc.code_name as type_name, t.ticket_type_code as type_code, 
                        t.ticket_name, t.ticket_info,
                        to_char(t.start_date, 'YYYY-MM-DD HH24:MI') as start_date,
                        to_char(t.end_date, 'YYYY-MM-DD HH24:MI') as end_date,
                        tp.currency_code as currency, tp.price,
                        t.is_buying as buying, t.created_at
                        """);
                FROM("ticket as t");
                JOIN("code as tc on tc.code = t.ticket_type_code");
                LEFT_OUTER_JOIN("ticket_price as tp on tp.ticket_id = t.id and tp.currency_code = 'USD'");
                WHERE("t.exhibition_id = #{ticket.exhibitionId}");
                WHERE("t.is_deleted = 0");
                ORDER_BY("t.id desc");
                LIMIT(ticket.getLength());
                OFFSET(ticket.getStart());
            }
        }.toString();
    }

    public String getTicketCount(PageableTicket ticket) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("ticket");
                WHERE("exhibition_id = #{ticket.exhibitionId}");
                WHERE("is_deleted = 0");
            }
        }.toString();
    }

    public String getTicketPaymentExcel(long ticketId) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY p.id asc) AS no,
                        p.id, o.merchant_uid, p.imp_uid, p.paid_amount as amount, p.currency,
                        p.pg_provider, p.pay_method, p.paid_at
                        """);
                FROM("payment as p");
                JOIN("ticket_order as o on p.merchant_uid = o.merchant_uid and p.status = 'paid'");
                WHERE("o.ticket_id = #{ticketId}");
                WHERE("o.order_status_code = 'Payment Completed'");
                ORDER_BY("p.id desc");
            }
        }.toString();
    }


    public String getTicketPaymentList(PageableTicketPayment ticketPayment) {
        return new SQL(){
            {
                SELECT("""
                        ROW_NUMBER() OVER (ORDER BY p.id asc) AS no,
                        p.id, o.merchant_uid, p.imp_uid, p.paid_amount as amount, p.currency,
                        p.pg_provider, p.pay_method, p.paid_at
                        """);
                FROM("payment as p");
                JOIN("ticket_order as o on p.merchant_uid = o.merchant_uid and p.status = 'paid'");
                WHERE("o.ticket_id = #{ticketPayment.ticketId}");
                WHERE("o.order_status_code = 'Payment Completed'");
                ORDER_BY("p.id desc");
                LIMIT(ticketPayment.getLength());
                OFFSET(ticketPayment.getStart());
            }
        }.toString();
    }

    public String getTicketPaymentCount(PageableTicketPayment ticketPayment) {
        return new SQL(){
            {
                SELECT("""
                        count(*)
                        """);
                FROM("payment as p");
                JOIN("ticket_order as o on p.merchant_uid = o.merchant_uid and p.status = 'paid'");
                WHERE("o.ticket_id = #{ticketPayment.ticketId}");
                WHERE("o.order_status_code = 'Payment Completed'");
                ORDER_BY("p.id desc");
            }
        }.toString();
    }

    public String getTicket(long id) {
        return new SQL(){
            {
                SELECT("""
                        t.id, t.is_buying as buying, ex.id as exhibition_id, ex.exhibition_name,
                        to_char(t.start_date, 'YYYY-MM-DD HH24:MI') as start_date,
                        to_char(t.end_date, 'YYYY-MM-DD HH24:MI') as end_date,
                        tc.code_name as type_name, t.ticket_type_code as type_code,
                        t.ticket_name, t.ticket_info
                        """);
                FROM("ticket as t");
                JOIN("exhibition as ex on ex.id = t.exhibition_id");
                JOIN("code as tc on tc.code = t.ticket_type_code");
                WHERE("t.id = #{id}");
            }
        }.toString();
    }


    public String getPriceList(long ticketId) {
        return new SQL(){
            {
                SELECT("""
                        id, ticket_id, currency_code as currency, price
                        """);
                FROM("ticket_price");
                WHERE("ticket_id =#{ticketId}");
            }
        }.toString();
    }

    public String getTicketTotalAmount(long ticketId) {
        return new SQL(){
            {
                SELECT("""
                        p.currency, count(*) as count, sum(p.paid_amount) as amount
                        """);
                FROM("payment as p");
                JOIN("ticket_order as o on p.merchant_uid = o.merchant_uid and p.status = 'paid'");
                WHERE("o.ticket_id = #{ticketId}");
                WHERE("o.order_status_code = 'Payment Completed'");
                GROUP_BY("p.currency");
            }
        }.toString();
    }

    public String insertTicket(TicketDTO ticket) {
        return new SQL(){
            {
                INSERT_INTO("ticket");
                VALUES("exhibition_id","#{ticket.exhibitionId}");
                VALUES("ticket_name","#{ticket.ticketName}");
                VALUES("ticket_info","#{ticket.ticketInfo}");
                VALUES("start_date","#{ticket.startDate}");
                VALUES("end_date","#{ticket.endDate}");
                VALUES("ticket_type_code", "#{ticket.typeCode}");
                VALUES("is_buying", "0");
                VALUES("created_at", "sysdate()");
                VALUES("created_by", "#{ticket.adminId}");
            }
        }.toString();
    }

    public String insertTicketPrice(Price price) {
        return new SQL(){
            {
                INSERT_INTO("ticket_price");
                VALUES("ticket_id", "#{price.ticketId}");
                VALUES("currency_code", "#{price.currency}");
                VALUES("price", "#{price.price}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();
    }

    public String updateTicket(long id, TicketDTO ticket) {
        return new SQL(){
            {
                UPDATE("ticket");
                SET("ticket_name = #{ticket.ticketName}");
                SET("ticket_info = #{ticket.ticketInfo}");
                SET("start_date = #{ticket.startDate}");
                SET("end_date = #{ticket.endDate}");
                SET("ticket_type_code = #{ticket.typeCode}");
                SET("updated_at = sysdate()");
                SET("updated_by = #{ticket.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String updatedTicketPrice(Price price) {
        return new SQL(){
            {
                UPDATE("ticket_price");
                SET("price = #{price.price}");
                SET("updated_at = sysdate()");
                WHERE("id = #{price.id}");
            }
        }.toString();
    }

    public String updatePublish(long id, TicketDTO ticket) {
        return new SQL(){
            {
                UPDATE("ticket");
                SET("is_buying   = #{ticket.buying}");
                SET("updated_at   = sysdate()");
                SET("updated_by   = #{ticket.adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String deleteTicket(long id, long adminId) {
        return new SQL(){
            {
                UPDATE("ticket");
                SET("is_buying   = 0");
                SET("is_deleted   = 1");
                SET("updated_at   = sysdate()");
                SET("updated_by   = #{adminId}");
                SET("deleted_at   = sysdate()");
                SET("deleted_by   = #{adminId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}

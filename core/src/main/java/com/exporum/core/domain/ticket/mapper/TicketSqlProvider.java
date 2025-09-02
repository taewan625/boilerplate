package com.exporum.core.domain.ticket.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

public class TicketSqlProvider {

    public String getTicket(long id){
        return new SQL(){
            {
                SELECT("""
                        t.id, exhibition_id, c.alpha_2 as country_code, e.year,
                        ticket_type_code as ticket_type,
                        ticket_name, ticket_info,
                        t.start_date, t.end_date
                        """);
                FROM("ticket as t");
                JOIN("exhibition as e ON e.id = t.exhibition_id");
                JOIN("country as c ON e.country_id = c.id");
                WHERE("t.id = #{id}");
            }
        }.toString();
    }


    public String getTicketList(long exhibitionId){
        return new SQL(){
            {
                SELECT("""
                        id, exhibition_id,
                        ticket_type_code as ticket_type,
                        ticket_name, ticket_info,
                        start_date, end_date
                        """);
                FROM("ticket");
                WHERE("is_buying = 1");
                WHERE("is_deleted = 0");

            }
        }.toString();
    }

    public String getTicketsForSale(){
        return new SQL(){
            {
                SELECT("""
                        t.id, exhibition_id, c.alpha_2 as country_code, e.year,
                        ticket_type_code as ticket_type,
                        t.ticket_name, t.ticket_info,
                        t.start_date, t.end_date
                        """);
                FROM("ticket as t");
                JOIN("exhibition as e ON e.id = t.exhibition_id");
                JOIN("country as c ON e.country_id = c.id");
                WHERE("NOW() BETWEEN t.start_date AND t.end_date");
                WHERE("t.is_buying = 1");
                WHERE("t.is_deleted = 0");
            }
        }.toString();
    }

    public String getTicketPrices(long ticketId){
        return new SQL(){
            {
                SELECT("""
                        id, currency_code as currency, price
                        """);
                FROM("ticket_price");
                WHERE("ticket_id = #{ticketId}");
            }
        }.toString();


    }
}

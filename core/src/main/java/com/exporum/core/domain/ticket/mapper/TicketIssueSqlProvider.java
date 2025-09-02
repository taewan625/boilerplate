package com.exporum.core.domain.ticket.mapper;

import com.exporum.core.domain.ticket.model.TicketIssueCancel;
import com.exporum.core.domain.ticket.model.TicketIssueDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */
public class TicketIssueSqlProvider {

    public String getBarcode(String orderId){
        return new SQL(){
            {
                SELECT("issue_number");
                FROM("ticket_issue");
                WHERE("order_id = #{orderId}");
            }
        }.toString();
    }



    public String insertTicketIssue(TicketIssueDTO ticketIssue){
        return new SQL(){
            {
                INSERT_INTO("ticket_issue");
                VALUES("order_id", "#{ticketIssue.orderId}");
                VALUES("file_id", "#{ticketIssue.fileId}");
                VALUES("issue_number", "#{ticketIssue.issueNumber}");
                VALUES("created_at", "sysdate()");
            }
        }.toString();

    }

    public String updateTicketIssueCancel(TicketIssueCancel ticketIssueCancel){
        return new SQL(){
            {
                UPDATE("ticket_issue");
                SET("is_cancelled = 1");
                SET("cancel_date = sysdate()");
                SET("updated_at = sysdate()");
                SET("updated_by = #{ticketIssueCancel.adminId}");
                WHERE("order_id = #{ticketIssueCancel.merchantId}");
                WHERE("issue_number = #{ticketIssueCancel.barcode}");
            }
        }.toString();
    }
}

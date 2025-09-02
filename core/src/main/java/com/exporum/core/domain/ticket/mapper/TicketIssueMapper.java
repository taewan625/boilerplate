package com.exporum.core.domain.ticket.mapper;

import com.exporum.core.domain.ticket.model.TicketIssueCancel;
import com.exporum.core.domain.ticket.model.TicketIssueDTO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */
public interface TicketIssueMapper {

    @InsertProvider(type = TicketIssueSqlProvider.class, method = "insertTicketIssue")
    int insertTicketIssue(@Param("ticketIssue") TicketIssueDTO ticketIssue);

    @UpdateProvider(type = TicketIssueSqlProvider.class, method = "updateTicketIssueCancel")
    int updateTicketIssueCancel(@Param("ticketIssueCancel") TicketIssueCancel ticketIssueCancel);


    @SelectProvider(type = TicketIssueSqlProvider.class, method = "getBarcode")
    String getBarcode(@Param("orderId") String orderId);
}

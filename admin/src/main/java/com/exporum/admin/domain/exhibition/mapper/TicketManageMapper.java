package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */

@Mapper
public interface TicketManageMapper {

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicketList")
    List<TicketList> getTicketList(@Param("ticket") PageableTicket ticket);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicketCount")
    long getTicketCount(@Param("ticket") PageableTicket ticket);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicketPaymentExcel")
    List<TicketPaymentList> getTicketPaymentExcel(@Param("ticketId") long ticketId);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicketPaymentList")
    List<TicketPaymentList> getTicketPaymentList(@Param("ticketPayment") PageableTicketPayment ticketPayment);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicketPaymentCount")
    long  getTicketPaymentCount(@Param("ticketPayment") PageableTicketPayment ticketPayment);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicket")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "prices", many = @Many(select = "getPriceList")),
            @Result(column = "id", property = "amounts", many = @Many(select = "getTicketTotalAmount")),
    })
    Ticket getTicket(@Param("id") long id);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getPriceList")
    List<Price> getPriceList(@Param("ticketId") long ticketId);

    @SelectProvider(type = TicketManageSqlProvider.class, method = "getTicketTotalAmount")
    List<TicketTotalAmount> getTicketTotalAmount(@Param("ticketId") long ticketId);


    @InsertProvider(type = TicketManageSqlProvider.class, method = "insertTicket")
    @Options(useGeneratedKeys = true, keyProperty = "ticket.id", keyColumn = "id")
    int insertTicket(@Param("ticket") TicketDTO ticket);

    @InsertProvider(type = TicketManageSqlProvider.class, method = "insertTicketPrice")
    int insertTicketPrice(@Param("price") Price price);


    @UpdateProvider(type = TicketManageSqlProvider.class, method = "updateTicket")
    int updateTicket(@Param("id") long id, @Param("ticket") TicketDTO ticket);

    @UpdateProvider(type = TicketManageSqlProvider.class, method = "updatedTicketPrice")
    int updatedTicketPrice(@Param("price") Price price);

    @UpdateProvider(type = TicketManageSqlProvider.class, method = "updatePublish")
    int updatePublish(@Param("id") long id, @Param("ticket") TicketDTO ticket);

    @UpdateProvider(type = TicketManageSqlProvider.class, method = "deleteTicket")
    int deleteTicket(@Param("id") long id, @Param("adminId") long adminId);

}

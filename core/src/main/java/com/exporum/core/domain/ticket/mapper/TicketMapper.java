package com.exporum.core.domain.ticket.mapper;

import com.exporum.core.domain.ticket.model.Ticket;
import com.exporum.core.domain.ticket.model.TicketPrice;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

@Mapper
public interface TicketMapper {

    @SelectProvider(type = TicketSqlProvider.class, method = "getTicket")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "prices", many = @Many(select = "getTicketPrices"))
    })
    Ticket getTicket(@Param("id") long id);

    @SelectProvider(type = TicketSqlProvider.class, method = "getTicketList")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "prices", many = @Many(select = "getTicketPrices"))
    })
    List<Ticket> getTicketList(@Param("exhibitionId") long exhibitionId);


    @SelectProvider(type = TicketSqlProvider.class, method = "getTicketsForSale")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "prices", many = @Many(select = "getTicketPrices"))
    })
    List<Ticket> getTicketsForSale();


    @SelectProvider(type = TicketSqlProvider.class, method = "getTicketPrices")
    List<TicketPrice> getTicketPrices(@Param("ticketId") long ticketId);
}

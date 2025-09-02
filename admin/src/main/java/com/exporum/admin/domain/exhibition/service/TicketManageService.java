package com.exporum.admin.domain.exhibition.service;

import com.exporum.admin.common.service.CommonService;
import com.exporum.admin.domain.attendee.model.AttendeeExcel;
import com.exporum.admin.domain.exhibition.mapper.TicketManageMapper;
import com.exporum.admin.domain.exhibition.model.*;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.admin.helper.DateTimeHelper;
import com.exporum.core.domain.excel.ExcelGenerator;
import com.exporum.core.domain.excel.OneSheetExcelGenerator;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class TicketManageService {

    private final TicketManageMapper ticketManageMapper;

    private final CommonService commonService;

    @Value("${exhibition.default-time-zone}")
    private String defaultTimeZone;

    public Ticket getTicket(long id) {
        Ticket ticket = Optional.ofNullable(ticketManageMapper.getTicket(id)).orElseThrow(DataNotFoundException::new);

        String timezone = commonService.getExhibitionTimezone(ticket.getExhibitionId());

        String startDate = DateTimeHelper.getConvertLocalTime(defaultTimeZone, timezone, ticket.getStartDate());
        String endDate = DateTimeHelper.getConvertLocalTime(defaultTimeZone, timezone, ticket.getEndDate());;

        ticket.setLocalStartDate(startDate);
        ticket.setLocalEndDate(endDate);

        return ticket;
    }

    @Transactional
    public void updatePublish(long id, TicketDTO ticket) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        ticket.setAdminId(adminId);

        if(!(ticketManageMapper.updatePublish(id,ticket)>0)){
            throw new OperationFailException();
        }
    }

    @Transactional
    public void deleteTicket(long id) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if(!(ticketManageMapper.deleteTicket(id,adminId)>0)){
            throw new OperationFailException();
        }
    }


    @Transactional
    public void insertTicketProcess(TicketDTO ticket) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        ticket.setAdminId(adminId);

        String timezone = commonService.getExhibitionTimezone(ticket.getExhibitionId());

        String startDate = DateTimeHelper.getConvertLocalTime(timezone, defaultTimeZone, ticket.getStartDate());
        String endDate = DateTimeHelper.getConvertLocalTime(timezone, defaultTimeZone, ticket.getEndDate());;

        ticket.setStartDate(startDate);
        ticket.setEndDate(endDate);

        this.insertTicket(ticket);

        if(ticket.getPrices().isEmpty()){
            throw new OperationFailException();
        }

        for(Price price: ticket.getPrices()){
            price.setTicketId(ticket.getId());
            this.insertTicketPrice(price);
        }
    }

    @Transactional
    public void updateTicketProcess(long id,TicketDTO ticket) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        ticket.setAdminId(adminId);

        String timezone = commonService.getExhibitionTimezone(ticket.getExhibitionId());

        String startDate = DateTimeHelper.getConvertLocalTime(timezone, defaultTimeZone, ticket.getStartDate());
        String endDate = DateTimeHelper.getConvertLocalTime(timezone, defaultTimeZone, ticket.getEndDate());;

        ticket.setStartDate(startDate);
        ticket.setEndDate(endDate);

        this.updateTicket(id, ticket);

        if(!ticket.getPrices().isEmpty()){
            for(Price price: ticket.getPrices()){
                updateTicketPrice(price);
            }
        }
    }

    public void getTicketList(PageableTicket pageable) {
        long recordsTotal = ticketManageMapper.getTicketCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        String timezone = commonService.getExhibitionTimezone(pageable.getExhibitionId());

        List<TicketList> ticketList = ticketManageMapper.getTicketList(pageable);

        for(TicketList ticket: ticketList){
            String startDate = DateTimeHelper.getConvertLocalTime(defaultTimeZone, timezone, ticket.getStartDate());
            String endDate = DateTimeHelper.getConvertLocalTime(defaultTimeZone, timezone, ticket.getEndDate());;

            ticket.setLocalStartDate(startDate);
            ticket.setLocalEndDate(endDate);
        }

        pageable.setData(ticketList);
    }


    public void getTicketPaymentList(PageableTicketPayment pageable) {
        long recordsTotal = ticketManageMapper.getTicketPaymentCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(ticketManageMapper.getTicketPaymentList(pageable));
    }


    public ResponseEntity<StreamingResponseBody> excelDownload(long id, HttpServletResponse response) throws IOException {


        List<TicketPaymentList> data = ticketManageMapper.getTicketPaymentExcel(id);

        ExcelGenerator<TicketPaymentList> excelFile = new OneSheetExcelGenerator<>(data, TicketPaymentList.class);
        StreamingResponseBody responseBody = outputStream -> excelFile.write(response.getOutputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + "ticket_payment.xlsx")
                .body(responseBody);
    }

    private void insertTicket(TicketDTO ticket) {
        if(!(ticketManageMapper.insertTicket(ticket)>0)) {
            throw new OperationFailException();
        }
    }

    private void insertTicketPrice(Price price) {
        if(!(ticketManageMapper.insertTicketPrice(price)>0)) {
            throw new OperationFailException();
        }
    }

    private void updateTicket(long id, TicketDTO ticket) {
        if(!(ticketManageMapper.updateTicket(id, ticket)>0)) {
            throw new OperationFailException();
        }
    }

    private void updateTicketPrice(Price price) {
        if(!(ticketManageMapper.updatedTicketPrice(price)>0)) {
            throw new OperationFailException();
        }
    }
}

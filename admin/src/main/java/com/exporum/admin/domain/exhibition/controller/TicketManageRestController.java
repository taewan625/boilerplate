package com.exporum.admin.domain.exhibition.controller;

import com.exporum.admin.domain.exhibition.model.PageableTicket;
import com.exporum.admin.domain.exhibition.model.PageableTicketPayment;
import com.exporum.admin.domain.exhibition.model.Ticket;
import com.exporum.admin.domain.exhibition.model.TicketDTO;
import com.exporum.admin.domain.exhibition.service.TicketManageService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class TicketManageRestController {

    private final TicketManageService ticketManageService;

    @PostMapping("/ticket")
    public PageableTicket getPageableTicketList(PageableTicket search) {
        ticketManageService.getTicketList(search);
        return search;
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<OperationResponse> getTicket(@PathVariable long id) {
        Ticket ticket = ticketManageService.getTicket(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, ticket));
    }

    @PutMapping("/ticket/{id}")
    public ResponseEntity<OperationResponse> updateTicket(@PathVariable long id, @RequestBody TicketDTO ticket) {
        ticketManageService.updateTicketProcess(id, ticket);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<OperationResponse> deleteTicket(@PathVariable long id) {
        ticketManageService.deleteTicket(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/ticket/{id}/publish")
    public ResponseEntity<OperationResponse> updateTicketPublish(@PathVariable long id, @RequestBody TicketDTO ticket) {
        ticketManageService.updatePublish(id, ticket);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/ticket/register")
    public ResponseEntity<OperationResponse> insertTicket(@RequestBody TicketDTO ticket) {
        ticketManageService.insertTicketProcess(ticket);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/ticket/payment")
    public PageableTicketPayment getPageableTicketPaymentList(PageableTicketPayment search) {
        ticketManageService.getTicketPaymentList(search);
        return search;
    }

    @GetMapping("/ticket/excel/{id}")
    public Callable<ResponseEntity<StreamingResponseBody>> excelDownloadTicketPayment(@PathVariable long id, HttpServletResponse response) throws IOException {
        return () -> ticketManageService.excelDownload(id,response);
    }


}

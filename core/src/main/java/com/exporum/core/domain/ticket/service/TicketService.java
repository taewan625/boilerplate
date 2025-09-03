package com.exporum.core.domain.ticket.service;

import com.exporum.core.domain.question.service.QuestionService;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.domain.ticket.mapper.TicketIssueMapper;
import com.exporum.core.domain.ticket.mapper.TicketMapper;
import com.exporum.core.domain.ticket.model.Ticket;
import com.exporum.core.domain.ticket.model.TicketIssueCancel;
import com.exporum.core.domain.ticket.model.TicketIssueDTO;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import com.exporum.core.helper.BarcodeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketMapper ticketMapper;

    private final TicketIssueMapper ticketIssueMapper;

    private final QuestionService questionService;

    private final StorageService storageService;

    private final BarcodeHelper barcodeHelper;

    @Value("${ncp.object-storage.path.badge}")
    private String badgePath;

    public Ticket getTicket(long ticketId) throws DataNotFoundException {
        return Optional.ofNullable(ticketMapper.getTicket(ticketId)).orElseThrow(DataNotFoundException::new);
    }

    public List<Ticket> getTicketList(long exhibitionId) {
        return ticketMapper.getTicketList(exhibitionId);
    }

    public List<Ticket> getTicketsForSale() {
        return ticketMapper.getTicketsForSale();
    }


    public String createTicketIssue(String merchantId) {
        FileDTO file = storageService.ncpUpload(barcodeHelper.getBarcode(), badgePath);

        String barcode = file.getOriginFileName().contains(".") ?
                file.getOriginFileName().substring(0, file.getOriginFileName().lastIndexOf(".")) :
                file.getOriginFileName();


        TicketIssueDTO ticketIssueDTO = TicketIssueDTO.builder()
                .orderId(merchantId)
                .issueNumber(barcode)
                .fileId(file.getId())
                .build();

        if(!(ticketIssueMapper.insertTicketIssue(ticketIssueDTO) > 0)) {
            throw new OperationFailException("ticketIssue create failed");
        }

        return barcode;
    }

    @Transactional
    public void updateTicketIssueCancel(TicketIssueCancel ticketIssueCancel) {
        if(!(ticketIssueMapper.updateTicketIssueCancel(ticketIssueCancel) > 0)) {
            throw new OperationFailException("ticketIssue update failed");
        }
    }

    public String getBarcode(String orderId){
        return Optional.ofNullable(ticketIssueMapper.getBarcode(orderId)).orElseThrow(DataNotFoundException::new);
    }
}

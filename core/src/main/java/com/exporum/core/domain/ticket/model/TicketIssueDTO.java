package com.exporum.core.domain.ticket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Getter
@Setter
public class TicketIssueDTO {

    private String orderId;
    private long fileId;
    private String issueNumber;


    @Builder
    public TicketIssueDTO(String orderId, long fileId, String issueNumber) {
        this.orderId = orderId;
        this.fileId = fileId;
        this.issueNumber = issueNumber;
    }

}

package com.exporum.core.domain.ticket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 21.
 * @description :
 */
@Getter
@Setter
public class TicketIssueCancel {
    private String merchantId;
    private String barcode;
    private long adminId;

    @Builder
    public TicketIssueCancel(String merchantId, String barcode, long adminId) {
        this.merchantId = merchantId;
        this.barcode = barcode;
        this.adminId = adminId;
    }
}

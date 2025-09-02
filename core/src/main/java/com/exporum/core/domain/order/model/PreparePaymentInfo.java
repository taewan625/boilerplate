package com.exporum.core.domain.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 10.
 * @description :
 */

@Getter
@Setter
public class PreparePaymentInfo {

    private String noticeUrl;
    private String language;
    private String merchantId;
    private String ticketName;
    private int ticketQuantity;
    private String buyerEmail;
    private String buyerName;
    private String buyerTel;
    private int amount;
    private String currency;


    @Builder
    public PreparePaymentInfo(String merchantId, String ticketName,int ticketQuantity, int amount,String language, String currency, String buyerEmail, String buyerName, String buyerTel, String noticeUrl) {
        this.merchantId = merchantId;
        this.ticketName = ticketName;
        this.ticketQuantity = ticketQuantity;
        this.amount = amount;
        this.language = language;
        this.currency = currency;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.buyerTel = buyerTel;
        this.noticeUrl = noticeUrl;
    }

}

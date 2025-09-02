package com.exporum.core.domain.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 6.
 * @description :
 */

@Getter
@Setter
public class Order {

    private String merchantUid;
    private long userId;
    private long ticketId;
    private String currencyCode;
    private String verifyCode;
    private String orderStatus;
    private int quantity;
    private int amount;
    private String ip;
    private String referer;
    private String userAgent;

    private String firstName;
    private String lastName;
    private String company;
    private String jobTitle;
    private long countryId;
    private String callingCode;
    private String mobileNumber;


    @Builder
    public Order(String merchantUid, long userId, long ticketId, String currencyCode, String verifyCode, String orderStatus, int quantity, int amount, String ip, String referer,String userAgent,
                 String firstName, String lastName, String company, String jobTitle, long countryId, String callingCode, String mobileNumber
    ) {
        this.merchantUid = merchantUid;
        this.userId = userId;
        this.ticketId = ticketId;
        this.currencyCode = currencyCode;
        this.verifyCode = verifyCode;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.amount = amount;
        this.ip = ip;
        this.referer = referer;
        this.userAgent = userAgent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.jobTitle = jobTitle;
        this.countryId = countryId;
        this.callingCode = callingCode;
        this.mobileNumber = mobileNumber;
    }
}

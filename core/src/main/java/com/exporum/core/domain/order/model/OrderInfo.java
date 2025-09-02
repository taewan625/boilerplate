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
public class OrderInfo {

    private long userId;
    private long ticketId;
    private String currency;
    private int ticketQuantity;
    private String firstName;
    private String lastName;
    private String company;
    private String jobTitle;

    private long countryId;
    private String callingCode;
    private String mobileNumber;

    @Builder
    public OrderInfo(long userId, long ticketId, String currency, int ticketQuantity, String firstName, String lastName, String company, String jobTitle,
                     long countryId, String callingCode, String mobileNumber) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.currency = currency;
        this.ticketQuantity = ticketQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.jobTitle = jobTitle;
        this.countryId = countryId;
        this.callingCode = callingCode;
        this.mobileNumber = mobileNumber;
    }

}

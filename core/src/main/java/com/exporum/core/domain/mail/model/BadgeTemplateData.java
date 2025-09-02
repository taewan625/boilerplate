package com.exporum.core.domain.mail.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Getter
@Setter
public class BadgeTemplateData {

    private String orderNumber;
    private String badgeName;
    private String firstName;
    private String lastName;
    private String company;
    private String country;
    private String city;
    private String callingCode;
    private String mobileNumber;
    private String email;
    private String paidAt;
    private String currency;
    private int amount;
    private String jobTitle;
    private String receiver;
    private String exhibitionCity;
    private String exhibitionVenue;
    private String issueNumber;
    private String barcodePath;


}

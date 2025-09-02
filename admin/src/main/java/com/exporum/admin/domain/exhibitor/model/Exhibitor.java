package com.exporum.admin.domain.exhibitor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Getter
@Setter
public class Exhibitor {
    private long id;
    private long exhibitionId;

    private boolean scaiSales;
    private boolean scaMembership;


    private String company;
    private String brand;
    private String industry;
    private String industryCode;
    private String country;
    private String countryId;
    private String sponsor;
    private String sponsorCode;
    private String earlyApplication;
    private String earlyApplicationCode;
    private String applicationType;
    private String applicationTypeCode;
    private String applicationDate;
    private String homepage;
    private String instagram;
    private String facebook;
    private String etcSns;
    private String contactName;
    private String jobTitle;
    private String email;
    private String callingCode;
    private String phoneNumber;
    private String address;
    private String boothNumber;
    private int boothCount;
    private String description;
    private boolean mutualTaxExemption;

    private InvitationCount invitation;
}

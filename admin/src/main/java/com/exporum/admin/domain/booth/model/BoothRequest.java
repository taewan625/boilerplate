package com.exporum.admin.domain.booth.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */


@Getter
@Setter
public class BoothRequest {
    private long no;
    private long id;
    private long countryId;
    private long exhibitionId;
    private String country;
    private String brandName;
    private String exhibition;
    private String industryCode;
    private String industry;
    private String company;
    private String contactPerson;
    private String jobTitle;
    private String email;
    private String secondEmail;
    private String officeCallingCode;
    private String officeNumber;
    private String mobileCallingCode;
    private String mobileNumber;
    private String address;
    private String homepage;
    private String facebook;
    private String instagram;
    private String etcSns;
    private String introduction;
    private boolean check;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;

}

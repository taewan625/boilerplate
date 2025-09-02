package com.exporum.admin.domain.brand.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 13.
 * @description :
 */

@Getter
@Setter
public class Brand {
    private long id;
    private long companyId;
    private String brand;
    private String company;
    private String industry;
    private String industryCode;
    private String country;
    private String countryId;
    private String address;
    private String managerName;
    private String jobTitle;
    private String email;
    private String officeCalling;
    private String officeNumber;
    private String mobileCalling;
    private String mobileNumber;
    private long fileId;
    private String filePath;
    private String introduction;
    private String homepage;
    private String instagram;
    private String facebook;
    private String etcSns;
    private String booth;
    private int approve;
    private long exhibitionId;
    private String exhibitionName;
}

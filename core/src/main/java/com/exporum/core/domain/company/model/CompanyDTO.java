package com.exporum.core.domain.company.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Getter
@Setter
public class CompanyDTO {

    private long id;
    private long countryId;
    private String industryCode;
    private String companyName;
    private String email;
    private String countryCallingNumber;
    private String officeNumber;
    private String companyNameEn;
    private String companyAddress;
    private String homepage;
    private String etcIndustry;

    @Builder
    public CompanyDTO(long id, long countryId, String industryCode, String companyName, String email, String countryCallingNumber,
                      String officeNumber, String companyNameEn, String companyAddress, String homepage, String etcIndustry) {
        this.id = id;
        this.countryId = countryId;
        this.industryCode = industryCode;
        this.companyName = companyName;
        this.email = email;
        this.countryCallingNumber = countryCallingNumber;
        this.officeNumber = officeNumber;
        this.companyNameEn = companyNameEn;
        this.companyAddress = companyAddress;
        this.homepage = homepage;
        this.etcIndustry = etcIndustry;
    }

}

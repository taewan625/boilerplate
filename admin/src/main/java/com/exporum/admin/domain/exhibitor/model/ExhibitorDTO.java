package com.exporum.admin.domain.exhibitor.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */

@Getter
@Setter
public class ExhibitorDTO {

    private long id;

    @Min(value = 1, message = "is a required field.")
    private long countryId;
    @Min(value = 1, message = "is a required field.")
    private long exhibitionId;

    @NotBlank(message = "is a required field.")
    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String applicationType;

    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String industry;

    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String sponsorCode;

    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String earlyApplicationCode;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String companyName;
    private String companyNameEn;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String brandName;
    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String homepage;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String instagram;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String facebook;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String etcSns;
    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    private String contactName;
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    private String jobTitle;
    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    private String email;


    @Size(max = 10, message = "Must be 10 characters or fewer.")
    private String callingCode;

    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String phoneNumber;

    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String address;

    private String boothNumber;
    private int boothCount;

    private boolean scaiSales;
    private boolean scaMembership;

    @NotBlank(message = "is a required field.")
    private String applicationDate;

    @Size(max = 5000, message = "Must be 5000 characters or fewer.")
    private String description;

    private long adminId;
}

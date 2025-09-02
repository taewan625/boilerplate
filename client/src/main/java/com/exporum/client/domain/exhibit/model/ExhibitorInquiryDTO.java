package com.exporum.client.domain.exhibit.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Getter
@Setter
public class ExhibitorInquiryDTO {

    @Min(value = 1, message = "Please select an option.")
    private long countryId;

    @Min(value = 1, message = "is a required field.")
    private long exhibitionId;

    @NotBlank(message = "is a required field.")
    private String industryCode;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String companyName;

    @Size(max = 300, message = "Must be 100 characters or fewer.")
    private String brandName;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String managerName;

    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String jobTitle;

    @NotBlank(message = "is a required field.")
    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String email;

    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String secondEmail;


    private String officeCallingNumber;
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String officeNumber;

    private String mobileCallingNumber;
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String mobileNumber;


    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String companyAddress;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String homepage;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String facebook;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String instagram;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String etcSns;

    private String introduction;


}

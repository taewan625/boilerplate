package com.exporum.client.domain.exhibit.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Getter
@Setter
public class ExhibitorDTO {
    private long id;
    private long companyId;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String companyName;

    private String companyNameEn;

    @NotBlank(message = "is a required field.")
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String brandName;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String managerName;

    @Size(max = 50, message = "Must be 50 characters or fewer.")
    private String department;

    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String jobTitle;

    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    private String email;


    private String managerCallingNumber;

    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String mobileNumber;


    @NotBlank(message = "is a required field.")
    private String officeCallingNumber;
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String officeNumber;

    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String companyAddress;
    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String homepage;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String facebook;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String instagram;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String twitter;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String etcSns;


    private String introduction;

    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    private String industryCode;

    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String etcIndustry;

    @Min(value = 1, message = "is a required field.")
    private long countryId;

    @Min(value = 1, message = "is a required field.")
    private long exhibitionId;


    @NotNull(message = "is a required field.")
    private MultipartFile file;

    private long fileId;

}

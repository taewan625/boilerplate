package com.exporum.admin.domain.brand.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 13.
 * @description :
 */

@Getter
@Setter
public class BrandDTO {


    MultipartFile file;

    private long companyId;
    private long countryId;

    @NotBlank(message = "is a required field.")
    private String industryCode;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String companyName;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String companyNameEn;

    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    @Email(message = "Invalid email format.")
    private String companyEmail;

    private String officeCallingCode;
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String officeNumber;

    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String address;

    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String homepage;

    private String booth;

    private long fileId;

    @NotBlank(message = "is a required field.")
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String brandName;

    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String facebook;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String instagram;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String twitter;
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String etcSns;

    private String introduction;
    private int approve;


    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String managerName;
    @Size(max = 255, message = "Must be 255 characters or fewer.")
    private String jobTitle;

    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    @Email(message = "Invalid email format.")
    private String managerEmail;

    private String managerCallingCode;

    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String mobileNumber;

    private long adminId;
}

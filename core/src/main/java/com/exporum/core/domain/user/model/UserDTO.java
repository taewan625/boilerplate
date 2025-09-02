package com.exporum.core.domain.user.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 6.
 * @description :
 */


@Getter
@Setter
public class UserDTO {

    private long userId;

    @Min(value = 1, message = "is a required field.")
    private long countryId;

    private String prefixCode;

    @NotBlank(message = "is a required field.")
    @Size(max = 200, message = "Must be 200 characters or fewer.")
    private String email;

    private String password;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String firstName;


    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String lastName;

    @AssertTrue(message = "You must accept the terms and conditions.")
    private boolean policyAgree;

    @AssertTrue(message = "You must accept the terms and conditions.")
    private boolean privacyAgree;

    @NotBlank(message = "is a required field.")
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String city;

    @NotBlank(message = "is a required field.")
    @Size(max = 100, message = "Must be 100 characters or fewer.")
    private String company;

    @NotBlank(message = "is a required field.")
    @Size(max = 500, message = "Must be 500 characters or fewer.")
    private String jobTitle;

    private String countryCallingNumber;

    private String mobileNumber;
}

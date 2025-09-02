package com.exporum.core.domain.user.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */

@Getter
@Setter
public class User {

    private long id;
    private long countryId;
    private String countryCode;
    private String country;
    private String prefixCode;
    private String prefix;
    private String email;
    private String firstName;
    private String lastName;
    private String countryCallingNumber;
    private String mobileNumber;
    private boolean policyAgree;
    private boolean privacyAgree;
    private String city;
    private String company;
    private String jobTitle;

}

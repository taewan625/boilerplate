package com.exporum.core.entity;

import com.exporum.core.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class Attendee extends BaseEntity {

    private long exhibitionId;
    private long countryId;
    private String attendeeCode;
    private String prefixCode;
    private String firstName;
    private String lastName;
    private String countryCallingNumber;
    private String mobileNumber;
    private String email;
    private boolean isPolicyAgree;
    private  boolean isPrivacyAgree;
    private String city;
    private String company;
    private String jobTitle;

}

package com.exporum.client.domain.exhibit.model;

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
public class ExhibitorManagerDTO {

    private long exhibitorId;
    private String managerName;
    private String department;
    private String jobTitle;
    private String email;
    private String callingNumber;
    private String mobileNumber;


    @Builder
    public ExhibitorManagerDTO(long exhibitorId, String managerName, String department, String jobTitle, String email, String callingNumber, String mobileNumber) {
        this.exhibitorId = exhibitorId;
        this.managerName = managerName;
        this.department = department;
        this.jobTitle = jobTitle;
        this.email = email;
        this.callingNumber = callingNumber;
        this.mobileNumber = mobileNumber;
    }

}

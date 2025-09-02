package com.exporum.admin.domain.exhibitor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Getter
@Setter
public class ExhibitorList {

    private long id;
    private long no;
    private long exhibitionId;
    private int boothCount;
    private String company;
    private String brand;
    private String country;
    private String industry;
    private String sponsor;
    private String applicationType;
    private String applicationDate;

    private InvitationCount invitation;
}

package com.exporum.admin.domain.exhibitor.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 19.
 * @description :
 */

@Getter
@Setter
public class InvitationDTO {
    private long exhibitionId;

    private String type;
    private String email;
    private String name;
    private String company;
    private String jobTitle;
    private String country;
    private String city;
    private String phoneNumber;

    private String barcode;
    private long fileId;

    private boolean cancelled;
    private String cancelReason;

    private String barcodePath;

    private long adminId;
}

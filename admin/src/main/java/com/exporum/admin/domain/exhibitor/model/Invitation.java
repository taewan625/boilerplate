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
public class Invitation {
    private long no;
    private long id;
    private long exhibitorId;
    private String type;
    private long fileId;
    private String barcode;
    private String email;
    private String name;
    private String company;
    private String jobTitle;
    private String country;
    private String city;
    private String phoneNumber;
    private boolean cancelled;
    private String cancelReason;
    private String createdAt;
}

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
public class AddInvitation {

    private long id;
    private int badgeAddCount;
    private int invitationAddCount;
    private String reason;
    private String createdAt;

}

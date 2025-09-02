package com.exporum.admin.domain.exhibitor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 18.
 * @description :
 */

@Getter
@Setter
public class InvitationCount {
    private int sendBadgeCount;
    private int sendInvitationCount;
    private int badgeCount;
    private int invitationCount;


    @Builder
    public InvitationCount(int sendBadgeCount, int sendInvitationCount, int badgeCount, int invitationCount) {
        this.sendBadgeCount = sendBadgeCount;
        this.sendInvitationCount = sendInvitationCount;
        this.badgeCount = badgeCount;
        this.invitationCount = invitationCount;
    }
}

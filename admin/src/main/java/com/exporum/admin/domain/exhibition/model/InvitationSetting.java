package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Getter
@Setter
public class InvitationSetting {
    private int minBoothSize;
    private int maxBoothSize;
    private int badgeCount;
    private int invitationCount;
}

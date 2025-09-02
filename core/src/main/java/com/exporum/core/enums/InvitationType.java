package com.exporum.core.enums;

import com.exporum.core.exception.OperationFailException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 19.
 * @description :
 */

@Getter
@RequiredArgsConstructor
public enum InvitationType {

    BADGE("EXHIBITOR_BADGE"),
    INVITATION("INVITATION");

    private final String type;

    public static InvitationType from(String type) {
        for (InvitationType invitationType : InvitationType.values()) {
            if (invitationType.getType().equalsIgnoreCase(type)) {
                return invitationType;
            }
        }
        throw new OperationFailException("Invalid InvitationType: " + type);
    }

    public static boolean isValid(String type) {
        for (InvitationType invitationType : InvitationType.values()) {
            if (invitationType.getType().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

}

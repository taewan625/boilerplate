package com.exporum.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 23.
 * @description :
 */

@Getter
@RequiredArgsConstructor
public enum BulletinBoardSystemType {

    BULLETIN_BOARD_SYSTEM_TYPE_NOTICE("NOTICE"),
    BULLETIN_BOARD_SYSTEM_TYPE_PRESS("PRESS");

    private final String type;
}

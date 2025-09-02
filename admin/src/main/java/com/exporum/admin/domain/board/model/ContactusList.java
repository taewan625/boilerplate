package com.exporum.admin.domain.board.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Getter
@Setter
public class ContactusList {
    private long no;
    private long id;
    private String contactor;
    private String inquiry;
    private String title;
    private boolean answer;
    private String createdAt;
}

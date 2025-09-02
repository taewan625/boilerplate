package com.exporum.admin.domain.board.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Getter
@Setter
public class BoardList {

    private long no;
    private long id;
    private String title;
    private boolean disabled;
    private String createdBy;
    private String createdAt;
    private boolean attached;
}

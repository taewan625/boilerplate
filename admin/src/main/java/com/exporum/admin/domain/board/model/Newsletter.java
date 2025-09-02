package com.exporum.admin.domain.board.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */

@Getter
@Setter
public class Newsletter {

    private long id;
    private long logoFileId;
    private long htmlFileId;
    private String title;
    private String content;
    private String issueDate;
    private boolean enable;
    private boolean deleted;

}

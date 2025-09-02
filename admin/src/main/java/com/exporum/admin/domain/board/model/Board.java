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
public class Board {
    private long id;

    private String title;
    private String content;
    private long fileId;
    private String uuid;
    private String filename;
    private int fileSize;
    private boolean disabled;
    private boolean attached;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

}

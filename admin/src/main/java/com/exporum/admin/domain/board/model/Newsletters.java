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
public class Newsletters {
    private long no;

    private long id;
    private long logoFileId;
    private String logoFileName;
    private String logoFilePath;
    private long htmlFileId;
    private String htmlFileName;

    private String title;
    private String content;
    private String issueDate;
    private boolean enable;
    private boolean deleted;

    private boolean logoAttached;
    private String logoUuid;

    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;

}

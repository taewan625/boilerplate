package com.exporum.client.domain.notice.model;


import lombok.Getter;
import lombok.Setter;

/**
 * NewsletterDetail.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 8. 14.
 * @description:
 */
@Getter
@Setter
public class NewsletterDetail {
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

    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;

}

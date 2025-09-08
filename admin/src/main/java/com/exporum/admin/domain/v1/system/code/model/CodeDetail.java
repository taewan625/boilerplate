package com.exporum.admin.domain.v1.system.code.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * NewsletterDetail.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 8. 14.
 * @description:
 */
@Getter
@Setter
public class CodeDetail {
    private String codeId;
    private String parentCodeId;
    private String codeValue;
    private String description;
    private Integer sortOrder;
    private Integer depth;
    private Boolean isEnable;
    private Boolean isDelete;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}

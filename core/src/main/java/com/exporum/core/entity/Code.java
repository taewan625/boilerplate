package com.exporum.core.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */
@Getter
@Setter
public class Code {
    private String code;
    private String parentCode;
    private String codeName;
    private int displayOrder;
    private int codeLevel;
    private boolean isUse;
    private boolean isDeleted;
    private String description;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
    private String deletedAt;
    private String deletedBy;
}

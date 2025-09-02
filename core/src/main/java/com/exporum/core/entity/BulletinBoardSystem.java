package com.exporum.core.entity;

import com.exporum.core.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */
@Getter
@Setter
public class BulletinBoardSystem extends BaseEntity {
    private String bbsCode;
    private long exhibitionId;
    private String title;
    private String content;
    private boolean isFixed;
    private boolean isDisabled;
    private boolean isDeleted;
}

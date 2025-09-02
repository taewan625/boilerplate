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
public class BbsAttachmentFile extends BaseEntity {

    private long bbsId;
    private long fileId;
    private boolean isDeleted;

}

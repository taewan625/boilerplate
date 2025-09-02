package com.exporum.core.entity.base;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class BaseEntity {

    private long id;
    private Timestamp createAt;
    private Timestamp createBy;
    private Timestamp updatedAt;
    private Timestamp updatedBy;
    private Timestamp deletedAt;
    private Timestamp deletedBy;

}

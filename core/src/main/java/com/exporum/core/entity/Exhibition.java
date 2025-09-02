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
public class Exhibition extends BaseEntity {
    private long countryId;
    private int year;
    private String exhibitionName;
    private String startDate;
    private String endDate;
    private boolean isUse;
    private boolean isDeleted;
    private String city;
}

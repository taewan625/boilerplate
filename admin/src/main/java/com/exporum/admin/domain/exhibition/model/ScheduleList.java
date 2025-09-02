package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Getter
@Setter
public class ScheduleList {

    private long id;
    private String eventDate;
    private String eventName;
    private String organization;
    private boolean used;
    private String createdAt;

}

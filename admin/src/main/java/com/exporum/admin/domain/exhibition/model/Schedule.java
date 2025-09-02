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
public class Schedule {

    private long id;
    private String exhibitionName;
    private String status;
    private String eventDate;
    private String organization;
    private String eventName;
    private String description;

}

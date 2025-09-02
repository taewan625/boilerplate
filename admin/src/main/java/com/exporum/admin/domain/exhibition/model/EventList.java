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
public class EventList {

    private long id;
    private String startTime;
    private String endTime;
    private String title;
    private String location;
    private int order;

}

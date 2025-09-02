package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 19.
 * @description :
 */

@Getter
@Setter
public class EventDTO {
    private long eventId;
    private String eventTitle;
    private String eventLocation;
    private String startTime;
    private String endTime;
    private int order;

    private long adminId;
}

package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 19.
 * @description :
 */

@Getter
@Setter
public class ScheduleDTO {

    private long id;
    private long exhibitionId;
    private String eventDate;
    private String organization;
    private String eventName;
    private String eventDescription;
    private String eventCode;

    private boolean used;

    private long adminId;

    private List<EventDTO> events;
}

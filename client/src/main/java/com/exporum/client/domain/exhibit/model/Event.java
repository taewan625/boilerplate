package com.exporum.client.domain.exhibit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */

@Getter
@Setter
public class Event {
    private long id;
    private int eventYear;
    private String eventCode;
    private String eventDate;
    private String eventName;
    private String eventDescription;
    private List<EventDetail> eventDetails;

}

package com.exporum.client.domain.exhibit.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */


@Getter
@Setter
public class EventDetail {
    private String eventTitle;
    private String eventLocation;
    private String eventDescription;
    private int displayOrder;
    private String startTime;
    private String endTime;
}

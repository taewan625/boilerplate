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
public class EventSchedule {

    private long id;
    private String country;
    private String city;
    private String venue;
    private String startDate;
    private String endDate;
    private List<Integer> exhibitionYears;
    private List<Event> events;

}

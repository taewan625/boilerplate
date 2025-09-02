package com.exporum.client.domain.exhibit.service;

import com.exporum.client.domain.exhibit.mapper.EventMapper;
import com.exporum.client.domain.exhibit.model.EventSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;

    public List<EventSchedule> getEvents(int eventYear) {
        return eventMapper.getEvents(eventYear);
    }

}

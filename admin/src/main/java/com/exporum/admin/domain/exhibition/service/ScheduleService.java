package com.exporum.admin.domain.exhibition.service;

import com.exporum.admin.domain.exhibition.mapper.ScheduleMapper;
import com.exporum.admin.domain.exhibition.model.*;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public Schedule getSchedule(long id){
        return Optional.ofNullable(scheduleMapper.getSchedule(id)).orElseThrow(DataNotFoundException::new);
    }





    @Transactional
    public void updatedUseSchedule(long id, ScheduleDTO schedule){
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        schedule.setAdminId(adminId);

        if(!(scheduleMapper.updatedUseSchedule(id, schedule)>0)){
            throw new OperationFailException();
        }
    }


    @Transactional
    public void deleteSchedule(long id){
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if(!(scheduleMapper.deleteSchedule(id, adminId) > 0)) {
            throw new OperationFailException();
        }
    }


    @Transactional
    public void updateSchedule(long id, ScheduleDTO schedule) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        schedule.setAdminId(adminId);

        if(!(scheduleMapper.updateSchedule(id, schedule)>0)){
            throw new OperationFailException();
        }
    }

    public void getScheduleList(PageableSchedule pageable){
        long recordsTotal = scheduleMapper.getScheduleCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(scheduleMapper.getScheduleList(pageable));
    }

    public void getEventList(PageableEvent pageable){
        long recordsTotal = scheduleMapper.getEventCount(pageable);

        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(scheduleMapper.getEventList(pageable));
    }


    @Transactional
    public void insertScheduleProcess(ScheduleDTO schedule) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        schedule.setAdminId(adminId);

        this.scheduleMapper.insertSchedule(schedule);

        for(EventDTO event : schedule.getEvents()){
            event.setEventId(schedule.getId());
            event.setAdminId(adminId);

            this.insetEvent(event);
        }
    }


    @Transactional
    public void insertEventProcess(EventDTO event){
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        event.setAdminId(adminId);
        this.insetEvent(event);
    }

    @Transactional
    public void deleteEventProcess(long eventId, long id){
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        this.deleteEvent(id, adminId);
    }

    @Transactional
    public void updateOrderProcess(List<EventList> events){
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        for(EventList event: events){
            updateEventOrder(event, adminId);
        }
    }

    private void deleteEvent(long id, long adminId) {
        if(!(scheduleMapper.deleteEvent(id,adminId)>0)){
            throw new OperationFailException();
        }
    }


    private void updateEventOrder(EventList event, long adminId) {
        if(!(scheduleMapper.updateEventOrder(event,adminId)>0)){
            throw new OperationFailException();
        }
    }

    private void insetEvent(EventDTO event) {
        if(!(scheduleMapper.insertEvent(event)>0)){
            throw new OperationFailException();
        }
    }

    private void insertSchedule(ScheduleDTO schedule) {
        if(!(scheduleMapper.insertSchedule(schedule)>0)){
            throw new OperationFailException();
        }
    }
}

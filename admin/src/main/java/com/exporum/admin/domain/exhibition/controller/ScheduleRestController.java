package com.exporum.admin.domain.exhibition.controller;

import com.exporum.admin.domain.exhibition.model.*;
import com.exporum.admin.domain.exhibition.service.ScheduleService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class ScheduleRestController {


    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public PageableSchedule getPageableSubscribe(PageableSchedule search) {
        scheduleService.getScheduleList(search);
        return search;
    }

    @PostMapping("/schedule/register")
    public ResponseEntity<OperationResponse> insertSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.insertScheduleProcess(scheduleDTO);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<OperationResponse> getSchedule(@PathVariable long id) {
        Schedule schedule = scheduleService.getSchedule(id);
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, schedule));
    }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<OperationResponse> updateSchedule(@PathVariable long id, @RequestBody ScheduleDTO schedule) {
        scheduleService.updateSchedule(id, schedule);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PutMapping("/schedule/{id}/publish")
    public ResponseEntity<OperationResponse> updateUseSchedule(@PathVariable long id, @RequestBody ScheduleDTO schedule) {
        scheduleService.updatedUseSchedule(id, schedule);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<OperationResponse> deleteSchedule(@PathVariable long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/schedule/event")
    public PageableEvent getPageableSubscribe(PageableEvent search) {
        scheduleService.getEventList(search);
        return search;
    }

    @PutMapping("/schedule/event/order")
    public ResponseEntity<OperationResponse> updateOrder(@RequestBody List<EventList> events) {
        scheduleService.updateOrderProcess(events);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @PostMapping("/schedule/event/register")
    public ResponseEntity<OperationResponse> registryEvent(@RequestBody EventDTO event) {
        scheduleService.insertEventProcess(event);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }

    @DeleteMapping("/schedule/event/{eventId}/{id}")
    public ResponseEntity<OperationResponse> deleteEvent(@PathVariable long eventId, @PathVariable long id) {
        scheduleService.deleteEventProcess(eventId, id);
        return ResponseEntity.ok(new OperationResponse(OperationStatus.SUCCESS));
    }
}

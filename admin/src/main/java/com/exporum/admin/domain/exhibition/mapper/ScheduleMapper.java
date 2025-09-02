package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Mapper
public interface ScheduleMapper {

    @SelectProvider(type = ScheduleSqlProvider.class, method = "getSchedule")
    Schedule getSchedule(@Param("id") long id);

    @InsertProvider(type = ScheduleSqlProvider.class, method = "insertSchedule")
    @Options(useGeneratedKeys = true, keyProperty = "schedule.id", keyColumn = "id")
    int insertSchedule(@Param("schedule") ScheduleDTO schedule);

    @UpdateProvider(type = ScheduleSqlProvider.class, method = "updateSchedule")
    int updateSchedule(@Param("id") long id, @Param("schedule") ScheduleDTO schedule);

    @UpdateProvider(type = ScheduleSqlProvider.class, method = "updatedUseSchedule")
    int updatedUseSchedule(@Param("id") long id, @Param("schedule") ScheduleDTO schedule);

    @UpdateProvider(type = ScheduleSqlProvider.class, method = "deleteSchedule")
    int deleteSchedule(@Param("id") long id, @Param("adminId") long adminId);

    @SelectProvider(type = ScheduleSqlProvider.class, method = "getScheduleList")
    List<ScheduleList> getScheduleList(@Param("schedule")PageableSchedule schedule);

    @SelectProvider(type = ScheduleSqlProvider.class, method = "getScheduleCount")
    long getScheduleCount(@Param("schedule")PageableSchedule schedule);

    @SelectProvider(type = ScheduleSqlProvider.class, method = "getEventList")
    List<EventList> getEventList(@Param("event") PageableEvent event);

    @SelectProvider(type = ScheduleSqlProvider.class, method = "getEventCount")
    long getEventCount(@Param("event")PageableEvent event);

    @InsertProvider(type = ScheduleSqlProvider.class, method = "insertEvent")
    int insertEvent(@Param("event") EventDTO event);

    @UpdateProvider(type = ScheduleSqlProvider.class, method = "updateEventOrder")
    int updateEventOrder(@Param("event") EventList event, @Param("adminId") long adminId);

    @UpdateProvider(type = ScheduleSqlProvider.class, method = "deleteEvent")
    int deleteEvent(@Param("id") long id, @Param("adminId") long adminId);

}

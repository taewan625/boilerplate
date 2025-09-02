package com.exporum.client.domain.exhibit.mapper;

import com.exporum.client.domain.exhibit.model.Event;
import com.exporum.client.domain.exhibit.model.EventDetail;
import com.exporum.client.domain.exhibit.model.EventSchedule;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */

@Mapper
public interface EventMapper {

    @SelectProvider(type = EventSqlProvider.class, method = "getEvents")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "exhibitionYears", many = @Many(select = "com.exporum.client.domain.exhibition.mapper.ExhibitionMapper.getExhibitionYearList")),
            @Result(column = "id", property = "events", many = @Many(select = "getEventList"))
    })
    List<EventSchedule> getEvents(@Param("eventYear") int eventYear);


    @SelectProvider(type = EventSqlProvider.class, method = "getEventList")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "eventDetails", many = @Many(select = "getEventDetailList"))
    })
    List<Event> getEventList(@Param("exhibitionId") long id);


    @SelectProvider(type = EventSqlProvider.class, method = "getEventDetailList")
    List<EventDetail> getEventDetailList(@Param("eventId") long id);


}

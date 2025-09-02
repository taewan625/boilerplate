package com.exporum.admin.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 5.
 * @description :
 */
public class DateTimeHelper {

    public static String getConvertLocalTime(String timezoneId, String convertTimezoneId, String dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);


        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of(timezoneId));
        ZonedDateTime utcPlus9Time = localZonedDateTime.withZoneSameInstant(ZoneId.of(convertTimezoneId));

        return utcPlus9Time.format(formatter);
    }

}

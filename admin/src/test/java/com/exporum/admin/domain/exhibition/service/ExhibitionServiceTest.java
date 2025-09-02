package com.exporum.admin.domain.exhibition.service;

import com.exporum.core.entity.Exhibition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@SpringBootTest
class ExhibitionServiceTest {


    @Autowired
    private ExhibitionService exhibitionService;

    @Test
    void test(){

        List<Exhibition> exhibitions = exhibitionService.findAll();


        assertEquals(2, exhibitions.size());

    }



    @Test
    void timezoneTest(){
        String time = "2025-04-28 01:59";
        //String time = "2025-04-27 23:59";
        String code = "ID";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);


        String timezone = "Asia/Jakarta";

        ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        ZonedDateTime utcPlus9Time = localZonedDateTime.withZoneSameInstant(ZoneId.of(timezone));

        System.out.println(utcPlus9Time.format(formatter));

    }

    @Test
    void timezoneTest2(){        // 1. 입력 시간 (국가별 현지 시간)
        String inputTime = "2025-04-27 23:59";
        String inputCountryCode = "US"; // 예: 미국 동부시간 기준

        // 2. 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(inputTime, formatter);

        // 3. 국가 코드(Alpha-3 → Alpha-2 변환)
        String countryCode = inputCountryCode.length() == 3 ? getAlpha2FromAlpha3(inputCountryCode) : inputCountryCode;

        if (countryCode != null) {
            String timeZoneId = getTimeZoneIdByCountryCode(countryCode);
            if (timeZoneId != null) {
                // 4. 입력된 국가 시간대에서 ZonedDateTime 생성
                ZonedDateTime localZonedDateTime = localDateTime.atZone(ZoneId.of(timeZoneId));

                // 5. UTC+9 (Asia/Seoul)로 변환
                ZonedDateTime utcPlus9Time = localZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

                // 6. 결과 출력
                System.out.println("입력 국가 시간: " + localZonedDateTime.format(formatter) + " " + timeZoneId);
                System.out.println("변환된 UTC+9 시간: " + utcPlus9Time.format(formatter) + " Asia/Seoul");
            } else {
                System.out.println("해당 국가 코드에 대한 시간대를 찾을 수 없습니다.");
            }
        } else {
            System.out.println("올바른 국가 코드가 아닙니다.");
        }
    }

    // Alpha-3 → Alpha-2 변환
    public static String getAlpha2FromAlpha3(String alpha3Code) {
        for (String isoCountry : Locale.getISOCountries()) {
            Locale locale = new Locale("", isoCountry);
            if (locale.getISO3Country().equalsIgnoreCase(alpha3Code)) {
                return isoCountry;
            }
        }
        return null;
    }

    // Alpha-2 코드로 주요 시간대 가져오기
    public static String getTimeZoneIdByCountryCode(String countryCode) {
        Map<String, String> countryToTimeZone = new HashMap<>();
        countryToTimeZone.put("KR", "Asia/Seoul");  // 한국
        countryToTimeZone.put("JP", "Asia/Tokyo");  // 일본
        countryToTimeZone.put("CN", "Asia/Shanghai");  // 중국
        countryToTimeZone.put("US", "America/New_York");  // 미국 동부시간
        countryToTimeZone.put("GB", "Europe/London");  // 영국
        countryToTimeZone.put("FR", "Europe/Paris");  // 프랑스
        countryToTimeZone.put("DE", "Europe/Berlin");  // 독일
        countryToTimeZone.put("IN", "Asia/Kolkata");  // 인도
        countryToTimeZone.put("AU", "Australia/Sydney");  // 호주
        countryToTimeZone.put("BR", "America/Sao_Paulo");  // 브라질
        countryToTimeZone.put("RU", "Europe/Moscow");  // 러시아

        return countryToTimeZone.getOrDefault(countryCode.toUpperCase(), null);
    }
}
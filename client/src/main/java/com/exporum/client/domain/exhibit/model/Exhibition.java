package com.exporum.client.domain.exhibit.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */

@Getter
@Setter
public class Exhibition {


    private long id;
    private String country;
    private String countryEn;
    private int year;
    private String exhibition;
    private String startDate;
    private String endDate;
    private String city;
    private String venue;

}

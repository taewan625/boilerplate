package com.exporum.core.domain.country.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 10.
 * @description :
 */

@Getter
@Setter
public class Countries {

    private long id;
    private String alpha2Code;
    private String countryName;
    private String countryNameEn;

}

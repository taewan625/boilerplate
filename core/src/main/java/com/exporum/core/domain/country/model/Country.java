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
public class Country {

    private long id;
    private String alpha2Code;
    private String alpha3Code;
    private String countryName;
    private String countyNameEn;
    private String numeric;
    private String callingCode;
    private boolean isMutualTaxExemption;
    private String mutualTaxExemptionDate;

}

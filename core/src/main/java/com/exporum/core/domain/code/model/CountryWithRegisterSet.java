package com.exporum.core.domain.code.model;

import com.exporum.core.domain.country.model.CallingCode;
import com.exporum.core.domain.country.model.Countries;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 13.
 * @description :
 */

@Getter
@Setter
public class CountryWithRegisterSet {

    List<CodeList> codeList;
    List<Countries> countryList;
    List<CallingCode> callingCodeList;


    @Builder
    public CountryWithRegisterSet(List<CodeList> codeList, List<Countries> countryList, List<CallingCode> callingCodeList) {
        this.codeList = codeList;
        this.countryList = countryList;
        this.callingCodeList = callingCodeList;
    }
}

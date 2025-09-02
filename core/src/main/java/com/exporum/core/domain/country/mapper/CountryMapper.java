package com.exporum.core.domain.country.mapper;

import com.exporum.core.domain.country.model.CallingCode;
import com.exporum.core.domain.country.model.Countries;
import com.exporum.core.domain.country.model.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 10.
 * @description :
 */

@Mapper
public interface CountryMapper {


    @SelectProvider(type = CountrySqlProvider.class, method = "getCountry")
    Country getCountry(@Param("id") long id);

    @SelectProvider(type = CountrySqlProvider.class, method = "getCountryList")
    List<Countries> getCountryList();

    @SelectProvider(type = CountrySqlProvider.class, method = "getCallingCodeList")
    List<CallingCode> getCallingCodeList();

}

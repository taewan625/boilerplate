package com.exporum.core.domain.country.service;

import com.exporum.core.domain.country.mapper.CountryMapper;
import com.exporum.core.domain.country.model.CallingCode;
import com.exporum.core.domain.country.model.Countries;
import com.exporum.core.domain.country.model.Country;
import com.exporum.core.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 10.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryMapper countryMapper;


    public Country getCountry(long id) throws DataNotFoundException {
        return Optional.ofNullable(countryMapper.getCountry(id)).orElseThrow(DataNotFoundException::new);
    }

    public List<Countries> getCountryList() {
        return countryMapper.getCountryList();
    }

    public List<CallingCode> getCallingCodeList() {
        return countryMapper.getCallingCodeList();
    }

}

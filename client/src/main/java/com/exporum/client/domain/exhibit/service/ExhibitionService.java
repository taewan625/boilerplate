package com.exporum.client.domain.exhibit.service;

import com.exporum.client.domain.exhibit.mapper.ExhibitionMapper;
import com.exporum.client.domain.exhibit.model.Exhibition;
import com.exporum.core.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionMapper exhibitionMapper;


    public Exhibition getCurrentExhibition() throws DataNotFoundException {
        return exhibitionMapper.getCurrentExhibition();
    }

    public List<Integer> getExhibitionYears(){
        return exhibitionMapper.getExhibitionYearList();
    }


}

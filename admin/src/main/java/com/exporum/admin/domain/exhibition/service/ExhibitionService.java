package com.exporum.admin.domain.exhibition.service;

import com.exporum.admin.domain.exhibition.mapper.ExhibitionMapper;
import com.exporum.core.entity.Exhibition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionMapper exhibitionMapper;


    public List<Exhibition> findAll() {
        return exhibitionMapper.findAll();
    }
}

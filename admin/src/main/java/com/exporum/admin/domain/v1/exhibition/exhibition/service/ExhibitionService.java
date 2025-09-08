package com.exporum.admin.domain.v1.exhibition.exhibition.service;

import com.exporum.admin.domain.v1.exhibition.exhibition.mapper.ExhibitionMapper;
import com.exporum.admin.domain.v1.exhibition.exhibition.model.ExhibitionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ExhibitionService.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 7. 최초 작성
 */
@Service
@RequiredArgsConstructor
public class ExhibitionService {
    private final ExhibitionMapper exhibitionMapper;

    public void createExhibition(ExhibitionCreateRequest exhibitionCreateRequest) {
        exhibitionMapper.createExhibition(exhibitionCreateRequest);
    }
}

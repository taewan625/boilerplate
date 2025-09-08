package com.exporum.admin.domain.v1.exhibition.exhibition.mapper;

import com.exporum.admin.domain.v1.exhibition.exhibition.model.ExhibitionCreateRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * ExhibitionMapper.java
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
@Mapper
public interface ExhibitionMapper {
    void createExhibition(ExhibitionCreateRequest exhibitionCreateRequest);
}

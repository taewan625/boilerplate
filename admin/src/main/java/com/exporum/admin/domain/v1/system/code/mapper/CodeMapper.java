package com.exporum.admin.domain.v1.system.code.mapper;

import com.exporum.admin.core.pagination.SearchRequest;
import com.exporum.admin.domain.v1.system.code.model.CodeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CodeMapper.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 8. 최초 작성
 */
@Mapper
public interface CodeMapper {
    //parent code 목록 count
    long selectParentCodeListCount(SearchRequest<String> searchRequest);

    //parent code 목록 조회 (페이징)
    List<CodeDetail> selectParentCodeList(SearchRequest<String> searchRequest);
}

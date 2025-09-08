package com.exporum.admin.domain.v1.system.code.service;

import com.exporum.admin.core.pagination.Pageable;
import com.exporum.admin.core.pagination.SearchRequest;
import com.exporum.admin.core.pagination.SearchResponse;
import com.exporum.admin.domain.v1.system.code.mapper.CodeMapper;
import com.exporum.admin.domain.v1.system.code.model.CodeDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * CodeService.java
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
@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeMapper codeMapper;

    //code 목록 조회(페이징)
    public SearchResponse<CodeDetail> selectParentCodeList(SearchRequest<String> searchRequest) {
        long totalCount = codeMapper.selectParentCodeListCount(searchRequest);
        Pageable pageable = searchRequest.getPageable();
        pageable.setPages(totalCount);

        List<CodeDetail> contents = (0 < totalCount) ?  codeMapper.selectParentCodeList(searchRequest) : Collections.emptyList();

        return SearchResponse.<CodeDetail>builder().pageable(pageable).contents(contents).build();
    }
}

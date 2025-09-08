package com.exporum.admin.domain.v1.system.code.model;


import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;

/**
 * CodeSearchRequest.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 8. 최초 작성
 */
public class CodeSearchRequest extends Pageable {
    private String searchText;

    public CodeSearchRequest(int page, int pageSize) {
        super(page, pageSize);
    }

    public CodeSearchRequest(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public CodeSearchRequest(String searchText, int page, int pageSize) {
        super(page, pageSize);
        this.searchText = searchText;
    }
}
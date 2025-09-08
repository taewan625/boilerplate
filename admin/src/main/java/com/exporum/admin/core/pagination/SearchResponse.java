package com.exporum.admin.core.pagination;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * SearchResponse.java
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
@Getter
public class SearchResponse<T> {
    private Pageable pageable;
    private List<T> contents;

    @Builder
    public SearchResponse(Pageable pageable, List<T> contents) {
        this.pageable = pageable;
        this.contents = contents;
    }
}
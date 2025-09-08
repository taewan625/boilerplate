package com.exporum.admin.core.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SearchRequest.java
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
@Setter
@NoArgsConstructor
public class SearchRequest<T> {
    private Pageable pageable;
    private T searchCondition; //검색 조건 (각 메뉴별 model만든것을 넣으면 됨)

    @Builder
    public SearchRequest(Pageable pageable, T searchCondition) {
        this.pageable = pageable;
        this.searchCondition = searchCondition;
    }
}
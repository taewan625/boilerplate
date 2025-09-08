package com.exporum.admin.core.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pageable.java
 *
 * <p>공통 페이징 정보 클래스</p>
 *
 * @author
 * @since 2025. 9. 8.
 */
@Getter
@NoArgsConstructor
public class Pageable {
    private int page = 1;
    private int pageSize = 10;
    private int offset = 0;

    private int totalPages = 0;
    private long totalElements = 0L;

    public Pageable(int page, int pageSize) {
        if (page > 0) this.page = page;
        if (pageSize > 0) this.pageSize = pageSize;
        this.offset = this.pageSize * (this.page - 1);
    }

    public void setPages(long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / this.pageSize);
    }
}

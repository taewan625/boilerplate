package com.exporum.client.domain.notice.model;


import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * PageableNewsletter.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 8. 14.
 * @description:
 */
@Getter
@Setter
public class SearchNewsletter extends Pageable {
    private String searchText;

    public SearchNewsletter(int page, int pageSize) {
        super(page, pageSize);
    }

    public SearchNewsletter(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public SearchNewsletter(String searchText, int page, int pageSize) {
        super(page, pageSize);
        this.searchText = searchText;
    }
}

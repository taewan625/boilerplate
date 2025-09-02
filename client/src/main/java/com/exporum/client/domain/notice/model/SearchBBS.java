package com.exporum.client.domain.notice.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 2.
 * @description :
 */

@Getter
@Setter
public class SearchBBS extends Pageable {

    private String bbsType;
    private String searchText;

    public SearchBBS(int page, int pageSize) {
        super(page, pageSize);
    }

    public SearchBBS(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }


    @Builder
    public SearchBBS(String bbsType, String searchText, int page, int pageSize) {
        super(page, pageSize);
        this.bbsType = bbsType;
        this.searchText = searchText;
    }
}

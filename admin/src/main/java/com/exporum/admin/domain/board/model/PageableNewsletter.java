package com.exporum.admin.domain.board.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */

@Getter
@Setter
public class PageableNewsletter extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private String status;
    private String searchText;

    List<Newsletters> data;

    public PageableNewsletter(){}

    public PageableNewsletter(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableNewsletter(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableNewsletter(List<Newsletters> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

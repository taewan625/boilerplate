package com.exporum.admin.domain.board.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@Getter
@Setter
public class PageableContactus extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;


    private String status;

    List<ContactusList> data;

    public PageableContactus(){}

    public PageableContactus(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableContactus(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableContactus(List<ContactusList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

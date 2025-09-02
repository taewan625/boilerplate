package com.exporum.admin.domain.subscribe.model;

import com.exporum.admin.domain.board.model.BoardList;
import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Getter
@Setter
public class PageableSubscribe extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;


    private String startDate;
    private String endDate;
    private String subscribe;
    private String status;
    private String searchType;
    private String searchText;

    List<SubscribeList> data;

    public PageableSubscribe(){}

    public PageableSubscribe(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableSubscribe(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableSubscribe(List<SubscribeList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

package com.exporum.admin.domain.booth.model;

import com.exporum.admin.domain.subscribe.model.SubscribeList;
import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */

@Getter
@Setter
public class PageableBoothRequest extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private String searchType;
    private String searchText;

    List<BoothRequest> data;

    public PageableBoothRequest() {
    }

    public PageableBoothRequest(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableBoothRequest(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableBoothRequest(List<BoothRequest> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}
package com.exporum.admin.domain.brand.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 12.
 * @description :
 */

@Getter
@Setter
public class PageableBrand extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private long exhibitionId;
    private String approve;
    private String cancelled;
    private String type;
    private String searchText;

    List<BrandList> data;

    public PageableBrand(){}

    public PageableBrand(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableBrand(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableBrand(List<BrandList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

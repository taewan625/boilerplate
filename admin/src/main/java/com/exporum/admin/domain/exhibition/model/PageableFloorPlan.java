package com.exporum.admin.domain.exhibition.model;

import com.exporum.admin.domain.subscribe.model.SubscribeList;
import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Getter
@Setter
public class PageableFloorPlan extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private long exhibitionId;

    List<FloorPlanList> data;

    public PageableFloorPlan(){}

    public PageableFloorPlan(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableFloorPlan(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableFloorPlan(List<FloorPlanList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

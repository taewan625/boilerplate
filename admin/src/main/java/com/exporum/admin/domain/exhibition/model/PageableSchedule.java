package com.exporum.admin.domain.exhibition.model;

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
public class PageableSchedule extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private long exhibitionId;

    List<ScheduleList> data;

    public PageableSchedule(){}

    public PageableSchedule(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableSchedule(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableSchedule(List<ScheduleList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

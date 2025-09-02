package com.exporum.admin.domain.exhibitor.model;

import com.exporum.admin.domain.brand.model.BrandList;
import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Getter
@Setter
public class PageableExhibitor  extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private long exhibitionId;
    private String applicationType;
    private String industry;
    private String sponsor;
    private String type;
    private String searchText;

    List<ExhibitorList> data;

    public PageableExhibitor(){}

    public PageableExhibitor(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableExhibitor(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableExhibitor(List<ExhibitorList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

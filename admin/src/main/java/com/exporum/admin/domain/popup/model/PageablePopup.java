package com.exporum.admin.domain.popup.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */

@Getter
@Setter
public class PageablePopup extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;



    private long exhibitionId;
    private boolean published;

    List<PopupList> data;

    public PageablePopup(){}

    public PageablePopup(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageablePopup(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageablePopup(List<PopupList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }

}

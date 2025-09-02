package com.exporum.admin.domain.exhibitor.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 19.
 * @description :
 */

@Getter
@Setter
public class PageableInvitation extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;

    private long exhibitorId;
    private String badeType;
    private String state;
    private String type;
    private String searchText;

    List<Invitation> data;

    public PageableInvitation(){}

    public PageableInvitation(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableInvitation(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableInvitation(List<Invitation> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

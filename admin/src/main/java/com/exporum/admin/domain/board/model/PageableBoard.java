package com.exporum.admin.domain.board.model;

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
public class PageableBoard extends Pageable {
    private int start;
    private int length;
    private long recordsTotal;
    private long recordsFiltered;



    private String boardType;
    private String status;
    private String type;
    private String searchText;

    List<BoardList> data;

    public PageableBoard(){}

    public PageableBoard(int page, int pageSize) {
        super(page, pageSize);
    }

    public PageableBoard(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }

    @Builder
    public PageableBoard(List<BoardList> data, int page, int pageSize) {
        super(page, pageSize);
        this.data = data;
    }
}

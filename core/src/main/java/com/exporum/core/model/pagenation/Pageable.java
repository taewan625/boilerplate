package com.exporum.core.model.pagenation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {


    private int page;
    private int offset;
    private int pageSize;
    private int totalPages;
    private long totalElements;


    public Pageable(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;

        if(page < 0) {
            this.page = 1;
        }
        if(pageSize < 0) {
            this.pageSize = 10;
        }

        this.offset = pageSize * (page - 1);

    }

    public void setPages(long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / this.pageSize);
    }

}

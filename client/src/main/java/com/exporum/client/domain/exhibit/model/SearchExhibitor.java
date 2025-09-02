package com.exporum.client.domain.exhibit.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */


@Getter
@Setter
public class SearchExhibitor extends Pageable {

    private int year;
    private String searchText;

    public SearchExhibitor(int page, int pageSize) {
        super(page, pageSize);
    }

    public SearchExhibitor(int page, int offset, int pageSize, int totalPages, long totalElements) {
        super(page, offset, pageSize, totalPages, totalElements);
    }


    @Builder
    public SearchExhibitor(int year, String searchText, int page, int pageSize) {
        super(page, pageSize);
        this.year = year;
        this.searchText = searchText;
    }
}

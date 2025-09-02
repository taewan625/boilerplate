package com.exporum.client.domain.exhibit.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */

@Getter
@Setter
public class Exhibitor {
    private List<Integer> exhibitionYears;
    private Pageable pageable;
    private List<ExhibitorDetail> exhibitors;

    @Builder
    public Exhibitor(List<Integer> exhibitionYears, Pageable pageable, List<ExhibitorDetail> exhibitors) {
        this.exhibitionYears = exhibitionYears;
        this.pageable = pageable;
        this.exhibitors = exhibitors;
    }
}

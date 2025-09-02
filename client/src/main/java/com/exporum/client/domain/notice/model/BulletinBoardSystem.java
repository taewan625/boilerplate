package com.exporum.client.domain.notice.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 3.
 * @description :
 */
@Getter
@Setter
public class BulletinBoardSystem {

    private Pageable pageable;
    private List<BBSList> bbsList;

    @Builder
    public BulletinBoardSystem(Pageable pageable, List<BBSList> bbsList) {
        this.pageable = pageable;
        this.bbsList = bbsList;
    }

}

package com.exporum.admin.domain.v1.system.code.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Newsletters.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 8. 14.
 * @description:
 */
@Getter
@Setter
public class ParentCodes {
    private Pageable pageable;
    private List<CodeDetail> parentCodes;

    @Builder
    public ParentCodes(Pageable pageable, List<CodeDetail> parentCodes) {
        this.pageable = pageable;
        this.parentCodes = parentCodes;
    }
}

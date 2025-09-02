package com.exporum.admin.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 19.
 * @description :
 */

@Getter
@Setter
public class ExhibitionSelectOption {
    private long id;
    private String exhibitionName;
    private boolean used;
}

package com.exporum.core.domain.code.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class CodeList {
    private int displayOrder;
    private int codeLevel;
    private String code;
    private String codeName;
    private List<CodeList> children;
}

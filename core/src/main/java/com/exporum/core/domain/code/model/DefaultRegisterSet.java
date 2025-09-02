package com.exporum.core.domain.code.model;

import com.exporum.core.domain.country.model.CallingCode;
import com.exporum.core.domain.country.model.Countries;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 21.
 * @description :
 */

@Getter
@Setter
public class DefaultRegisterSet {
    List<CodeList> codeList;

    @Builder
    public DefaultRegisterSet(List<CodeList> codeList) {
        this.codeList = codeList;
    }
}

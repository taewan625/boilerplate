package com.exporum.admin.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */

@Getter
@Setter
public class CountryOption {

    private long id;
    private String country;
    private boolean mutualTaxExemption;

}

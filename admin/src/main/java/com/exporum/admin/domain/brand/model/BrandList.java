package com.exporum.admin.domain.brand.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 12.
 * @description :
 */

@Getter
@Setter
public class BrandList {
    private long no;
    private long id;
    private long exhibitionId;
    private String country;
    private String brand;
    private String company;
    private String booth;
    private String industry;
    private int approve;
    private boolean cancelled;
    private String createdAt;
    private String updatedAt;
}

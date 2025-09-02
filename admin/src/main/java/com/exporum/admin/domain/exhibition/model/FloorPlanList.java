package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Getter
@Setter
public class FloorPlanList {
    private long no;
    private long id;
    private String filePath;
    private String uuid;
    private String filename;
    private boolean deleted;
    private String createdAt;

}

package com.exporum.client.domain.exhibit.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 24.
 * @description :
 */

@Getter
@Setter
public class FloorPlan {
    private long id;
    private long fileId;
    private String originFileName;
    private long fileSize;
    private String mimeType;
    private String path;
    private String uuid;
}

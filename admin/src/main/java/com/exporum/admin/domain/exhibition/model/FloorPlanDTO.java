package com.exporum.admin.domain.exhibition.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 18.
 * @description :
 */

@Getter
@Setter
public class FloorPlanDTO {

    private long exhibitionId;
    MultipartFile file;

    private long fileId;
    private long adminId;


}

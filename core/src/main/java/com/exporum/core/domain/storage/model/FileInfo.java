package com.exporum.core.domain.storage.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Getter
@Setter
public class FileInfo {

    private long id;
    private String mimeType;
    private String uuid;
    private String fileName;
    private String originFileName;
    private String fileSize;
    private String ext;
    private String filePath;
    private boolean use;
    private boolean deleted;

}

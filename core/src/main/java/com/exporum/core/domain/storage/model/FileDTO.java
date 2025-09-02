package com.exporum.core.domain.storage.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */

@Getter
@Setter
public class FileDTO {

    private long id;
    private String mimeType;
    private String uuid;
    private String fileName;
    private String originFileName;
    private long fileSize;
    private String ext;
    private String filePath;


    @Builder
    public FileDTO(long id, String mimeType, String uuid, String fileName, String originFileName, long fileSize, String ext, String filePath) {
        this.id = id;
        this.mimeType = mimeType;
        this.uuid = uuid;
        this.fileName = fileName;
        this.originFileName = originFileName;
        this.fileSize = fileSize;
        this.ext = ext;
        this.filePath = filePath;
    }

}

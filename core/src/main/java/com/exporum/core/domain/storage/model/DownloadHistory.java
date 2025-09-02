package com.exporum.core.domain.storage.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */

@Getter
@Setter
public class DownloadHistory {

    private long fileId;
    private String ip;
    private String userAgent;
    private String downloadUrl;
    private String fileTypeCode;
    private String fileName;
    private String referer;
    private String parameter;
    private long downloadBy;


}

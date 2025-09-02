package com.exporum.client.domain.notice.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Getter
@Setter
public class PostAttachFile {
    private String uuid;
    private String path;
    private String fileOriginalName;
}

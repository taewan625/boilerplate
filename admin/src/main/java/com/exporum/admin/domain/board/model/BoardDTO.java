package com.exporum.admin.domain.board.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */

@Getter
@Setter
public class BoardDTO {

    private long id;
    private String title;
    private String content;
    private String bbsCode;
    private long exhibitionId;

    private boolean disabled;

    private MultipartFile file;
    private long fileId;
    private long adminId;
}

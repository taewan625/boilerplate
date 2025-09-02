package com.exporum.admin.domain.board.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */


@Getter
@Setter
public class NewsletterDTO extends Newsletter{
    private MultipartFile logoFile;
    private MultipartFile htmlFile;
}

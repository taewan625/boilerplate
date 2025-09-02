package com.exporum.client.domain.notice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Getter
@Setter
public class Post {

    private long id;
    private String bbsCode;
    private String title;
    private String content;

    private List<PostAttachFile> attachFiles;

    private PreviousAndNextPost prePost;
    private PreviousAndNextPost nextPost;
}

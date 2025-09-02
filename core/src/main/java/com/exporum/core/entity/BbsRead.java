package com.exporum.core.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class BbsRead {
    private long id;
    private long bbsId;
    private String referer;
    private String userAgent;
    private String ip;
    private String readAt;
    private String parameter;
}

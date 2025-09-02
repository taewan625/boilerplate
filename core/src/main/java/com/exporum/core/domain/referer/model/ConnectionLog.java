package com.exporum.core.domain.referer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */


@Getter
@Setter
public class ConnectionLog {
    private long exhibitionId;
    private String referer;
    private String url;
    private String parameter;
    private String language;
    private String userAgent;
    private String ip;


    @Builder
    public ConnectionLog(long exhibitionId, String referer, String url, String parameter, String language, String userAgent, String ip) {
        this.exhibitionId = exhibitionId;
        this.referer = referer;
        this.url = url;
        this.parameter = parameter;
        this.language = language;
        this.userAgent = userAgent;
        this.ip = ip;
    }
}

package com.exporum.client.domain.notice.model;

import com.exporum.core.model.pagenation.Pageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Newsletters.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 8. 14.
 * @description:
 */
@Getter
@Setter
public class Newsletters {
    private Pageable pageable;
    private List<NewsletterDetail> newsletters;

    @Builder
    public Newsletters(Pageable pageable, List<NewsletterDetail> newsletters) {
        this.pageable = pageable;
        this.newsletters = newsletters;
    }
}

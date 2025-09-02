package com.exporum.client.domain.notice.service;

import com.exporum.client.domain.notice.mapper.NewsletterMapper;
import com.exporum.client.domain.notice.model.NewsletterDetail;
import com.exporum.client.domain.notice.model.Newsletters;
import com.exporum.client.domain.notice.model.SearchNewsletter;
import com.exporum.client.domain.notice.model.SubscribeDTO;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 3.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class NewsletterService {
    @Value("${resource.storage.url}")
    private String storageUrl;

    private final NewsletterMapper newsletterMapper;

    @Transactional
    public void insertSubscribe(SubscribeDTO subscribeDTO) throws OperationFailException {

        if(!(newsletterMapper.insertSubscribe(subscribeDTO) > 0)) {
            throw new OperationFailException();
        }

    }

    //newsletters 목록 조회
    public Newsletters getNewsletters(SearchNewsletter searchNewsletter) {
        searchNewsletter.setPages(newsletterMapper.getNewsletterCount(searchNewsletter));

        return Newsletters.builder()
                .pageable(searchNewsletter)
                .newsletters(newsletterMapper.getNewsletterList(searchNewsletter, storageUrl))
                .build();
    }

    //newsletter 상세 조회
    public NewsletterDetail getNewsletter(long id){
        return Optional.ofNullable(newsletterMapper.getNewsletter(id, storageUrl)).orElseThrow(DataNotFoundException::new);
    }


}

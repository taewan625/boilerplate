package com.exporum.client.domain.notice.service;

import com.exporum.client.domain.notice.mapper.BBSMapper;
import com.exporum.client.domain.notice.model.BulletinBoardSystem;
import com.exporum.client.domain.notice.model.Post;
import com.exporum.client.domain.notice.model.SearchBBS;
import com.exporum.core.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 2.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class BBSService {

    private final BBSMapper bbsMapper;

    @Value("${resource.storage.url}")
    private String storageUrl;

    public BulletinBoardSystem getBBS(SearchBBS searchBBS){

        searchBBS.setPages(bbsMapper.getBBSCount(searchBBS));

        return BulletinBoardSystem.builder()
                .pageable(searchBBS)
                .bbsList(bbsMapper.getBBSList(searchBBS))
                .build();

    }

    public Post getPost(long id){
        Post post = Optional.ofNullable(bbsMapper.getBBS(id)).orElseThrow(DataNotFoundException::new);
        post.getAttachFiles().forEach(attachFile -> {attachFile.setPath(storageUrl + attachFile.getPath());});
        return post;
    }

}

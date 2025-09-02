package com.exporum.admin.domain.board.service;

import com.exporum.admin.domain.board.mapper.NewsletterMapper;
import com.exporum.admin.domain.board.model.Newsletter;
import com.exporum.admin.domain.board.model.NewsletterDTO;
import com.exporum.admin.domain.board.model.Newsletters;
import com.exporum.admin.domain.board.model.PageableNewsletter;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.domain.storage.model.FileDTO;
import com.exporum.core.domain.storage.service.StorageService;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class NewsletterService {

    private final NewsletterMapper newsletterMapper;

    private final StorageService storageService;

    @Value("${ncp.object-storage.path.newsletter}")
    private String newsletterObjectStoragePath;

    @Value("${resource.storage.url}")
    private String resourceStorageUrl;

    public void getPageableNewsletter(PageableNewsletter pageable) {
        long recordsTotal = newsletterMapper.getNewsletterCount(pageable);
        pageable.setRecordsTotal(recordsTotal);
        pageable.setRecordsFiltered(recordsTotal);

        pageable.setData(newsletterMapper.getNewsletterList(pageable));
    }

    public Newsletters getNewsletter(long id){
        return Optional.ofNullable(newsletterMapper.getNewsletter(id, resourceStorageUrl)).orElseThrow(DataNotFoundException::new);
    }

    @Transactional
    public void insertNewsletterProcess(NewsletterDTO newsletter) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if (newsletter.getLogoFile() != null){
            FileDTO file = storageService.ncpUpload(newsletter.getLogoFile(), newsletterObjectStoragePath);
            newsletter.setLogoFileId(file.getId());
        }

        if(newsletter.getHtmlFile() != null){
            FileDTO file = storageService.ncpUpload(newsletter.getHtmlFile(), newsletterObjectStoragePath);
            newsletter.setHtmlFileId(file.getId());
        }

        this.insertNewsletter(newsletter, adminId);
    }


    @Transactional
    public void updateNewsletterProcess(long id, NewsletterDTO newsletter) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        if (newsletter.getLogoFile() != null){
            FileDTO file = storageService.ncpUpload(newsletter.getLogoFile(), newsletterObjectStoragePath);
            newsletter.setLogoFileId(file.getId());
        }

        if(newsletter.getHtmlFile() != null){
            FileDTO file = storageService.ncpUpload(newsletter.getHtmlFile(), newsletterObjectStoragePath);
            newsletter.setHtmlFileId(file.getId());
        }


        this.updateNewsletter(id, newsletter, adminId);
    }

    @Transactional
    public void updateNewsletterEnabled(long id, Newsletter newsletter) {
        long adminId = AuthenticationHelper.getAuthenticationUserId();

        this.updateNewsletterEnabled(id, newsletter, adminId);
    }



    public void deleteNewsletterProcess(long id){
        long adminId = AuthenticationHelper.getAuthenticationUserId();
        this.deleteNewsletter(id, adminId);
    }

    private void deleteNewsletter(long id, long adminId) {
        if(!(newsletterMapper.deleteNewsletter(id, adminId) > 0)) {
            throw new OperationFailException();
        }
    }

    private void updateNewsletter(long id, NewsletterDTO newsletter, long adminId) {
        if(!(newsletterMapper.updateNewsletter(id, newsletter, adminId) > 0)) {
            throw new OperationFailException();
        }
    }

    private void updateNewsletterEnabled(long id, Newsletter newsletter, long adminId) {
        if(!(newsletterMapper.updateNewsletterEnabled(id, newsletter, adminId) > 0)) {
            throw new OperationFailException();
        }
    }

    private void insertNewsletter(NewsletterDTO newsletter, long adminId) {
        if(!(newsletterMapper.insertNewsletter(newsletter, adminId) > 0)) {
            throw new OperationFailException();
        }
    }

}

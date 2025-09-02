package com.exporum.client.domain.notice.mapper;

import com.exporum.client.domain.notice.model.NewsletterDetail;
import com.exporum.client.domain.notice.model.SearchNewsletter;
import com.exporum.client.domain.notice.model.SubscribeDTO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 3.
 * @description :
 */

@Mapper
public interface NewsletterMapper {

    @InsertProvider(type = NewsletterSqlProvider.class, method = "insertSubscribe")
    int insertSubscribe(@Param("subscribe") SubscribeDTO subscribe);


    @SelectProvider(type = NewsletterSqlProvider.class, method = "getNewsletterList")
    List<NewsletterDetail> getNewsletterList(@Param("search") SearchNewsletter search, @Param("storageUrl") final String storageUrl);

    @SelectProvider(type = NewsletterSqlProvider.class, method = "getNewsletterCount")
    long getNewsletterCount(@Param("search") SearchNewsletter search);

    @SelectProvider(type = NewsletterSqlProvider.class, method = "getNewsletter")
    NewsletterDetail getNewsletter(@Param("id") long id, @Param("storageUrl") final String storageUrl);
}

package com.exporum.admin.domain.board.mapper;

import com.exporum.admin.domain.board.model.Newsletter;
import com.exporum.admin.domain.board.model.NewsletterDTO;
import com.exporum.admin.domain.board.model.Newsletters;
import com.exporum.admin.domain.board.model.PageableNewsletter;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 8. 13.
 * @description :
 */

@Mapper
public interface NewsletterMapper {

    @SelectProvider(type = NewsletterSqlProvider.class, method = "getNewsletterList")
    List<Newsletters> getNewsletterList(@Param("search") PageableNewsletter search);

    @SelectProvider(type = NewsletterSqlProvider.class, method = "getNewsletterCount")
    long getNewsletterCount(@Param("search") PageableNewsletter search);

    @SelectProvider(type = NewsletterSqlProvider.class, method = "getNewsletter")
    Newsletters getNewsletter(@Param("newsletterId") long newsletterId, @Param("resourceStorageUrl") String resourceStorageUrl);

    @InsertProvider(type = NewsletterSqlProvider.class, method = "insertNewsletter")
    int insertNewsletter(@Param("newsletter") NewsletterDTO newsletter, @Param("adminId") long adminId);

    @UpdateProvider(type = NewsletterSqlProvider.class, method = "updateNewsletter")
    int updateNewsletter(@Param("newsletterId")long newsletterId, @Param("newsletter") NewsletterDTO newsletter, @Param("adminId") long adminId);

    @UpdateProvider(type = NewsletterSqlProvider.class, method = "updateNewsletterEnabled")
    int updateNewsletterEnabled(@Param("newsletterId")long newsletterId, @Param("newsletter") Newsletter newsletter, @Param("adminId") long adminId);

    @UpdateProvider(type = NewsletterSqlProvider.class, method = "deleteNewsletter")
    int deleteNewsletter(@Param("newsletterId") long newsletterId, @Param("adminId") long adminId);


}

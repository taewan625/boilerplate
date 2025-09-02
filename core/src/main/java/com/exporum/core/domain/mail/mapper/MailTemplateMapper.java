package com.exporum.core.domain.mail.mapper;

import com.exporum.core.domain.mail.model.BadgeTemplateData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 16.
 * @description :
 */
@Mapper
public interface MailTemplateMapper {

    @SelectProvider(type= MailTemplateSqlProvider.class, method = "getBadgeTemplateData")
    BadgeTemplateData getBadgeTemplateData(@Param("merchantUid") String merchantUid);


}

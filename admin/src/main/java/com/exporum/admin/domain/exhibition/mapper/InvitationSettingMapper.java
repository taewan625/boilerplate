package com.exporum.admin.domain.exhibition.mapper;

import com.exporum.admin.domain.exhibition.model.InvitationSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Mapper
public interface InvitationSettingMapper {
    @SelectProvider(type = InvitationSettingSqlProvider.class, method = "getInvitationSetting")
    List<InvitationSetting> getInvitationSetting(@Param("exhibitionId") long exhibitionId);
}

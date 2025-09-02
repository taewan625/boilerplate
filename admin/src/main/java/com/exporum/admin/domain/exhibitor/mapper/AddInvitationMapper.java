package com.exporum.admin.domain.exhibitor.mapper;

import com.exporum.admin.domain.exhibitor.model.AddInvitation;
import com.exporum.admin.domain.exhibitor.model.AddInvitationDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Mapper
public interface AddInvitationMapper {

    @InsertProvider(type = AddInvitationSqlProvider.class, method = "addInvitation")
    int addInvitation(@Param("add") AddInvitationDTO add);

    @UpdateProvider(type = AddInvitationSqlProvider.class, method = "deleteAddInvitation")
    int deleteAddInvitation(@Param("id")long id, @Param("adminId") long adminId);

    @SelectProvider(type = AddInvitationSqlProvider.class, method = "getAddInvitation")
    List<AddInvitation> getAddInvitation(@Param("exhibitorId") long exhibitorId);

    @SelectProvider(type = AddInvitationSqlProvider.class, method = "getTotalAddInvitation")
    AddInvitation getTotalAddInvitation(@Param("exhibitorId") long exhibitorId);
}

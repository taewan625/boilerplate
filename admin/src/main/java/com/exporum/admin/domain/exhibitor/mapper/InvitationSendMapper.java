package com.exporum.admin.domain.exhibitor.mapper;

import com.exporum.admin.domain.exhibitor.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 19.
 * @description :
 */

@Mapper
public interface InvitationSendMapper {

    @SelectProvider(type = InvitationSendSqlProvider.class, method = "isBarcodeExists")
    boolean isBarcodeExists(@Param("barcode") String barcode);

    @InsertProvider(type = InvitationSendSqlProvider.class, method = "insertInvitation")
    int insertInvitation(@Param("id") long id, @Param("invitation") InvitationDTO invitation);

    @SelectProvider(type = InvitationSendSqlProvider.class, method = "getSendInvitationCount")
    List<SendInvitation> getSendInvitationCount(@Param("exhibitorId") long exhibitorId);

    @SelectProvider(type = InvitationSendSqlProvider.class, method = "getInvitationList")
    List<Invitation> getInvitationList(@Param("search") PageableInvitation search);

    @SelectProvider(type = InvitationSendSqlProvider.class, method = "getInvitationCount")
    long getInvitationCount(@Param("search") PageableInvitation search);

    @UpdateProvider(type = InvitationSendSqlProvider.class, method = "updateInvitation")
    int updateInvitation(@Param("id") long id, @Param("invitation") InvitationDTO invitation);

    @SelectProvider(type = InvitationSendSqlProvider.class, method = "invitationExcel")
    List<InvitationExcel> invitationExcel(@Param("exhibitionId") long exhibitionId);

    @SelectProvider(type = InvitationSendSqlProvider.class, method = "getRemindInvitation")
    List<InvitationDTO> getRemindInvitation();

}

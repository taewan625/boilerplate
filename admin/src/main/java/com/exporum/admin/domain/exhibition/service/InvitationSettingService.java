package com.exporum.admin.domain.exhibition.service;

import com.exporum.admin.domain.exhibition.mapper.InvitationSettingMapper;
import com.exporum.admin.domain.exhibition.model.InvitationSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 17.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class InvitationSettingService {
    private final InvitationSettingMapper invitationSettingMapper;

    public List<InvitationSetting> getInvitationSetting(long exhibitionId) {
        return invitationSettingMapper.getInvitationSetting(exhibitionId);
    }
}

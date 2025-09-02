package com.exporum.client.domain.home.service;

import com.exporum.client.domain.exhibit.mapper.ExhibitionMapper;
import com.exporum.client.domain.home.mapper.PopupMapper;
import com.exporum.client.domain.home.model.Popup;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupMapper popupMapper;

    private final ExhibitionMapper exhibitionMapper;
    @Value("${resource.storage.url}")
    private String storageUrl;

    public List<Popup> getPopup() {
        long exhibitionId = exhibitionMapper.getCurrentExhibition().getId();

        return popupMapper.getPopup(exhibitionId, storageUrl);
    }
}

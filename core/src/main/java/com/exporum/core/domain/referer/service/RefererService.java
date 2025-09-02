package com.exporum.core.domain.referer.service;

import com.exporum.core.domain.referer.mapper.RefererMapper;
import com.exporum.core.domain.referer.model.ConnectionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */

@Service
@RequiredArgsConstructor
public class RefererService {

    private final RefererMapper refererMapper;


    public void insertReferer(ConnectionLog connectionLog) {
        refererMapper.insertConnectionLog(connectionLog);
    }
}

package com.exporum.core.entity;

import com.exporum.core.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description : 페이지별 사용코드 설정 테이블 엔티티
 */

@Getter
@Setter
public class CodeSet extends BaseEntity {

    private String setCode;
    private String SetName;
    private String codes;
    private boolean isUse;
    private String description;

}

package com.exporum.admin.domain.v1.exhibition.exhibition.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * ExhibitionCreateRequest.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 8. 최초 작성
 */
@Getter
@Setter
public class ExhibitionCreateRequest {
    private Long id;
    @NotBlank(message = "전시 코드 ID는 필수입니다.")
    @Size(max = 50, message = "전시 코드 ID는 최대 50자까지 입력 가능합니다.")
    private String exhibitionCodeId;

    @NotNull(message = "KSIC 산업 분류는 필수입니다.")
    private Long ksicId;

    @NotNull(message = "기준 연도는 필수입니다.")
    private Integer year;

    @NotBlank(message = "전시명은 필수입니다.")
    @Size(max = 255, message = "전시명은 최대 255자까지 입력 가능합니다.")
    private String name;
}
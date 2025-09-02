package com.exporum.core.domain.excel.annotation;

import com.exporum.core.domain.excel.style.ExcelCellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public @interface ExcelColumnStyle {
    /**
     * Enum implements {@link ExcelCellStyle}
     * Also, can use just class.
     * If not use Enum, enumName will be ignored
     * @see com.exporum.core.domain.excel.style.DefaultExcelCellStyle
     * @see com.exporum.core.domain.excel.style.CustomExcelCellStyle
     */
    Class<? extends ExcelCellStyle> excelCellStyleClass();

    /**
     * name of Enum implements {@link ExcelCellStyle}
     * if not use Enum, enumName will be ignored
     */
    String enumName() default "";
}

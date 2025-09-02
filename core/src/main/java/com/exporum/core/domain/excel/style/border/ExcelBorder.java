package com.exporum.core.domain.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public interface ExcelBorder {
    void applyTop(CellStyle cellStyle);

    void applyRight(CellStyle cellStyle);

    void applyBottom(CellStyle cellStyle);

    void applyLeft(CellStyle cellStyle);
}

package com.exporum.core.domain.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public interface ExcelBorders {
    void apply(CellStyle cellStyle);
}

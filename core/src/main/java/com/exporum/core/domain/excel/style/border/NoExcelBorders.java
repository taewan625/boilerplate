package com.exporum.core.domain.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class NoExcelBorders implements ExcelBorders {
    @Override
    public void apply(CellStyle cellStyle) {
        // Do nothing
    }
}

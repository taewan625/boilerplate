package com.exporum.core.domain.excel.style.color;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class NoExcelColor implements ExcelColor {

    @Override
    public void applyForeground(CellStyle cellStyle) {
        // Do nothing
    }
}

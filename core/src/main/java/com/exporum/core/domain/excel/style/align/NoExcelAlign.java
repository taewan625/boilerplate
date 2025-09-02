package com.exporum.core.domain.excel.style.align;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class NoExcelAlign implements ExcelAlign {

    @Override
    public void apply(CellStyle cellStyle) {
        // Do nothing
    }

}

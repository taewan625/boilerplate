package com.exporum.core.domain.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class DefaultExcelBorder implements ExcelBorder {
    private ExcelBorderStyle borderStyle;

    public DefaultExcelBorder(ExcelBorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    @Override
    public void applyTop(CellStyle cellStyle) {
        cellStyle.setBorderTop(borderStyle.getStyle());
    }

    @Override
    public void applyRight(CellStyle cellStyle) {
        cellStyle.setBorderRight(borderStyle.getStyle());
    }

    @Override
    public void applyBottom(CellStyle cellStyle) {
        cellStyle.setBorderBottom(borderStyle.getStyle());
    }

    @Override
    public void applyLeft(CellStyle cellStyle) {
        cellStyle.setBorderLeft(borderStyle.getStyle());
    }
}

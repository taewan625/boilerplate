package com.exporum.core.domain.excel.style.configuration;

import com.exporum.core.domain.excel.style.align.ExcelAlign;
import com.exporum.core.domain.excel.style.align.NoExcelAlign;
import com.exporum.core.domain.excel.style.border.ExcelBorders;
import com.exporum.core.domain.excel.style.border.NoExcelBorders;
import com.exporum.core.domain.excel.style.color.DefaultExcelColor;
import com.exporum.core.domain.excel.style.color.ExcelColor;
import com.exporum.core.domain.excel.style.color.NoExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class ExcelCellStyleConfiguration {

    private ExcelAlign excelAlign = new NoExcelAlign();
    private ExcelColor foregroundColor = new NoExcelColor();
    private ExcelBorders excelBorders = new NoExcelBorders();

    public ExcelCellStyleConfiguration() {

    }

    public ExcelCellStyleConfiguration excelAlign(ExcelAlign excelAlign) {
        this.excelAlign = excelAlign;
        return this;
    }

    public ExcelCellStyleConfiguration foregroundColor(int red, int blue, int green) {
        this.foregroundColor = DefaultExcelColor.rgb(red, blue, green);
        return this;
    }

    public ExcelCellStyleConfiguration excelBorders(ExcelBorders excelBorders) {
        this.excelBorders = excelBorders;
        return this;
    }

    public void configure(CellStyle cellStyle) {
        excelAlign.apply(cellStyle);
        foregroundColor.applyForeground(cellStyle);
        excelBorders.apply(cellStyle);
    }
}

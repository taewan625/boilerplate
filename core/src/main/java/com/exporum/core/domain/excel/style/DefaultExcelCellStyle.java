package com.exporum.core.domain.excel.style;

import com.exporum.core.domain.excel.style.align.DefaultExcelAlign;
import com.exporum.core.domain.excel.style.align.ExcelAlign;
import com.exporum.core.domain.excel.style.border.DefaultExcelBorders;
import com.exporum.core.domain.excel.style.border.ExcelBorderStyle;
import com.exporum.core.domain.excel.style.color.DefaultExcelColor;
import com.exporum.core.domain.excel.style.color.ExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public enum DefaultExcelCellStyle implements ExcelCellStyle {

    GREY_HEADER(DefaultExcelColor.rgb(217, 217, 217),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
    BLUE_HEADER(DefaultExcelColor.rgb(223, 235, 246),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),

    CUSTOM_BLUE_HEADER(DefaultExcelColor.rgb(53, 111, 191),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
    CUSTOM_ORANGE_HEADER(DefaultExcelColor.rgb(240, 190, 35),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),
    CUSTOM_GREEN_HEADER(DefaultExcelColor.rgb(87, 164, 84),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER),

    BODY(DefaultExcelColor.rgb(255, 255, 255),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.GENERAL_CENTER),
    PEACH_ORANGE_BODY(DefaultExcelColor.rgb(255, 204, 153),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.GENERAL_CENTER);


    private final ExcelColor backgroundColor;
    /**
     * like CSS margin or padding rule,
     * List<DefaultExcelBorder> represents rgb TOP RIGHT BOTTOM LEFT
     */
    private final DefaultExcelBorders borders;
    private final ExcelAlign align;

    DefaultExcelCellStyle(ExcelColor backgroundColor, DefaultExcelBorders borders, ExcelAlign align) {
        this.backgroundColor = backgroundColor;
        this.borders = borders;
        this.align = align;
    }

    @Override
    public void apply(CellStyle cellStyle) {
        backgroundColor.applyForeground(cellStyle);
        borders.apply(cellStyle);
        align.apply(cellStyle);
    }
}

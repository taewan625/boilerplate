package com.exporum.core.domain.excel.style;

import com.exporum.core.domain.excel.style.configuration.ExcelCellStyleConfiguration;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public abstract class CustomExcelCellStyle implements ExcelCellStyle {

    private final ExcelCellStyleConfiguration configurer = new ExcelCellStyleConfiguration();

    public CustomExcelCellStyle() {
        configure(configurer);
    }

    public abstract void configure(ExcelCellStyleConfiguration configurer);

    @Override
    public void apply(CellStyle cellStyle) {
        configurer.configure(cellStyle);
    }
}

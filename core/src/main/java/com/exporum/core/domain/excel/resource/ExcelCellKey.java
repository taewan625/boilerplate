package com.exporum.core.domain.excel.resource;

import java.util.Objects;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class ExcelCellKey {
    private final String dataFieldName;
    private final ExcelRenderLocation excelRenderLocation;

    private ExcelCellKey(String dataFieldName, ExcelRenderLocation excelRenderLocation) {
        this.dataFieldName = dataFieldName;
        this.excelRenderLocation = excelRenderLocation;
    }

    public static ExcelCellKey of(String fieldName, ExcelRenderLocation excelRenderLocation) {
        assert excelRenderLocation != null;
        return new ExcelCellKey(fieldName, excelRenderLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelCellKey that = (ExcelCellKey) o;
        return Objects.equals(dataFieldName, that.dataFieldName) &&
                excelRenderLocation == that.excelRenderLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataFieldName, excelRenderLocation);
    }
}

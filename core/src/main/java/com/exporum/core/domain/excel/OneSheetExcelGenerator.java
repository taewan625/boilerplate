package com.exporum.core.domain.excel;

import com.exporum.core.domain.excel.resource.DataFormatDecider;

import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 * - support Excel Version over 2007
 * - support one sheet rendering
 * - support different DataFormat by Class Type
 * - support Custom CellStyle according to (header or body) and data field
 */
public class OneSheetExcelGenerator<T> extends SXSSFExcelGenerator<T> {
    private static final int ROW_START_INDEX = 0;
    private static final int COLUMN_START_INDEX = 0;
    private int currentRowIndex = ROW_START_INDEX;

    public OneSheetExcelGenerator(Class<T> type) {
        super(type);
    }

    public OneSheetExcelGenerator(List<T> data, Class<T> type) {
        super(data, type);
    }

    public OneSheetExcelGenerator(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider) {
        super(data, type, dataFormatDecider);
    }

    @Override
    protected void validateData(List<T> data) {
        int maxRows = supplyExcelVersion.getMaxRows();
        if (data.size() > maxRows) {
            throw new IllegalArgumentException(
                    String.format("This concrete ExcelFile does not support over %s rows", maxRows));
        }
    }

    @Override
    public void renderExcel(List<T> data) {
        // 1. Create sheet and renderHeader
        sheet = wb.createSheet();
        renderHeadersWithNewSheet(sheet, currentRowIndex++, COLUMN_START_INDEX);

        if (data.isEmpty()) {
            return;
        }

        // 2. Render Body
        for (Object renderedData : data) {
            renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX);
        }
    }

    @Override
    public void addRows(List<T> data) {
        renderBody(data, currentRowIndex++, COLUMN_START_INDEX);
    }
}

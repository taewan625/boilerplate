package com.exporum.core.domain.excel;

import com.exporum.core.domain.excel.exception.ExcelInternalException;
import com.exporum.core.domain.excel.resource.*;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static com.exporum.core.domain.excel.utils.SuperClassReflectionUtils.getField;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public abstract class SXSSFExcelGenerator<T> implements ExcelGenerator<T> {
    protected static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;

    protected SXSSFWorkbook wb;
    protected Sheet sheet;
    protected RenderResource resource;

    /**
     *SXSSFExcelFile
     * @param type Class type to be rendered
     */
    public SXSSFExcelGenerator(Class<T> type) {
        this(Collections.emptyList(), type, new DefaultDataFormatDecider());
    }

    /**
     * SXSSFExcelFile
     * @param data List Data to render excel file. data should have at least one @ExcelColumn on fields
     * @param type Class type to be rendered
     */
    public SXSSFExcelGenerator(List<T> data, Class<T> type) {
        this(data, type, new DefaultDataFormatDecider());
    }

    /**
     * SXSSFExcelFile
     * @param data List Data to render excel file. data should have at least one @ExcelColumn on fields
     * @param type Class type to be rendered
     * @param dataFormatDecider Custom DataFormatDecider
     */
    public SXSSFExcelGenerator(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider) {
        validateData(data);
        this.wb = new SXSSFWorkbook();
        this.resource = RenderResourceFactory.prepareRenderResource(type, wb, dataFormatDecider);
        renderExcel(data);
    }

    protected void validateData(List<T> data) { }

    protected abstract void renderExcel(List<T> data);

    protected void renderHeadersWithNewSheet(Sheet sheet, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = columnStartIndex;
        for (String dataFieldName : resource.getDataFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER));
            cell.setCellValue(resource.getExcelHeaderName(dataFieldName));
        }
    }

    protected void renderBody(Object data, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = columnStartIndex;

        Drawing<?> drawing = sheet.createDrawingPatriarch(); // 이미지 삽입용

        for (String dataFieldName : resource.getDataFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = getField(data.getClass(), (dataFieldName));
                field.setAccessible(true);
                cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY));
                Object cellValue = field.get(data);

                // 👇 이미지 필드 처리 (예: "imageUrl"이라는 필드명에만 적용)
                if (dataFieldName.toLowerCase().contains("image") && cellValue instanceof String urlString) {
                    try (InputStream imageStream = new URL(urlString).openStream()) {
                        byte[] imageBytes = IOUtils.toByteArray(imageStream);
                        int pictureIdx = wb.addPicture(imageBytes, SXSSFWorkbook.PICTURE_TYPE_PNG);

                        CreationHelper helper = wb.getCreationHelper();
                        ClientAnchor anchor = helper.createClientAnchor();
                        anchor.setCol1(columnIndex);
                        anchor.setRow1(rowIndex);

                        Picture picture = drawing.createPicture(anchor, pictureIdx);

                        // ✅ 셀 크기를 이미지에 맞게 조정
                        int barcodeWidth = 330;  // px
                        int barcodeHeight = 130; // px

                        // POI 기준 변환 비율
                        int poiColWidth = (int)(barcodeWidth * 35); // 열 너비 (1/256 단위)
                        short poiRowHeight = (short)(barcodeHeight * 15); // 행 높이 (1/20pt 단위)

                        sheet.setColumnWidth(columnIndex, poiColWidth); // 열 너비 설정
                        row.setHeight(poiRowHeight);                    // 행 높이 설정

                        // 그림 크기 셀에 맞게 조정
                        picture.resize(); // resize는 셀 기준 비율, 여기선 보조용
                    } catch (Exception e) {
                        cell.setCellValue("[이미지 로드 실패]");
                    }
                } else {
                    renderCellValue(cell, cellValue);
                }

            } catch (Exception e) {
                throw new ExcelInternalException(e.getMessage(), e);
            }
        }
    }

    private void renderCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number) {
            Number numberValue = (Number) cellValue;
            cell.setCellValue(numberValue.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }

    public void write(OutputStream stream) throws IOException {
        wb.write(stream);
        wb.close();
        wb.dispose();
        stream.close();
    }

}

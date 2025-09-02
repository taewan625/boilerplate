package com.exporum.admin.domain.subscribe.model;

import com.exporum.core.domain.excel.annotation.DefaultBodyStyle;
import com.exporum.core.domain.excel.annotation.DefaultHeaderStyle;
import com.exporum.core.domain.excel.annotation.ExcelColumn;
import com.exporum.core.domain.excel.annotation.ExcelColumnStyle;
import com.exporum.core.domain.excel.style.DefaultExcelCellStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 17.
 * @description :
 */


@Getter
@Setter
@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "GREY_HEADER")
)
@DefaultBodyStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
)
public class SubscribeExcel {

    @ExcelColumn(headerName = "No")
    private long no;
    @ExcelColumn(headerName = "Email")
    private String email;
    @ExcelColumn(headerName = "First Name")
    private String firstName;
    @ExcelColumn(headerName = "Last Name")
    private String lastName;
    @ExcelColumn(headerName = "Status")
    private String status;
    @ExcelColumn(headerName = "Subscribe Date")
    private String subscribeDate;

}

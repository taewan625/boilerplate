package com.exporum.admin.domain.exhibition.model;

import com.exporum.core.domain.excel.annotation.DefaultBodyStyle;
import com.exporum.core.domain.excel.annotation.DefaultHeaderStyle;
import com.exporum.core.domain.excel.annotation.ExcelColumn;
import com.exporum.core.domain.excel.annotation.ExcelColumnStyle;
import com.exporum.core.domain.excel.style.DefaultExcelCellStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 20.
 * @description :
 */

@Getter
@Schema
@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "GREY_HEADER")
)
@DefaultBodyStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
)
public class TicketPaymentList {

    @ExcelColumn(headerName = "No")
    private long no;
    private long id;

    @ExcelColumn(headerName = "Order No")
    private String merchantUid;

    @ExcelColumn(headerName = "Imp Uid")
    private String impUid;

    @ExcelColumn(headerName = "Amount")
    private int amount;

    @ExcelColumn(headerName = "Currency")
    private String currency;

    @ExcelColumn(headerName = "Pg Provider")
    private String pgProvider;

    @ExcelColumn(headerName = "Method")
    private String payMethod;

    @ExcelColumn(headerName = "Paid Date")
    private String paidAt;

}

package com.exporum.admin.domain.payment.model;

import com.exporum.core.domain.excel.annotation.DefaultBodyStyle;
import com.exporum.core.domain.excel.annotation.DefaultHeaderStyle;
import com.exporum.core.domain.excel.annotation.ExcelColumn;
import com.exporum.core.domain.excel.annotation.ExcelColumnStyle;
import com.exporum.core.domain.excel.style.DefaultExcelCellStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
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
public class PaymentExcel {
    @ExcelColumn(headerName = "No")
    private long no;
    @ExcelColumn(headerName = "Merchant Uid")
    private String merchantUid;
    @ExcelColumn(headerName = "Imp Uid")
    private String impUid;
    @ExcelColumn(headerName = "Status")
    private String status;
    @ExcelColumn(headerName = "Ticket")
    private String name;
    @ExcelColumn(headerName = "Method")
    private String payMethod;
    @ExcelColumn(headerName = "Card Name")
    private String cardName;
    @ExcelColumn(headerName = "Amount")
    private int paidAmount;
    @ExcelColumn(headerName = "Currency")
    private String currency;
    @ExcelColumn(headerName = "Buyer Email")
    private String buyerEmail;
    @ExcelColumn(headerName = "Buyer Name")
    private String buyerName;
    @ExcelColumn(headerName = "Paid Date")
    private String paidAt;
    @ExcelColumn(headerName = "Failed Date")
    private String failedAt;
    @ExcelColumn(headerName = "Fail Reason")
    private String failReason;
    @ExcelColumn(headerName = "Cancelled Date")
    private String cancelledAt;
    @ExcelColumn(headerName = "Cancelled Reason")
    private String cancelReason;

}

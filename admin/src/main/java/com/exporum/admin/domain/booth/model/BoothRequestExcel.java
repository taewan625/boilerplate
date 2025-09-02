package com.exporum.admin.domain.booth.model;

import com.exporum.core.domain.excel.annotation.DefaultBodyStyle;
import com.exporum.core.domain.excel.annotation.DefaultHeaderStyle;
import com.exporum.core.domain.excel.annotation.ExcelColumn;
import com.exporum.core.domain.excel.annotation.ExcelColumnStyle;
import com.exporum.core.domain.excel.style.DefaultExcelCellStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Kwon taewan
 * @date : 2025. 5. 23.
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
public class BoothRequestExcel {
    @ExcelColumn(headerName = "No")
    private long no;

    @ExcelColumn(headerName = "Country")
    private String country;

    @ExcelColumn(headerName = "Industry")
    private String industry;

    @ExcelColumn(headerName = "Company")
    private String company;

    @ExcelColumn(headerName = "Brand")
    private String brand;

    @ExcelColumn(headerName = "Contact Person")
    private String contactPerson;

    @ExcelColumn(headerName = "Job Title")
    private String jobTitle;

    @ExcelColumn(headerName = "Email")
    private String email;

    @ExcelColumn(headerName = "Second Email")
    private String secondEmail;

    @ExcelColumn(headerName = "Office Number")
    private String officeNumber;

    @ExcelColumn(headerName = "Mobile Number")
    private String mobileNumber;

    @ExcelColumn(headerName = "Address")
    private String address;

    @ExcelColumn(headerName = "Homepage")
    private String homepage;

    @ExcelColumn(headerName = "Facebook")
    private String facebook;

    @ExcelColumn(headerName = "Instagram")
    private String instagram;

    @ExcelColumn(headerName = "Etc SNS")
    private String etcSns;

    @ExcelColumn(headerName = "Inquiry Date")
    private String createdAt;

}


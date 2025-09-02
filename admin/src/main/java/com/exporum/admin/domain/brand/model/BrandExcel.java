package com.exporum.admin.domain.brand.model;

import com.exporum.core.domain.excel.annotation.DefaultBodyStyle;
import com.exporum.core.domain.excel.annotation.DefaultHeaderStyle;
import com.exporum.core.domain.excel.annotation.ExcelColumn;
import com.exporum.core.domain.excel.annotation.ExcelColumnStyle;
import com.exporum.core.domain.excel.style.DefaultExcelCellStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 14.
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
public class BrandExcel {

    @ExcelColumn(headerName = "No")
    private long no;

    @ExcelColumn(headerName = "Exhibit")
    private String exhibit;

    @ExcelColumn(headerName = "Company")
    private String company;

    @ExcelColumn(headerName = "Brand")
    private String brand;

    @ExcelColumn(headerName = "Industry")
    private String industry;

    @ExcelColumn(headerName = "Booth Number")
    private String booth;

    @ExcelColumn(headerName = "Country")
    private String country;

    @ExcelColumn(headerName = "Office Number")
    private String officeNumber;

    @ExcelColumn(headerName = "Manager Name")
    private String managerName;

    @ExcelColumn(headerName = "Job Title")
    private String jobTitle;

    @ExcelColumn(headerName = "Email")
    private String email;

    @ExcelColumn(headerName = "Mobile Number")
    private String mobileNumber;

    @ExcelColumn(headerName = "Status",
            bodyStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "PEACH_ORANGE_BODY")
    )
    private String status;

    @ExcelColumn(headerName = "Introduction")
    private String introduction;
}

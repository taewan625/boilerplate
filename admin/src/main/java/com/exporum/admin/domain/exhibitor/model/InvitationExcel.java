package com.exporum.admin.domain.exhibitor.model;

import com.exporum.core.domain.excel.annotation.DefaultBodyStyle;
import com.exporum.core.domain.excel.annotation.DefaultHeaderStyle;
import com.exporum.core.domain.excel.annotation.ExcelColumn;
import com.exporum.core.domain.excel.annotation.ExcelColumnStyle;
import com.exporum.core.domain.excel.style.DefaultExcelCellStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 20.
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
public class InvitationExcel {


    @ExcelColumn(headerName = "No")
    private long no;
    //@ExcelColumn(headerName = "image")
    private String barcodeImage;

    @ExcelColumn(headerName = "Barcode")
    private String barcode;

    @ExcelColumn(headerName = "Invitation Type")
    private String invitationType;
    @ExcelColumn(headerName = "Email")
    private String receiverEmail;
    @ExcelColumn(headerName = "Name")
    private String receiverName;
    @ExcelColumn(headerName = "Company")
    private String receiverCompany;
    @ExcelColumn(headerName = "Job Title")
    private String receiverJobTitle;
    @ExcelColumn(headerName = "Country")
    private String country;
    @ExcelColumn(headerName = "City")
    private String city;
    @ExcelColumn(headerName = "PhoneNumber")
    private String receiverPhoneNumber;


    @ExcelColumn(headerName = "Exhibitor Company",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String companyName;

    @ExcelColumn(headerName = "Brand / Roastery",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String brandName;

    @ExcelColumn(headerName = "APPLICATION TYPE",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String type;

    @ExcelColumn(headerName = "Booth Number",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String boothNumber;

    @ExcelColumn(headerName = "Contact Name",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String contactName;

    @ExcelColumn(headerName = "Contact Email",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String email;

    @ExcelColumn(headerName = "Contact PhoneNumber",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String phoneNumber;


}


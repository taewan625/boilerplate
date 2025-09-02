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
public class ExhibitorExcel {
    private long id;

    @ExcelColumn(headerName = "NO",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private long no;

    @ExcelColumn(headerName = "TYPE",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private String type;

    @ExcelColumn(headerName = "특이사항",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String description;

    @ExcelColumn(headerName = "Country",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String country;

    @ExcelColumn(headerName = "상호 면세국",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private boolean mutualTaxExemption;

    @ExcelColumn(headerName = "SCAI Sales",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private boolean scaiSales;

    @ExcelColumn(headerName = "스폰서",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String sponsor;

    @ExcelColumn(headerName = "신청일",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String applicationDate;

    @ExcelColumn(headerName = "Exhibitor Name",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String company;

    @ExcelColumn(headerName = "Brand / 로스터리명",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String brand;


    @ExcelColumn(headerName = "부스넘버",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_ORANGE_HEADER")
    )
    private String boothNumber;

    @ExcelColumn(headerName = "부스개수",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private int boothCount;

    @ExcelColumn(headerName = "Send Badge",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private int sendBadgeCount;

    @ExcelColumn(headerName = "Badge Count",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private int badgeCount;

    @ExcelColumn(headerName = "Send Invitation",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private int sendInvitationCount;

    @ExcelColumn(headerName = "Invitation Count",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_GREEN_HEADER")
    )
    private int invitationCount;


    @ExcelColumn(headerName = "SCA 멤버십",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private boolean scaMembership;

    @ExcelColumn(headerName = "Industry",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String industry;

    @ExcelColumn(headerName = "담당자",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String contactName;

    @ExcelColumn(headerName = "직위",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String jobTitle;

    @ExcelColumn(headerName = "전화",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String phoneNumber;

    @ExcelColumn(headerName = "이메일",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String email;

    @ExcelColumn(headerName = "주소",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String address;

    @ExcelColumn(headerName = "홈페이지",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String homepage;

    @ExcelColumn(headerName = "인스타그램",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String instagram;

    @ExcelColumn(headerName = "페이스북",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String facebook;

    @ExcelColumn(headerName = "기타 SNS",
            headerStyle = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "CUSTOM_BLUE_HEADER")
    )
    private String etcSns;

}

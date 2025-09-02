package com.exporum.admin.domain.popup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 6.
 * @description :
 */

@Getter
@Setter
public class PopupDTO {

    private long id;
    private long exhibitionId;
    private String deviceCode;
    private String popupType;
    private String linkTargetCode;
    private String startDate;
    private String endDate;
    private String title;
    private String link;

    private boolean publish;

    private int displayOrder;

    private long fileId;
    private MultipartFile file;

    private long adminId;

}

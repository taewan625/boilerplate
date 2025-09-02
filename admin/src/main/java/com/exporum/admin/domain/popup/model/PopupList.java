package com.exporum.admin.domain.popup.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */

@Getter
@Setter
public class PopupList {

    private long no;
    private long id;
    private String linkType;
    private String title;
    private String publicationPeriod;
    private String startDate;
    private String endDate;
    private String link;
    private long fileId;
    private String path;
    private int displayOrder;
    private boolean published;
}

package com.exporum.client.domain.home.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 10.
 * @description :
 */

@Getter
@Setter
public class Popup {
    private long id;
    private int displayOrder;
    private String path;
    private String linkTarget;
    private String link;
}

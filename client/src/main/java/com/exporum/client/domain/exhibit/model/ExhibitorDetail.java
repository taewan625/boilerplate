package com.exporum.client.domain.exhibit.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 30.
 * @description :
 */


@Getter
@Setter
public class ExhibitorDetail {

    private long id;
    private int year;
    private String companyName;
    private String brandName;
    private String path;
    private String introduction;
    private String boothNumber;
    private String homepage;
    private String facebook;
    private String instagram;
    private String twitter;
    private String etcSns;

}

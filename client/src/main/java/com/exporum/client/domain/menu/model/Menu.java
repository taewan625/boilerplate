package com.exporum.client.domain.menu.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Menu {
    private long id;
    private long upperId;
    private int depth;
    private int order;
    private String url;
    private String name;
    private String linkTargetCode; //_self, _blank
    private String deviceCode; //both, web, mobile
    private String menuTypeCode; //client, admin

    private List<Menu> subMenus;

    public List<Menu> getSubMenus() {
        if (subMenus == null) subMenus = new ArrayList<>();
        return subMenus;
    }
}
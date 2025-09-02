package com.exporum.client.domain.menu.mapper;

import com.exporum.client.domain.menu.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> getMenus();

    List<Menu> getCurrentMenus(String currentPath);
}

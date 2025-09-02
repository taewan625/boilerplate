package com.exporum.client.domain.menu.service;

import com.exporum.client.domain.menu.mapper.MenuMapper;
import com.exporum.client.domain.menu.model.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;

    @Cacheable(value = "activeMenusCache")
    public List<Menu> getMenus() {
        //메뉴 조회(depth > order 기준 정렬)
        List<Menu> orderMenus = menuMapper.getMenus();

        //탐색 목적 HashMap
        Map<Long, Menu> menuMap = new HashMap<>();
        for (Menu menu : orderMenus) {
            menuMap.put(menu.getId(), menu);
        }

        //반환 List
        List<Menu> returnList = new ArrayList<>();

        for (Menu menu : orderMenus) {
            //메인 메뉴
            if (menu.getDepth() == 1) {
                returnList.add(menu);
            }
            //하위 메뉴
            else {
                long upperId = menu.getUpperId();
                //상위 id가 존재하는 경우 해당 상위 Menu 하위에 Menu 정보 넣기
                if (upperId != -1) {
                    Menu upperMenu = menuMap.get(upperId);
                    upperMenu.getSubMenus().add(menu);
                }
            }
        }

        return returnList;
    }

    @Cacheable(value = "currentMenuDepthCache", key = "#currentPath")
    public List<Menu> getCurrentMenus(String currentPath) {
        //현재 메뉴에서 상위 메뉴 목록 조회
        return menuMapper.getCurrentMenus(currentPath);
    }

}

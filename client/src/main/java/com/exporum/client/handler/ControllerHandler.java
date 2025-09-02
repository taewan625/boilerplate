package com.exporum.client.handler;

import com.exporum.client.domain.menu.model.Menu;
import com.exporum.client.domain.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * @author: Kwon Taewan
 * @date : 2025. 6. 9.
 * @description :
 */

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerHandler {
    private final MenuService menuService;

    //현재 경로
    @ModelAttribute("currentUrl")
    public String getCurrentUrl(HttpServletRequest request){
        return request.getServletPath();
    }

    //현재 경로 관련 메뉴 정보
    @ModelAttribute("currentMenus")
    public List<Menu> getCurrentMenus(HttpServletRequest request){
        return menuService.getCurrentMenus(request.getServletPath());
    }

    //메뉴 목록
    @ModelAttribute("menus")
    public List<Menu> getMenus(){
        return menuService.getMenus();
    }

}

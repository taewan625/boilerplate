package com.exporum.admin.domain.v1.exhibition;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ExhibitionController.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 7. 최초 작성
 */
@Controller
@RequestMapping("exhibition")
public class ExhibitionController {

    // 전시 목록
    @GetMapping("/exhibition")
    public String selectExhibitionList() {
        return "exhibition/exhibition/list";
    }

    // 전시 생성
    @GetMapping("/exhibition/create")
    public String createExhibition() {
        return "exhibition/exhibition/create";
    }

    // 전시 수정
    @GetMapping("/exhibition/update/{id}")
    public String updateExhibition(@PathVariable Long id, Model model) {
        model.addAttribute("exhibitionId", id);
        return "exhibition/exhibition/update";
    }

    // 전시 상세
    @GetMapping("/exhibition/detail/{id}")
    public String selectExhibitionDetail(@PathVariable Long id, Model model) {
        model.addAttribute("exhibitionId", id);
        return "exhibition/exhibition/detail";
    }

    // 관리자 목록
    @GetMapping("/manager")
    public String selectManagerList() {
        return "exhibition/manager/list";
    }

    // 관리자 생성
    @GetMapping("/manager/create")
    public String createManager() {
        return "exhibition/manager/create";
    }

    // 관리자 수정
    @GetMapping("/manager/update/{id}")
    public String updateManager(@PathVariable Long id, Model model) {
        model.addAttribute("managerId", id);
        return "exhibition/manager/update";
    }

    // 관리자 상세
    @GetMapping("/manager/detail/{id}")
    public String selectManagerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("managerId", id);
        return "exhibition/manager/detail";
    }

    // 역할 목록
    @GetMapping("/role")
    public String selectRoleList() {
        return "exhibition/role/list";
    }

    // 역할 생성
    @GetMapping("/role/create")
    public String createRole() {
        return "exhibition/manager/create";
    }

    // 역할 수정
    @GetMapping("/role/update/{id}")
    public String updateRole(@PathVariable Long id, Model model) {
        model.addAttribute("roleId", id);
        return "exhibition/manager/update";
    }

    // 역할 상세
    @GetMapping("/role/detail/{id}")
    public String selectRoleDetail(@PathVariable Long id, Model model) {
        model.addAttribute("roleId", id);
        return "exhibition/role/detail";
    }
}

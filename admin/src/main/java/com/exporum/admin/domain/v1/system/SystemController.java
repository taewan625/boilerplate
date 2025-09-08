package com.exporum.admin.domain.v1.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * CodeController.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 8. 최초 작성
 */
@Controller
@RequestMapping("system")
public class SystemController {
    //공통코드 목록
    @GetMapping("/code")
    public String selectCodeList() {
        return "/system/code/list";
    }

    // 전시 상세
    @GetMapping("/code/detail/{id}")
    public String selectCodeDetail(@PathVariable Long id, Model model) {
        model.addAttribute("codeId", id);
        return "system/code/detail";
    }

    //공통 코드 등록
    @GetMapping("/code/create")
    public String createCode() {
        return "/system/code/create";
    }

    //공통 코드 수정
    @GetMapping("/code/update/{id}")
    public String updateCode(@PathVariable Long id, Model model) {
        model.addAttribute("codeId", id);
        return "/system/code/update";
    }
}

package com.exporum.admin.domain.exhibitor.controller;

import com.exporum.admin.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 14.
 * @description :
 */

@Controller
@RequiredArgsConstructor
public class ExhibitorController {

    private final CommonService commonService;

    @GetMapping("/exhibitor")
    public String exhibitor(Model model) {
        model.addAttribute("applicationTypes", commonService.getSelectOption("APPLICATION-TYPE"));
        model.addAttribute("sponsors", commonService.getSelectOption("SPONSOR-TYPE"));
        model.addAttribute("industries", commonService.getSelectOption("INDUSTRY-TYPE"));
        return "exhibitor/exhibitor";

    }

    @GetMapping("/exhibitor/register")
    public String exhibitorAdd(Model model) {
        this.getCodes(model);
        return "exhibitor/exhibitor-write";
    }

    @GetMapping("/exhibitor/{id}")
    public String exhibitorDetail(@PathVariable long id, Model model) {
        this.getCodes(model);
        return "exhibitor/exhibitor-detail";
    }


    @GetMapping("/exhibitor/{id}/modify")
    public String exhibitorModify(@PathVariable long id, Model model) {
        this.getCodes(model);
        return "exhibitor/exhibitor-modify";
    }






    private void getCodes(Model model) {
        model.addAttribute("countries", commonService.getCountryOption());
        model.addAttribute("callingCodes", commonService.getCallingCodeOption());
        model.addAttribute("industries", commonService.getSelectOption("INDUSTRY-TYPE"));
        model.addAttribute("earlyApplications", commonService.getSelectOption("EARLY-APPLICATION-TYPE"));
        model.addAttribute("sponsors", commonService.getSelectOption("SPONSOR-TYPE"));
        model.addAttribute("applicationTypes", commonService.getSelectOption("APPLICATION-TYPE"));
    }
}

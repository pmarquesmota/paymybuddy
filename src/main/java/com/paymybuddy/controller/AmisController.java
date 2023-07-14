package com.paymybuddy.controller;

import com.paymybuddy.entity.User;
import com.paymybuddy.service.AmisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
public class AmisController {
    @Autowired
    AmisService amisService;

    @PutMapping("/ami/update/{userParam}")
    public String updateAmi(@PathVariable Long userParam,
                            @RequestParam Long ancienAmiParam,
                            @RequestParam Long nouvelAmiParam,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = amisService.updateAmi(userParam, ancienAmiParam, nouvelAmiParam);

            model.addAttribute("Ami", user);
            return "ami/update";
        } catch (NoSuchElementException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/amis/list";
        }
    }

}

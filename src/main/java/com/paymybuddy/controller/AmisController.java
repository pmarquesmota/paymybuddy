package com.paymybuddy.controller;

import com.paymybuddy.entity.User;
import com.paymybuddy.service.AmisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/amis")
public class AmisController {
    @Autowired
    AmisService amisService;

    @GetMapping("/{userParam}")
    public String listAmis(@PathVariable Long userParam,
                           Model model,
                           RedirectAttributes redirectAttributes
                           ){
        try {

            List<User> users = amisService.listAmis(userParam);

            model.addAttribute("Ami", users);
            return "amis/list";
        } catch (NoSuchElementException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "amis/list";
        }

    }

    @PostMapping("/{userId}/friends/{friendId}")
    public String addAmis(@PathVariable Long userId,
                          @PathVariable Long friendId,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            User user = amisService.addAmis(userId, friendId);

            model.addAttribute("Ami", user);
            return "ami/list";
        } catch (NoSuchElementException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/amis/list";
        }
    }

    @PutMapping("/{userId}")
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

    @DeleteMapping("/{user}/friends/{friend}")
    public String deleteAmis(@PathVariable("user") Long userId,
                            @PathVariable("friend") Long friendId,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            amisService.deleteAmi(userId, friendId);
            return "redirect:/amis/list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/amis/list";
        }
    }

}

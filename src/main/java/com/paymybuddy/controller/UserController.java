package com.paymybuddy.controller;

import com.paymybuddy.entity.User;
import com.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/utilisateur")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{userParam}")
    public String listUtilisateur(@PathVariable Long userParam,
                                  Model model,
                                  RedirectAttributes redirectAttributes
    ) {
        try {
            User utilisateur = userService.getUser(userParam);

            model.addAttribute("Utilisateur", utilisateur);
            return "/list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message",
                    "L'utilisateur " + userParam + "n'existe pas"
            );
            return "/list";
        }

    }

    @GetMapping("/")
    public String listUtilisateurs(Model model,
                                   RedirectAttributes redirectAttributes
    ) {
        List<User> utilisateurs = userService.getUsers();

        model.addAttribute("Utilisateurs", utilisateurs);
        return "/list";
    }

    @PostMapping("/")
    public String addUtilisateur(
            @RequestBody User utilisateur,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(utilisateur);

            model.addAttribute("Utilisateur", utilisateur);
            return "/list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message",
                    "Vous devez spécifier un utilisateur.");
            return "redirect:/list";
        }
    }

    @PutMapping("/{userId}")
    public String updateUtilisateur(
            @PathVariable Long userId,
            @RequestParam User utilisateur,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(utilisateur);

            model.addAttribute("Utilisateur", utilisateur);
            return "/list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/list";
        }
    }

    @DeleteMapping("/{userId}")
    public String deleteUtilisateur(
            @PathVariable Long userId,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(userId);
            return "redirect:/list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/list";
        }
    }


}

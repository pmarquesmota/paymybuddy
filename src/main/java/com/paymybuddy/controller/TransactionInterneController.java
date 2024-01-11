package com.paymybuddy.controller;

import com.paymybuddy.service.TransactionExterneService;
import com.paymybuddy.service.TransactionInterneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/TransactionInterne")
public class TransactionInterneController {
    @Autowired
    TransactionInterneService transactionInterneService;

    @PostMapping("/credit/{userParam}/friend/{amiParam}")
    public String TransactionInterneCredit(
            @PathVariable Long userParam,
            @PathVariable Long amiParam,
            @RequestBody BigDecimal montant,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {

            transactionInterneService.credit(userParam, amiParam, montant);

            model.addAttribute("transactionExterne", userParam);
            return "/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "/";
        }
    }

    @PostMapping("/debit/{userParam}/friend/{amiParam}")
    public String TransactionInterneDebit(
            @PathVariable Long userParam,
            @PathVariable Long amiParam,
            @RequestBody BigDecimal montant,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        try {

            transactionInterneService.debit(userParam, amiParam, montant);

            model.addAttribute("transactionExterne", userParam);
            return "/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "/";
        }

    }

}

package com.paymybuddy.controller;

import com.paymybuddy.service.TransactionExterneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/TransactionExterne")
public class TransactionExterneController {
    @Autowired
    TransactionExterneService transactionExterneService;

    @PostMapping("/credit/{userParam}")
    public String TransactionsExternesCredit(
            @PathVariable Long userParam,
            @RequestBody BigDecimal montant,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {

            transactionExterneService.credit(userParam, montant);

            model.addAttribute("transactionExterne", userParam);
            model.addAttribute("credit", montant);
            return "/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "/";
        }
    }

    @PostMapping("/debit/{userParam}")
    public String TransactionsExternesDebit(
            @PathVariable Long userParam,
            @RequestBody BigDecimal montant,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        try {

            transactionExterneService.debit(userParam, montant);

            model.addAttribute("transactionExterne", userParam);
            model.addAttribute("debit", montant);
            return "/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "/";
        }

    }
}
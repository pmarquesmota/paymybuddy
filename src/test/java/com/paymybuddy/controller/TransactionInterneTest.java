package com.paymybuddy.controller;

import com.paymybuddy.service.TransactionInterneService;
import com.paymybuddy.service.TransactionInterneService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

public class TransactionInterneTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    TransactionInterneService transactionInterneService;

    @Test
    public void transactionsInterneCreditWithoutErrors() throws Exception {
        Mockito
                .doNothing()
                .when(transactionInterneService)
                .credit(anyLong(),anyLong(),any(BigDecimal.class));

        mvc.perform(post("/TransactionInterne/credit/42/friend/69")
                        .content("421")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("/"))
                .andExpect(model().attribute("transactionInterne", is(42L)));

    }

    @Test
    public void transactionsInternesCreditWithErrors() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'utilisateur "
                    + invocation.getArguments()[0]
                    + " n'existe pas"
            );
        }).when(transactionInterneService).credit(
                anyLong(),
                anyLong(),
                any(BigDecimal.class)
        );

        mvc.perform(post("/TransactionInterne/credit/42/friend/69")
                        .content("421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"));
    }

    @Test
    public void transactionsInternesCreditWithErrors2() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'ami "
                    + invocation.getArguments()[1]
                    + " n'existe pas"
            );
        }).when(transactionInterneService).credit(
                42L,
                anyLong(),
                any(BigDecimal.class)
        );

        mvc.perform(post("/TransactionInterne/credit/42/friend/69")
                        .content("421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'ami 69 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void transactionsInternesDebitWithoutErrors() throws Exception {
        Mockito
                .doNothing()
                .when(transactionInterneService)
                .debit(
                        anyLong(),
                        anyLong(),
                        any(BigDecimal.class)
                );

        mvc.perform(post("/TransactionInterne/debit/42/friend/69")
                        .content("421")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("/"))
                .andExpect(model().attribute("transactionInterne", is(42L)));

    }

    @Test
    public void transactionsInternesDebitWithErrors() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'utilisateur "
                    + invocation.getArguments()[0]
                    + " n'existe pas"
            );
        }).when(transactionInterneService).debit(
                anyLong(),
                anyLong(),
                any(BigDecimal.class)
        );

        mvc.perform(post("/TransactionInterne/debit/42/friend/69")
                        .content("421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void transactionsInternesDebitWithErrors2() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'ami "
                    + invocation.getArguments()[1]
                    + " n'existe pas"
            );
        }).when(transactionInterneService).debit(
                42L,
                anyLong(),
                any(BigDecimal.class)
        );

        mvc.perform(post("/TransactionInterne/debit/42/friend/69")
                        .content("421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'ami 69 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

}

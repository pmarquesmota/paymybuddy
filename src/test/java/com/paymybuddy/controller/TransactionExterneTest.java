package com.paymybuddy.controller;

import com.paymybuddy.entity.User;
import com.paymybuddy.service.TransactionExterneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AmisController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionExterneTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    TransactionExterneService transactionExterneService;

    @Test
    public void transactionsExternesCreditWithoutErrors() throws Exception {
        Mockito.doNothing().when(transactionExterneService).credit(anyLong(),any(BigDecimal.class));

        mvc.perform(post("/TransactionExterne/credit/42"))
                .andExpect(status().isOk())
                .andExpect(view().name("/"))
                .andExpect(model().attribute("transactionExterne", is(42L)));

    }

    @Test
    public void transactionsExternesCreditWithErrors() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'utilisateur "
                    + invocation.getArguments()[0]
                    + " n'existe pas"
            );
        }).when(transactionExterneService).credit(
                anyLong(),
                any(BigDecimal.class)
        );

        mvc.perform(post("/TransactionExterne/credit/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"));
    }

    @Test
    public void transactionsExternesDebitWithoutErrors() throws Exception {
        Mockito.doNothing().when(transactionExterneService).debit(anyLong(),any(BigDecimal.class));

        mvc.perform(post("/debit/42"))
                .andExpect(status().isOk())
                .andExpect(view().name("/"))
                .andExpect(model().attribute("transactionExterne", is(42L)));
    }

    @Test
    public void transactionsExternesDebitWithErrors() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'utilisateur "
                    + invocation.getArguments()[0]
                    + " n'existe pas"
            );
        }).when(transactionExterneService).debit(
                anyLong(),
                any(BigDecimal.class)
        );

        mvc.perform(post("/TransactionExterne/debit/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"));
    }
}

package com.paymybuddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.entity.User;
import com.paymybuddy.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AmisController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    public void listUtilisateurWithoutErrors() throws Exception {
        User user = new User();
        when(userService.getUser(anyLong())).thenReturn(user);

        mvc.perform(get("/utilisateur/42"))
                .andExpect(status().isOk())
                .andExpect(view().name("/list"))
                .andExpect(model().attribute("Utilisateur", is(user)));

    }

    @Test
    public void listUtilisateurWithErrors() throws Exception {
        when(userService.getUser(anyLong())).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/utilisateur/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));

    }

    @Test
    public void listUtilisateursWithoutErrors() throws Exception {
        List<User> users = new ArrayList<>();
        when(userService.getUsers()).thenReturn(users);

        mvc.perform(get("/utilisateur/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/list"))
                .andExpect(model().attribute("Utilisateurs", is(users)));

    }

    @Test
    public void addUtilisateurWithoutErrors() throws Exception {
        User user = new User(
                "toto",
                "toto55",
                "0",
                BigDecimal.valueOf(0L),
                new ArrayList<>()
        );
        Mockito.doNothing().when(userService).saveUser(
                any()
        );
        mvc.perform(MockMvcRequestBuilders
                        .post("/utilisateur")
                        .content(asJsonString(
                                new User(
                                        "toto",
                                        "toto55",
                                        "0",
                                        BigDecimal.valueOf(0L),
                                        new ArrayList<>()
                                )
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(model().attribute("Utilisateurs", is(user))
                );
    }

    @Test
    public void addUtilisateurWithErrors() throws Exception {
        when(userService.saveUser(any())).thenThrow(
                IllegalArgumentException.class);

        mvc.perform(MockMvcRequestBuilders
                        .post("/utilisateur")
                        .content(asJsonString(
                                null
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "Vous devez spécifier un utilisateur."))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof IllegalArgumentException)
                );

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package com.paymybuddy.controller;

import com.paymybuddy.entity.User;
import com.paymybuddy.service.AmisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AmisController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AmisControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    AmisService amisService;

    @Test
    public void listAmisWithoutErrors() throws Exception {
        User user = new User();
        List<User> users = Arrays.asList(user);

        when(amisService.listAmis(anyLong())).thenReturn(users);

        mvc.perform(get("/ami/42"))
                .andExpect(status().isOk())
                .andExpect(view().name("ami/list"))
                .andExpect(model().attribute("Ami", is(user)));

    }

    @Test
    public void listAmisWithErrors() throws Exception {

        when(amisService.listAmis(anyLong())).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/ami/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void addAmisWithoutError() throws Exception {
        User user = new User();
        when(amisService.addAmis(
                anyLong(),
                anyLong()
        )).thenReturn(user);

        mvc.perform(post("/amis/69/friends/42")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ami/list"))
                .andExpect(model().attribute("Ami", is(user)));
    }

    @Test
    public void addAmisWithError() throws Exception {
        User user = new User();
        when(amisService.addAmis(
                anyLong(),
                anyLong()
        )).thenThrow(NoSuchElementException.class);

        mvc.perform(post("/amis/69/friends/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 69 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void addAmisWithError2() throws Exception {
        User user = new User();
        when(amisService.addAmis(
                69L,
                anyLong()
        )).thenThrow(NoSuchElementException.class);

        mvc.perform(post("/amis/69/friends/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 69 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void addAmisWithError3() throws Exception {
        User user = new User();
        when(amisService.addAmis(
                anyLong(),
                42L
        )).thenThrow(NoSuchElementException.class);

        mvc.perform(post("/amis/69/friends/42"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'ami 42 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void updateAmisWithoutErrors() throws Exception {
        User user = new User();
        when(amisService.updateAmi(
                anyLong(),
                anyLong(),
                anyLong()
        )).thenReturn(user);

        mvc.perform(put("/ami/42")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "42")
                        .param("nouvelAmiParam", "42")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ami/update"))
                .andExpect(model().attribute("Ami", is(user)));

    }

    @Test
    public void updateAmisWithError() throws Exception {
        when(amisService.updateAmi(
                anyLong(),
                anyLong(),
                anyLong()
        )).thenAnswer(
                (Answer) invocation -> {
                    throw new NoSuchElementException("L'utilisateur "
                            + invocation.getArguments()[0]
                            + " n'existe pas"
                    );
                });

        mvc.perform(put("/amis/42")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "69")
                        .param("nouvelAmiParam", "421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void updateAmisWithError2() throws Exception {
        when(amisService.updateAmi(
                anyLong(),
                anyLong(),
                anyLong()
        )).thenAnswer(
                (Answer) invocation -> {
                    throw new NoSuchElementException("L'ancien ami "
                            + invocation.getArguments()[1]
                            + " n'existe pas"
                    );
                });

        mvc.perform(put("/amis/42")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "69")
                        .param("nouvelAmiParam", "421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'ancien ami 69 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void updateAmisWithError3() throws Exception {
        when(amisService.updateAmi(
                anyLong(),
                anyLong(),
                anyLong()
        )).thenAnswer(
                (Answer) invocation -> {
                    throw new NoSuchElementException("Le nouvel ami "
                            + invocation.getArguments()[2]
                            + " n'existe pas"
                    );
                });

        mvc.perform(put("/amis/42")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "69")
                        .param("nouvelAmiParam", "421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "Le nouvel ami 421 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void deleteAmisWithoutErrors() throws Exception {
        Mockito.doNothing().when(amisService).deleteAmi(
                anyLong(),
                anyLong());

        mvc.perform(delete("/ami/42/friends/69"))
                .andExpect(status().isOk())
                .andExpect(view().name("ami/list"));
    }

    @Test
    public void deleteAmisWithError() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'utilisateur "
                    + invocation.getArguments()[0]
                    + " n'existe pas"
            );
        }).when(amisService).deleteAmi(
                anyLong(),
                anyLong()
        );

        mvc.perform(delete("/amis/42/friends/69")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "69")
                        .param("nouvelAmiParam", "421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'utilisateur 42 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

    @Test
    public void deleteAmisWithError2() throws Exception {
        Mockito.doAnswer(invocation -> {
            throw new NoSuchElementException("L'ami "
                    + invocation.getArguments()[1]
                    + " n'existe pas"
            );
        }).when(amisService).deleteAmi(
                42L,
                anyLong()
        );

        mvc.perform(delete("/amis/42/friends/69")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "69")
                        .param("nouvelAmiParam", "421")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"))
                .andExpect(MockMvcResultMatchers.flash().attribute(
                        "message",
                        "L'ami 69 n'existe pas"))
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof NoSuchElementException));
    }

}

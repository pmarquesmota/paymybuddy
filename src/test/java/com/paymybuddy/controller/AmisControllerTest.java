package com.paymybuddy.controller;

import com.paymybuddy.entity.User;
import com.paymybuddy.service.AmisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void amisGetUpdateWithoutErrors() throws Exception {
        User user = new User();
        when(amisService.updateAmi(
                anyLong(),
                anyLong(),
                anyLong()
        )).thenReturn(user);

        mvc.perform(post("/ami/update/42")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "42")
                        .param("nouvelAmiParam", "42")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ami/update"))
                .andExpect(model().attribute("Ami", is(user)));

    }

    @Test
    public void amisGetUpdateWithError() throws Exception {
        when(amisService.updateAmi(
                anyLong(),
                anyLong(),
                anyLong()
        )).thenThrow(NoSuchElementException.class);

        mvc.perform(post("/ami/update/42")
                        .param("userParam", "42")
                        .param("ancienAmiParam", "42")
                        .param("nouvelAmiParam", "42")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/amis/list"));
    }
}

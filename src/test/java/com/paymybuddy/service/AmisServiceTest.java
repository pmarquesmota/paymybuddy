package com.paymybuddy.service;

import com.paymybuddy.entity.User;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmisServiceTest {
    @InjectMocks
    UserRepository userRepository;

    @Mock
    User user;

    @InjectMocks
    AmisService amisService;

    @Test
    public void saveAmi_should_pass(){
        List<User> amis = new ArrayList<>();
        User ami = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(ami));
        when(user.getAmis()).thenReturn(amis);
        doNothing().when(amis).add(isA(User.class));
        when(userRepository.save(any())).thenReturn(ami);

        amisService.saveAmi(42L, 42L);
        User ami_to_compare = userRepository.findById(42L).orElse(null);
        assertEquals(ami,ami_to_compare);
    }

    @Test
    public void saveAmi_should_pass_then_fail(){
        User ami = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(ami)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> amisService.saveAmi(42L, 42L));
    }

    @Test
    public void saveAmi_should_fail_then_pass(){
        User ami = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty()).thenReturn(Optional.of(ami));
        assertThrows(NoSuchElementException.class, () -> amisService.saveAmi(42L, 42L));
    }

    @Test
    public void saveAmi_should_fail(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> amisService.saveAmi(42L, 42L));
    }

    @Test
    public void listAmis_shouldReturnOK(){
        List<User> amis = new ArrayList<>();
        User ami = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(ami));
        when(user.getAmis()).thenReturn(amis);

        List<User> amis_to_compare = amisService.listAmis(42L);
        assertEquals(amis, amis_to_compare);
    }

    @Test
    public void listAmis_shouldReturnNotOK(){
        doThrow(new NoSuchElementException()).when(userRepository).findById(anyLong());

        assertThrows(NoSuchElementException.class, () -> amisService.listAmis(42L));
    }

    @Test
    public void addAmis_shouldReturnOK(){
        List<User> amis = new ArrayList<>();
        User ami = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(ami));
        Mockito
                .doNothing()
                .when(userRepository)
                .save(any());
        when(user.getAmis()).thenReturn(amis);

        User staticAmi = new User();
        amis.add(ami);
        staticAmi.setAmis(amis);

        User ami_to_compare = amisService.addAmis(42L, 69L);
        assertEquals(staticAmi, ami_to_compare);
    }

    @Test
    public void addAmis_shouldReturnNotOK(){
        doThrow(new NoSuchElementException()).when(userRepository).findById(anyLong());

        assertThrows(NoSuchElementException.class, () -> amisService.addAmis(42L, 69L));
    }


}

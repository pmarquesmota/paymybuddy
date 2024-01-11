package com.paymybuddy.service;

import com.paymybuddy.entity.TransactionExterne;
import com.paymybuddy.entity.User;
import com.paymybuddy.repository.TransactionExterneRepository;
import com.paymybuddy.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionExterneServiceTest {
    @InjectMocks
    TransactionExterneRepository transactionExterneRepository;

    @InjectMocks
    UserRepository userRepository;

    @InjectMocks
    TransactionExterneService transactionExterneService;

    @Mock
    User user;

    @Test
    public void credit_should_pass(){
        User user = new User();
        TransactionExterne transactionExterne =
                new TransactionExterne(42L, "credit", new BigDecimal(69), user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).save(any());
        doNothing().when(transactionExterneRepository).save(any());

        try {
            transactionExterneService.credit(42L, new BigDecimal(69));
        } catch(Exception e){}
        TransactionExterne transactionExterne_to_compare =
                transactionExterneRepository.findById(42L).orElse(null);
        assertEquals(transactionExterne, transactionExterne_to_compare);
    }

    @Test
    public void credit_should_fail(){
        when(transactionExterneRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionExterneService.credit(42L, BigDecimal.valueOf(42L)));
    }

    @Test
    public void debit_should_pass(){
        User user = new User();
        TransactionExterne transactionExterne =
                new TransactionExterne(42L, "debit", new BigDecimal(69), user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(user.getSolde()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(user).setSolde(any());
        doNothing().when(userRepository).save(any());
        doNothing().when(transactionExterneRepository).save(any());
        try {
            transactionExterneService.debit(42L, new BigDecimal(69));
        } catch(Exception e){}
            TransactionExterne transactionExterne_to_compare =
                transactionExterneRepository.findById(42L).orElse(null);
        assertEquals(transactionExterne, transactionExterne_to_compare);
    }

    @Test
    public void debit_should_fail(){
        when(transactionExterneRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionExterneService.debit(42L, BigDecimal.valueOf(42L)));
    }

}

package com.paymybuddy.service;

import com.paymybuddy.entity.TransactionInterne;
import com.paymybuddy.entity.User;
import com.paymybuddy.repository.TransactionInterneRepository;
import com.paymybuddy.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionInterneServiceTest {
    @InjectMocks
    TransactionInterneRepository transactionInterneRepository;

    @InjectMocks
    TransactionInterneService transactionInterneService;
    
    @InjectMocks
    UserRepository userRepository;
    
    @Test
    public void CreditShouldPass(){
        User crediteur = new User();
        User debiteur = new User();
        TransactionInterne TransactionInterne =
                new TransactionInterne(42L, new BigDecimal(69), crediteur, debiteur, new BigDecimal(69));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).save(any());
        doNothing().when(transactionInterneRepository).save(any());

        try {
            transactionInterneService.credit(42L, 42L, new BigDecimal(69));
        } catch(Exception e){}
        TransactionInterne TransactionInterne_to_compare =
                transactionInterneRepository.findById(42L).orElse(null);
        assertEquals(TransactionInterne, TransactionInterne_to_compare);
    }

    @Test
    public void credit_should_fail(){
        when(transactionInterneRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionInterneService.credit(42L, 42L, new BigDecimal(69)));
    }


}

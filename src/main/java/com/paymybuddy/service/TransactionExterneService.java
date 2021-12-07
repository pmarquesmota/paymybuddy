package com.paymybuddy.service;

import com.paymybuddy.entity.TransactionExterne;
import com.paymybuddy.entity.User;
import com.paymybuddy.repository.TransactionExterneRepository;
import com.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.NoSuchElementException;

@Service
public class TransactionExterneService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionExterneRepository transactionExterneRepository;

    @Transactional
    void credit(Long userid, Double montant){
        User u = userRepository.findById(userid).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + userid + " n'existe pas"));
        u.setSolde(u.getSolde()+montant);
        userRepository.save(u);

        TransactionExterne t = new TransactionExterne();
        t.setMontant(montant);
        t.setType("credit");
        t.setUser(u);
        transactionExterneRepository.save(t);
    }

    @Transactional
    public void debit(Long userid, Double montant) throws Exception {
        User u = userRepository.findById(userid).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + userid + " n'existe pas"));
        if(u.getSolde() > montant) {
            u.setSolde(u.getSolde() - montant);
            userRepository.save(u);
        } else {
            throw new Exception("L'utilisateur " + userid + "n'a pas assez d'argent");
        }
        TransactionExterne t = new TransactionExterne();
        t.setMontant(montant);
        t.setType("debit");
        t.setUser(u);
        transactionExterneRepository.save(t);
    }
}

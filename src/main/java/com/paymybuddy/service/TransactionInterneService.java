package com.paymybuddy.service;

import com.paymybuddy.entity.TransactionInterne;
import com.paymybuddy.entity.User;
import com.paymybuddy.repository.TransactionInterneRepository;
import com.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class TransactionInterneService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionInterneRepository transactionInterneRepository;

    @Transactional
    public void credit(Long userid, Long amiid, BigDecimal montant) throws Exception {
        User f = userRepository.findById(amiid).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + amiid + " n'existe pas"));
        User u = userRepository.findById(userid).orElseThrow(() ->
                new NoSuchElementException("L'ami " + userid + " n'existe pas"));
        u.setSolde(u.getSolde().add(montant));
        userRepository.save(u);

        TransactionInterne t = new TransactionInterne();
        t.setMontant(montant);
        t.setCrediteur(u);
        t.setDebiteur(f);
        t.setCommission(montant.multiply(BigDecimal.valueOf(0.005)));
        transactionInterneRepository.save(t);
    }

    @Transactional
    public void debit(Long userid, Long amiid, BigDecimal montant) throws Exception {
        User u = userRepository.findById(userid).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + userid + " n'existe pas"));
        if(u.getSolde().compareTo(montant) > 0) {
            u.setSolde(u.getSolde().subtract(montant));
            userRepository.save(u);
        } else {
            throw new Exception("L'utilisateur " + userid + "n'a pas assez d'argent");
        }
        User f = userRepository.findById(amiid).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + amiid + " n'existe pas"));
        f.setSolde(f.getSolde().add(montant));
        userRepository.save(f);

        TransactionInterne t = new TransactionInterne();
        t.setMontant(montant);
        t.setCrediteur(f);
        t.setDebiteur(u);
        t.setCommission(montant.multiply(BigDecimal.valueOf(0.005)));
        transactionInterneRepository.save(t);
    }
}

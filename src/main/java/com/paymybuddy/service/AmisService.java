package com.paymybuddy.service;

import com.paymybuddy.entity.User;
import com.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AmisService {
    @Autowired
    private UserRepository userRepository;

    public void saveAmi (Long user, Long ami){
        User u = userRepository.findById(user).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + user + " n'existe pas"));
        User f = userRepository.findById(ami).orElseThrow(() ->
                new NoSuchElementException("L'ami " + ami + " n'existe pas"));

        u.getAmis().add(f);

        userRepository.save(u);
    }

    public List<User> listAmis(Long id)  {
        User u = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur' " + id + " n'existe pas"));
        return u.getAmis();
    }

    public void updateAmi(Long user, Long ancienAmi, Long nouvelAmi){
        User u = userRepository.findById(user).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + user + " n'existe pas"));
        User a = userRepository.findById(ancienAmi).orElseThrow(() ->
                new NoSuchElementException("L'ami " + ancienAmi + " n'existe pas"));
        User n = userRepository.findById(nouvelAmi).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + nouvelAmi + " n'existe pas"));

        u.getAmis().remove(a);
        u.getAmis().add(n);
        userRepository.save(u);
    }

    public void deleteAmi(Long user, Long ami){
        User u = userRepository.findById(user).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + user + " n'existe pas"));
        User f = userRepository.findById(ami).orElseThrow(() ->
                new NoSuchElementException("L'ami " + ami + " n'existe pas"));
        u.getAmis().remove(f);

        userRepository.save(u);
    }
}

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

    public User addAmis(Long id, Long amiId)  {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur ' " + id + " n'existe pas"));
        User userFriend = userRepository.findById(amiId).orElseThrow(() ->
                new NoSuchElementException("L'ami ' " + amiId + " n'existe pas"));
        user.getAmis().add(userFriend);

        userRepository.save(user);
        return user;
    }

    public User updateAmi(Long utilisateur, Long ancienAmi, Long nouvelAmi){
        User friend = userRepository.findById(utilisateur).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + utilisateur + " n'existe pas"));
        User oldFriend = userRepository.findById(ancienAmi).orElseThrow(() ->
                new NoSuchElementException("L'ancien ami " + ancienAmi + " n'existe pas"));
        User newFriend = userRepository.findById(nouvelAmi).orElseThrow(() ->
                new NoSuchElementException("Le nouvel ami " + nouvelAmi + " n'existe pas"));

        friend.getAmis().remove(oldFriend);
        friend.getAmis().add(newFriend);
        userRepository.save(friend);

        return friend;
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

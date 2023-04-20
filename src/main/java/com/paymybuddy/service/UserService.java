package com.paymybuddy.service;

import com.paymybuddy.entity.User;
import com.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser (User u){
        return userRepository.save(u);
    }

    public User listUser(Long id)  {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("L'utilisateur " + id + " n'existe pas"));
    }

    public User updateUser(User u){

        return userRepository.save(u);
    }

    public void deleteUser(Long id){

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("L'utilisateur " + id + " n'existe pas");
        }
    }

    public void createUser(User user) {
        User thisUser = userRepository.getUserByUsername(user.getName());
        if (thisUser == null) {
            userRepository.save(user);
        }
    }

}

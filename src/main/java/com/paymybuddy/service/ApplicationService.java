package com.paymybuddy.service;

import com.paymybuddy.repository.ApplicationReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationReposiroty applicationReposiroty;

}

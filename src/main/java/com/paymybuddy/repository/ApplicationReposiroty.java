package com.paymybuddy.repository;

import com.paymybuddy.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationReposiroty extends JpaRepository<Application, Long> {
}

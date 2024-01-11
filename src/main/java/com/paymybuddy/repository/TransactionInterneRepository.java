package com.paymybuddy.repository;

import com.paymybuddy.entity.TransactionInterne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/TransactionExterne")
public interface TransactionInterneRepository extends JpaRepository<TransactionInterne, Long> {
}

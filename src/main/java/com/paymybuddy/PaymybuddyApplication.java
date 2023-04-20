package com.paymybuddy;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.paymybuddy.entity.User;
import com.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PaymybuddyApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@PostConstruct
	public void init(){
		User user = new User("admin",passwordEncoder.encode("admin"),"",0.0);
		userService.createUser(user);
	}

}


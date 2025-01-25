package com.manu;

import com.manu.entities.UserEntity;
import com.manu.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MovieReservationApplication {

	@Autowired private UserRepository userRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	@PostConstruct
	void devUser(){
		var dev = userRepository
				.save(
						new UserEntity(
								0,
								"admin",
								passwordEncoder.encode("hello"),
								"dev@gmail.com")
				);
		System.out.println("Dev User: " + dev.getEmail() + " is available to use");
	}


	public static void main(String[] args) {
		SpringApplication.run(MovieReservationApplication.class, args);
		System.out.println("Movie Reservation App Started Successfully");
	}

}

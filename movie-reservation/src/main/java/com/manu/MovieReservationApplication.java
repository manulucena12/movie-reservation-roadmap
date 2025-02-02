package com.manu;

import com.manu.entities.UserEntity;
import com.manu.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MovieReservationApplication {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Value("${dev.user.email}")
  private String email;

  @Value("${dev.user.password}")
  private String password;

  @PostConstruct
  void devUser() {
    userRepository.save(new UserEntity(500, "admin", passwordEncoder.encode(password), email));
    userRepository.save(
        new UserEntity(0, "user", passwordEncoder.encode(password), "user@gmail.com"));
    System.out.println("Dev user is available to use");
  }

  public static void main(String[] args) {
    SpringApplication.run(MovieReservationApplication.class, args);
    System.out.println("Movie Reservation App Started Successfully");
  }
}

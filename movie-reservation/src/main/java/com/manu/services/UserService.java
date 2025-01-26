package com.manu.services;

import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public HttpCustomResponse<Object> getAllUsers() {
    try {
      return new HttpCustomResponse<>(200, userRepository.findAll(), null);
    } catch (Exception e) {
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}

package com.manu.services;

import com.manu.dtos.requests.AuthRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.entities.UserEntity;
import com.manu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRepository userRepository;

  public HttpCustomResponse<Object> login(AuthRequest body) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
      var userCredentials = body.getEmail() + ":" + body.getPassword();
      var userToken =
          "Basic "
              + java.util.Base64.getEncoder()
                  .encodeToString(
                      userCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      return new HttpCustomResponse<>(200, userToken, null);
    } catch (Exception e) {
      if (e instanceof BadCredentialsException) {
        return new HttpCustomResponse<>(400, null, "Bad credentials");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> register(AuthRequest body) {
    try {
      var user = userRepository.findByEmail(body.getEmail());
      if (user.isPresent()) {
        return new HttpCustomResponse<>(400, null, "Email already taken");
      }
      userRepository.save(
          new UserEntity(0, "user", passwordEncoder.encode(body.getPassword()), body.getEmail()));
      var userCredentials = body.getEmail() + ":" + body.getPassword();
      var userToken =
          "Basic "
              + java.util.Base64.getEncoder()
                  .encodeToString(
                      userCredentials.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      return new HttpCustomResponse<>(200, userToken, null);
    } catch (Exception e) {
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}

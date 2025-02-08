package com.manu.services;

import com.manu.dtos.requests.BalanceRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.UserRepository;
import com.manu.security.resources.UserChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  @Autowired UserRepository userRepository;

  public HttpCustomResponse<Object> updateCash(BalanceRequest body, String userEmail) {
    try {
      if (!UserChecker.check(userEmail)) {
        return new HttpCustomResponse<>(
            403, null, "You cannot update a cash of a user that is not you");
      }
      var user =
          userRepository
              .findByEmail(userEmail)
              .orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      user.setBalance(user.getBalance() + body.getBalance());
      userRepository.save(user);
      return new HttpCustomResponse<>(
          200, null, "Your balance has been updated to " + user.getBalance() + " $");
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "User not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}

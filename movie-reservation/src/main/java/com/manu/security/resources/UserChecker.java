package com.manu.security.resources;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserChecker {

  public static boolean check(String emailToCheck) {
    var loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
    return loggedUser.equals(emailToCheck);
  }
}

package com.manu.security.resources;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthorityGranter {

  public static Collection<? extends GrantedAuthority> giveAuthorities(String role) {
    return switch (role) {
      case "user" -> List.of(
          new SimpleGrantedAuthority("buy"), new SimpleGrantedAuthority("cancel"));
      case "admin" -> List.of(new SimpleGrantedAuthority("manager"));
      default -> List.of();
    };
  }
}

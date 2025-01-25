package com.manu.security.resources;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class AuthorityGranter {

    public static Collection<? extends GrantedAuthority> giveAuthorities(String role){
        return switch (role){
            case "user" -> List.of(
                    new SimpleGrantedAuthority("buy"),
                    new SimpleGrantedAuthority("cancel")
            );
            case "admin" -> List.of(
                    new SimpleGrantedAuthority("show"),
                    new SimpleGrantedAuthority("movie")
            );
            default -> List.of();
        };
    }

}

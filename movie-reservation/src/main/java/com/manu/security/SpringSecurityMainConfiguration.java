package com.manu.security;

import com.manu.repositories.UserRepository;
import com.manu.security.resources.JpaUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// This method is not final yet, it is mandatory to add a specific auth method, for the moment is
// HttpBasic only
public class SpringSecurityMainConfiguration {

  @Autowired private UserRepository userRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .httpBasic(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorizationManagerRequestMatcherRegistry -> {
              authorizationManagerRequestMatcherRegistry
                  .requestMatchers("/users")
                  .hasAuthority("manager")
                  .requestMatchers("/rooms/**")
                  .hasAuthority("manager")
                  .requestMatchers(
                      "/swagger-ui/**",
                      "/configurations/ui",
                      "/configurations/security",
                      "/swagger-ui.html",
                      "/swagger-resources/**",
                      "/swagger-resources",
                      "/v3/api-docs",
                      "/v3/api-docs/**")
                  .permitAll()
                  .anyRequest()
                  .denyAll();
            })
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return email -> {
      var storedUser =
          userRepository
              .findByEmail(email)
              .orElseThrow(() -> new BadCredentialsException("Wrong Credentials"));
      return new JpaUserDetails(storedUser);
    };
  }
}

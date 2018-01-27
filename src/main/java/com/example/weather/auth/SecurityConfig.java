package com.example.weather.auth;

import com.example.weather.auth.entity.Account;
import com.example.weather.auth.repository.AccountRepository;
import com.example.weather.yahoo.forecast.YahooWeatherForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.ArrayList;

/**
 * Based on http://www.devglan.com/spring-security/spring-boot-security-rest-basic-authentication
 * */
@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(YahooWeatherForecastService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {

        if (accountRepository.count() == 0) {
            // if no user accounts are present, create default one
            accountRepository.save(new Account(null, "admin", "admin", "ADMIN", new ArrayList<>()));
        }

        accountRepository.findAll().forEach(
          x -> {
              try {
                  auth.inMemoryAuthentication().withUser(x.getUsername()).password(x.getPassword()).roles(x.getRole());
              } catch (Exception e) {
                  LOGGER.error(e.toString());
              }
          }
        );
    }
}
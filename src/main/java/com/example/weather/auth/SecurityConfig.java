package com.example.weather.auth;

import com.example.weather.auth.entity.Account;
import com.example.weather.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    UserDetailsService users() {
        if (accountRepository.count() == 0) {
            // if no user accounts are present, create default one
            accountRepository.save(new Account(null, "admin", "admin", "ADMIN"));
        }

        return new InMemoryUserDetailsManager(accountRepository.findAll().stream().map(
                acc -> User.withUsername(acc.getUsername()).roles(acc.getPassword()).password(acc.getPassword()).build()
        ).collect(Collectors.toList()));
    }


    //https://stackoverflow.com/questions/21128058/invalid-csrf-token-null-was-found-on-the-request-parameter-csrf-or-header
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
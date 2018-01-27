package com.example.weather.auth;

import com.example.weather.auth.entity.Account;
import com.example.weather.auth.repository.AccountRepository;
import com.example.weather.yahoo.forecast.YahooWeatherForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.ArrayList;

/**
 * Based on https://stackoverflow.com/a/46123450
 * */
@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(YahooWeatherForecastService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;


    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if (accountRepository.count() == 0) {
            LOGGER.debug("No user accounts present in the database. Creating default credentials: admin/admin");
            // if no user accounts are present, create default one
            accountRepository.save(new Account(null, "admin", "admin", "ADMIN", new ArrayList<>()));
        }

        auth.userDetailsService(userDetailsService);
    }

}
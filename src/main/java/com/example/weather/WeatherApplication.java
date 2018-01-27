package com.example.weather;

import com.example.weather.auth.entity.Account;
import com.example.weather.auth.repository.AccountRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@SpringBootApplication
public class WeatherApplication /*extends SpringBootServletInitializer*/ {

/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WeatherApplication.class);
	}
*/

/*    @Bean
    ApplicationRunner run(AccountRepository accRep) {
        return args ->
                Stream.of("admin")
                        .forEach(
                                x -> accRep.save(new Account(null, x, "admin", "ADMIN"))
                        );
    }*/

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WeatherApplication.class, args);

		//accountRepository.save(new Account("rbaxter", "password"));
	}

}

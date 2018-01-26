package com.example.weather;

import com.example.weather.location.Location;
import com.example.weather.location.LocationProviderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);

		for(Location c : LocationProviderService.getInstance().getLocations()){
            System.out.println(c.toString());
        }
	}



}

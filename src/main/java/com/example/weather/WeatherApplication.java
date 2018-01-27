package com.example.weather;

import com.example.weather.yahoo.Query;
import com.example.weather.yahoo.forecast.YahooWeatherForecastService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApplication /*extends SpringBootServletInitializer*/ {

/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WeatherApplication.class);
	}
*/

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WeatherApplication.class, args);

        Query asd = YahooWeatherForecastService.getInstance().makeQuery("ls");
        System.out.println(asd.toString());

		//accountRepository.save(new Account("rbaxter", "password"));
	}

}

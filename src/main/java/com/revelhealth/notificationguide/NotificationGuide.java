package com.revelhealth.notificationguide;

import com.revelhealth.notificationguide.service.ForecastService;

import java.util.List;

import com.revelhealth.notificationguide.model.Forecast;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationGuide {

	public static void main(String[] args) {
		SpringApplication.run(NotificationGuide.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String currentDirectory = System.getProperty("user.dir");
			ForecastService service = new ForecastService();
			List<Forecast> forecasts = service.getListOfForecasts(currentDirectory + "/src/test/5dayforecast.json");
			for (Forecast forecast : forecasts) {
				System.out.println(forecast.toString());
			}
		};
	}

}

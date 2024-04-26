package com.sap.cityservice;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cityservice.model.CityModel;
import com.sap.cityservice.service.CityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class CityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CityService cityService){
		return args -> {

			long cityCount = cityService.count();
			if (cityCount >= 215) {
				System.out.println("Cities already present in the database. Skipping save operation.");
				return;
			}

			// read JSON and load json OR JSON parsing
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<CityModel>> typeReference = new TypeReference<List<CityModel>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/city.json");
			try {
				List<CityModel> cities = mapper.readValue(inputStream,typeReference);
				cityService.save(cities);
				System.out.println("Cities Saved!");
			} catch (IOException e){
				System.out.println("Unable to save cities: " + e.getMessage());
			}
		};
	}
}
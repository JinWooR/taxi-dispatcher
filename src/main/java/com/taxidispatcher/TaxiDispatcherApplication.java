package com.taxidispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TaxiDispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiDispatcherApplication.class, args);
	}

}

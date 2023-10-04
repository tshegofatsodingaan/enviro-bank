package com.enviro.envirobankingapp;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
//@EnableScheduling
public class EnviroApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

//	Logger logger = LoggerFactory.getLogger(EnviroApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EnviroApplication.class, args);
	}

//	@Scheduled(fixedRate = 2000L)
//	public void job(){
//		logger.info("Job Current Time "+new Date());
//	}

}

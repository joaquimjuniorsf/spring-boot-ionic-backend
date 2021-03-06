package com.joaquim.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.joaquim.cursomc.service.DBService;
import com.joaquim.cursomc.service.EmailService;
import com.joaquim.cursomc.service.MockEmailService;

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private DBService dbservice;
	
	@Bean
	public boolean instantiateDataBase() {
		dbservice.instantiateDateBase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}

package br.com.thiago.retry.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.thiago.retry.service.DbService;
import br.com.thiago.retry.service.EmailService;
import br.com.thiago.retry.service.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	@Autowired
	private DbService dbService;

	@Bean
	public Boolean instantiateDatabase() throws ParseException {
		dbService.instantiateDataBase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}

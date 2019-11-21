package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;

	/*
	 * Anotacija za oznacavanje asinhronog zadatka
	 * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
	 */
	@Async
	public void sendNotificaitionAsync(UserDTO userDTO) throws MailException, InterruptedException {

		System.out.println("Slanje emaila..." + userDTO.getEmail());

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userDTO.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda registracije");
		mail.setText("Postovani,\n\nmolimo Vas da potvrdite vasu registraciju klikom na sledeci link: http://localhost:3000 .");
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
}

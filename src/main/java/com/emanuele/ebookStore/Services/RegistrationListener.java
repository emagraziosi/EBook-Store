package com.emanuele.ebookStore.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.emanuele.ebookStore.config.OnRegistrationCompleteEvent;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.entity.VerificationToken;
import com.emanuele.ebookStore.model.repositories.VerificationTokenRepository;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;
	
	@Autowired
	private JavaMailSender mailSender;

	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, user);
		verificationTokenRepo.save(verificationToken);
		
		String address = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/regitrationConfirm?token=" + token;
		String message = "Clicca sul link e verifica il tuo account!";
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(address);
		mail.setFrom("testemanuele@yahoo.com");
		mail.setSubject(subject);
		mail.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
		mailSender.send(mail);
	}
}

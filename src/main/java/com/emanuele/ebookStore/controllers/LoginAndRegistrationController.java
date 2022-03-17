package com.emanuele.ebookStore.controllers;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emanuele.ebookStore.config.OnRegistrationCompleteEvent;
import com.emanuele.ebookStore.model.entity.RegistrationContext;
import com.emanuele.ebookStore.model.entity.Response;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.entity.UserInfo;
import com.emanuele.ebookStore.model.entity.VerificationToken;
import com.emanuele.ebookStore.model.repositories.UserInfoRepository;
import com.emanuele.ebookStore.model.repositories.UserRepository;
import com.emanuele.ebookStore.model.repositories.VerificationTokenRepository;

@RestController
public class LoginAndRegistrationController {
	
	private Logger logger = LogManager.getLogger(RequestController.class);
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;

	@PostMapping("/register")
	public Response<User> registration(@RequestBody RegistrationContext registrationContext, 
			HttpServletRequest request) {
		User user = registrationContext.getUser();
		UserInfo userInfo = registrationContext.getUserInfo();
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(user.getEmail());
		if(!matcher.matches()) {
			return new Response<User>(400, "Invalid e-mail", null);
		}
		if(userRepository.findByEmail(user.getEmail()) != null) {
			return new Response<User>(400, "User already exists", null);
		}
		user.setIsAdmin(false);
		user.setLogType("standard");
		userRepository.save(user);
		logger.info(user.getId());
		userInfo.setId(user.getId());
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		userInfoRepository.save(userInfo);
		String appUrl = request.getContextPath();
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, request.getLocale(), user));
		return new Response<User>(200, "Registration started, sent verify link", user);
	}
	
	@GetMapping("/regitrationConfirm")
	public Response<User> registrationConfirm(@RequestParam("token") String token, HttpServletRequest request,
			HttpServletResponse response) {
		VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
		if(verificationToken == null) {
			return new Response<User>(401, "Invalid token", null);
		}
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return new Response<User>(401, "Expired token", null);
		}
		user.setEnabled(true);
		userRepository.save(user);
		return new Response<User>(200, "Registration confirmed", user);
	}
	
	@GetMapping("/login")
	public String login() {
		return "Salve, logga! ";
	}
	
	@GetMapping("/login-success")
	public String loginSuc() {
		return "Salve! Sei dentro!";
	}
	
	@GetMapping("/login-failure")
	public String loginFail() {
		return "Login fallito!";
	}
	
	@GetMapping("/logout-success")
	public String logoutSuc() {
		return "Bella, sei fuori!";
	}
}

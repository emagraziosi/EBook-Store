package com.emanuele.ebookStore.Services;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.entity.UserPrincipal;
import com.emanuele.ebookStore.model.repositories.UserInfoRepository;
import com.emanuele.ebookStore.model.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	private Logger logger = LogManager.getLogger(MyUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserInfoRepository userInfoRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		logger.info("Cerco email! " + email);
		User user = userRepo.findByEmail(email);
		logger.info("L'agg trovat! " + user);
		Date date = new Date(System.currentTimeMillis());
		if(user==null) {
			logger.info("NEW!");
			throw new UsernameNotFoundException("User 404");
		}
		if(user.getEnabled() == false) {
			throw new UsernameNotFoundException("User not enabled");
		}
		user.setLastLogin(date);
		userRepo.save(user);
		logger.info("Non new!");
		return new UserPrincipal(user, userInfoRepo);
	}
}


package com.emanuele.ebookStore.model.entity;

import java.util.Collection;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.emanuele.ebookStore.model.repositories.UserInfoRepository;

public class UserPrincipal implements UserDetails{

	private static final long serialVersionUID = 1L;

	private User user;
	
	private Logger logger = LogManager.getLogger(UserPrincipal.class);
	
	private UserInfoRepository userInfoRepo;
	
	public UserPrincipal(User user, UserInfoRepository userInfoRepo) {
		super();
		this.user = user;
		this.userInfoRepo = userInfoRepo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		logger.info("Vado cercando la passeword!");
		return userInfoRepo.findById(this.user.getId()).get().getPassword();
	}
	
	public User getUser() {
		return this.user;
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(this.user.getEnabled()) {
			return true;
		} else {
			return false;
		}
		
	}

	
}

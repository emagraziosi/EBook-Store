package com.emanuele.ebookStore.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.entity.UserPrincipal;
import com.emanuele.ebookStore.model.repositories.UserRepository;

@Service
public class UserGetter {

	@Autowired
	private UserRepository userRepository;
	
	public User getCurrentUser() {
		User currentUser = new User();
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() 
				instanceof DefaultOidcUser) {
			DefaultOidcUser oiUser = (DefaultOidcUser) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			currentUser = userRepository.findByEmail(oiUser.getAttribute("email"));
		} else if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() 
				instanceof UserPrincipal) {
			currentUser = ((UserPrincipal) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal()).getUser();
		}
		return currentUser;
	}
}

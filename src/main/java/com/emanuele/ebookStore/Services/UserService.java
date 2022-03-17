package com.emanuele.ebookStore.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public void processOAuthPostLogin(String email) {
        User existUser = repo.findByEmail(email);
         
        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);      
            newUser.setLogType("Google");
            repo.save(newUser);        
        }
         
    }
}

package com.emanuele.ebookStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emanuele.ebookStore.Services.UserGetter;
import com.emanuele.ebookStore.model.entity.Response;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.entity.UserInfo;
import com.emanuele.ebookStore.model.repositories.UserInfoRepository;

@RestController
public class UserInfoController {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserGetter userGetter;
	
	@PostMapping("/userinfo")
	public Response<UserInfo> userInfoSet(@RequestBody UserInfo userInfo){
		User currentUser = userGetter.getCurrentUser();
		UserInfo currentUserInfo = userInfoRepository.getById(currentUser.getId());
		userInfo.setId(currentUserInfo.getId());
		userInfo.setPassword(currentUserInfo.getPassword());
		userInfoRepository.save(userInfo);
		return new Response<UserInfo>(200, "Info added", userInfo);
	}
}

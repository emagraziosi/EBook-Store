package com.emanuele.ebookStore.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emanuele.ebookStore.model.entity.BooksInStore;
import com.emanuele.ebookStore.model.entity.Response;
import com.emanuele.ebookStore.model.entity.Transaction;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.entity.UserInfo;
import com.emanuele.ebookStore.model.entity.UserPrincipal;
import com.emanuele.ebookStore.model.repositories.BooksInStoreRepository;
import com.emanuele.ebookStore.model.repositories.TransactionRepository;
import com.emanuele.ebookStore.model.repositories.UserInfoRepository;
import com.emanuele.ebookStore.model.repositories.UserRepository;

@RestController
public class RequestController {

	private Logger logger = LogManager.getLogger(RequestController.class);
	
	@Autowired
	private BooksInStoreRepository bookInStoreRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private UserInfoRepository userInfoRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/addBook")
	public Response<BooksInStore> addBookInStore(@RequestBody BooksInStore book) {
		if(((UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUser().getIsAdmin()) 
		{
			logger.info(book.toString());
			bookInStoreRepo.save(book);
			return new Response<BooksInStore>(200, "Added", book);
		} else {
			return new Response<BooksInStore>(403, "Access denied, must be an admin", null);
		}
		
	}
	
	@PostMapping("/addUser")
	public Response<User> addUser(@RequestBody User user) {
		if(((UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUser().getIsAdmin()) {
			logger.info(user.toString());
			userRepo.save(user);
			return new Response<User>(200, "Added", user);
		} else {
			return new Response<User>(403, "Access denied, must be an admin", null);
		}
		
	}
	
	@PostMapping("/addUserInfo")
	public Response<UserInfo> addUserInfo(@RequestBody UserInfo userInfo) {
		logger.info("Trying to add UserInfos, let's check...");
		if(((UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUser().getIsAdmin()) {
			if(userRepo.existsById(userInfo.getId())) {
				logger.info("FOUND IT!" + userRepo.getById(userInfo.getId()).toString());
				userInfo.setPassword(encoder.encode(userInfo.getPassword()));
				userInfoRepo.save(userInfo);
				return new Response<UserInfo>(200, "Added", userInfo);
			}
			else {
				logger.info("User not found!");
				return new Response<UserInfo>(404, "User not found", null);
			}
		} else {
			return new Response<UserInfo>(403, "Access denied, must be an admin", null);
		}
	}
	
	@PostMapping("/addTransaction")
	public Response<Transaction> addTransaction(@RequestBody Transaction transaction) {
		logger.info("Trying to add Transaction, let's check...");
		if(((UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUser().getIsAdmin()) {
			if(userRepo.existsById(transaction.getUserT().getId()) &&
					bookInStoreRepo.existsById(transaction.getBookT().getId())) {
				logger.info("FOUND IT!");
				transactionRepo.save(transaction);
				return new Response<Transaction>(200, "Added", transaction);
			}
			else {
				logger.info("User (or Book) not found!");
				return new Response<Transaction>(404, "User (or book) not found", null);
			}
		} else {
			return new Response<Transaction>(403, "Access denied, must be an admin", null);
		}
	} 
}

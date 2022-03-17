package com.emanuele.ebookStore.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.emanuele.ebookStore.Services.UserGetter;
import com.emanuele.ebookStore.model.entity.BooksInStore;
import com.emanuele.ebookStore.model.entity.Response;
import com.emanuele.ebookStore.model.entity.Transaction;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.repositories.BooksInStoreRepository;
import com.emanuele.ebookStore.model.repositories.TransactionRepository;
import com.emanuele.ebookStore.model.repositories.UserRepository;

@RestController
public class HomeController {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	BooksInStoreRepository booksInStoreRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private UserGetter userGetter;
	
	@GetMapping("/home")
	public ModelAndView home(HttpServletResponse response) {
		if(SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			boolean logged = true;
			User currentUser = userGetter.getCurrentUser();
			List<BooksInStore> books = new ArrayList<BooksInStore>();
			for (Transaction i : transactionRepository.findByUserT(currentUser)) {
				books.add(i.getBookT());
			}
			Response<List<BooksInStore>> risposta = new Response<List<BooksInStore>>(response.getStatus(), "", books);
			ModelAndView mv = new ModelAndView("home.jsp");
			mv.addObject("risposta", risposta);
			mv.addObject("logged", logged);
			mv.addObject("currentUser", currentUser);
			return mv;
		} else {
			boolean logged = false;
			Response<List<BooksInStore>> risposta = new Response<List<BooksInStore>>(response.getStatus(), "", booksInStoreRepository.findTop5ByOrderByNumeroAcquistiDesc());
			ModelAndView mv = new ModelAndView("home.jsp");
			mv.addObject("risposta", risposta);
			mv.addObject("logged", logged);
			return mv;
		}
	}
}
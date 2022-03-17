package com.emanuele.ebookStore.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.emanuele.ebookStore.Services.UserGetter;
import com.emanuele.ebookStore.model.entity.BooksInStore;
import com.emanuele.ebookStore.model.entity.Response;
import com.emanuele.ebookStore.model.entity.Transaction;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.repositories.BooksInStoreRepository;
import com.emanuele.ebookStore.model.repositories.TransactionRepository;

@RestController
public class ReaderController {
	
	@Autowired
	UserGetter userGetter;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private BooksInStoreRepository booksInStoreRepo;

	@GetMapping("/read")
	public Response<List<BooksInStore>> read(){
		User currentUser = userGetter.getCurrentUser();
		List<BooksInStore> books = new ArrayList<BooksInStore>();
		for (Transaction i : transactionRepository.findByUserT(currentUser)) {
			books.add(i.getBookT());
		}
		return new Response<List<BooksInStore>>(200, "", books);
	}
	
	@GetMapping("/read/{bid}")
	public Response<BooksInStore> readBook(@PathVariable("bid")long bookId){
		User currentUser = userGetter.getCurrentUser();
		BooksInStore book = booksInStoreRepo.findById(bookId).orElse(null);
		for (Transaction i : transactionRepository.findByUserT(currentUser)) {
			if(i.getBookT() == book) {
				return new Response<BooksInStore>(200, "", book);
			}
		}
		return new Response<BooksInStore>(401, "You must buy the book", null);
	}
}

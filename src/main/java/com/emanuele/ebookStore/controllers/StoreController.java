package com.emanuele.ebookStore.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.emanuele.ebookStore.model.entity.BooksInStore;
import com.emanuele.ebookStore.model.entity.Response;
import com.emanuele.ebookStore.model.repositories.BooksInStoreRepository;

@RestController
public class StoreController {

	private Logger logger = LogManager.getLogger(StoreController.class);
	
	@Autowired
	private BooksInStoreRepository bookInStoreRepo;
	
	@GetMapping("/store")
	public Response<List<BooksInStore>> getAllBooks(){
		logger.info("Richiesti tutti i libri");
		return new Response<List<BooksInStore>>(200, "", bookInStoreRepo.findAll());
	}
	
	@GetMapping("/store/{bid}")
	public Response<BooksInStore> getBook(@PathVariable("bid")long bookId) {
		logger.info("Richiesto: " + bookId);
		logger.info("ById: " + bookInStoreRepo.findById(bookId).orElse(null));
		return new Response<BooksInStore>(200, "", bookInStoreRepo.findById(bookId).orElse(null));
	}
	
	@GetMapping("/store/findByName")
	public Response<List<BooksInStore>> getBooksByName(String titolo){
		logger.info("Richiesto: " + titolo);
		logger.info("ByNameContaining: " + bookInStoreRepo.findByTitoloContaining(titolo));
		return new Response<List<BooksInStore>>(200, "", bookInStoreRepo.findByTitoloContaining(titolo));
	}
	
	@GetMapping("/store/findByAuthor")
	public Response<List<BooksInStore>> getBooksByAutore(String autore){
		logger.info("Richiesto: " + autore);
		logger.info("ByAutoreContaining: " + bookInStoreRepo.findByAutoreContaining(autore));
		return new Response<List<BooksInStore>>(200, "", bookInStoreRepo.findByAutoreContaining(autore));
	}
	
	@GetMapping("/store/findByGenre")
	public Response<List<BooksInStore>> getBooksByGenere(String genere){
		logger.info("Richiesto: " + genere);
		logger.info("ByGenere: " + bookInStoreRepo.findByGenere(genere));
		return new Response<List<BooksInStore>>(200, "", bookInStoreRepo.findByGenere(genere));
	}
}

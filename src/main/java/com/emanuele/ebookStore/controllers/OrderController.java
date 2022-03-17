package com.emanuele.ebookStore.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emanuele.ebookStore.Services.PaymentService;
import com.emanuele.ebookStore.Services.UserGetter;
import com.emanuele.ebookStore.model.entity.BooksInStore;
import com.emanuele.ebookStore.model.entity.PayPalOrder;
import com.emanuele.ebookStore.model.entity.Transaction;
import com.emanuele.ebookStore.model.entity.User;
import com.emanuele.ebookStore.model.repositories.BooksInStoreRepository;
import com.emanuele.ebookStore.model.repositories.TransactionRepository;

@RestController
public class OrderController {
	
	private String orderId;

	private final PaymentService paymentService;
	
	@Autowired
	private BooksInStoreRepository booksInStoreRepository;
	
	@Autowired
	private UserGetter userGetter;
	
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public OrderController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("/buy/{tid}/capture")
	public void captureOrder(@PathVariable("tid") long transactionId, @RequestParam String token,
			HttpServletResponse response) throws IOException{
		orderId = token;
		paymentService.captureOrder(orderId);
		Transaction transaction = transactionRepository.getById(transactionId);
		transaction.setStatus("Success");
		Date date = new Date(System.currentTimeMillis());
		transaction.setDataCompletamento(date);
		transactionRepository.save(transaction);
		long bookId = transaction.getBookT().getId();
		try {
			response.sendRedirect("/store/" + bookId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private URI buildReturnUrl(HttpServletRequest request, long transactionId) {
		try {
			URI requestUri = URI.create(request.getRequestURL().toString());
			return new URI(requestUri.getScheme(), requestUri.getUserInfo(), requestUri.getHost(),
					requestUri.getPort(), "/buy/" + transactionId + "/capture", null, null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@GetMapping("/buy/{bid}")
	public void placeOrder(@PathVariable("bid") long bookId, HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		User currentUser = userGetter.getCurrentUser();
		List<Transaction> userBooks =  transactionRepository.findByUserT(currentUser);
		for(Transaction i : userBooks) {
			if(i.getBookT().getId() == bookId) {
				if(i.getStatus() == "Success") {
					response.sendRedirect("/store/" + bookId);
				}
			}
		}
		Transaction transaction = new Transaction();
		transaction.setBookT(booksInStoreRepository.findById(bookId).orElse(null));
		transaction.setUserT(currentUser);
		transaction.setMetodoPagamento("PayPal");
		transaction.setStatus("Started");
		BooksInStore book = booksInStoreRepository.getById(transaction.getBookT().getId());
		book.setNumeroAcquisti(book.getNumeroAcquisti() + 1);
		transactionRepository.save(transaction);
		final URI returnUrl = buildReturnUrl(request, transaction.getId());
		double price = booksInStoreRepository.findById(bookId).get().getPrezzo();
		PayPalOrder createdOrder = paymentService.createOrder(price, returnUrl, transaction);
		try {
			response.sendRedirect(createdOrder.getApprovalLink().toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
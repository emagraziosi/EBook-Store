package com.emanuele.ebookStore.Services;

import java.net.URI;

import com.emanuele.ebookStore.model.entity.PayPalOrder;
import com.emanuele.ebookStore.model.entity.Transaction;

public interface PaymentService {
	PayPalOrder createOrder(Double totalAmount, URI returnUrl, Transaction transaction);
	public void captureOrder(String orderId);
}

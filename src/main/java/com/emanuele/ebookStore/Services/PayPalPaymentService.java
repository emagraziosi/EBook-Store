package com.emanuele.ebookStore.Services;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.emanuele.ebookStore.model.entity.PayPalOrder;
import com.emanuele.ebookStore.model.entity.Transaction;
import com.emanuele.ebookStore.model.repositories.TransactionRepository;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

@Service
public class PayPalPaymentService implements PaymentService{
	
	private Logger logger = LogManager.getLogger(PayPalPaymentService.class);
	
	private final String APPROVE_LINK_REL = "approve";

	private final PayPalHttpClient payPalHttpClient;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public PayPalPaymentService(@Value("${paypal.client.id}") String clientId,
								@Value("${paypal.client.secret}") String clientSecret) {
		payPalHttpClient = new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
	}

	@Override
	public PayPalOrder createOrder(Double totalAmount, URI returnUrl, Transaction transaction){
		final OrderRequest orderRequest = createOrderRequest(totalAmount, returnUrl);
		final OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
		transaction.setStatus("Working");
		transactionRepository.save(transaction);
		try {
			final HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
			final Order order = orderHttpResponse.result();
			LinkDescription approveUri = extractApprovalLink(order);
			return new PayPalOrder(order.id(), URI.create(approveUri.href()));
		} catch (IOException e) {
			transaction.setStatus("Failed");
			transactionRepository.save(transaction);
			throw new RuntimeException(e);
		}
	}
	
	private OrderRequest createOrderRequest(Double totalAmount, URI returnUrl) {
		final OrderRequest orderRequest = new OrderRequest();
		setCheckoutIntent(orderRequest);
		setPurchaseUnits(totalAmount, orderRequest);
		setApplicationContext(returnUrl, orderRequest);
		return orderRequest;
	}
	
	private void setCheckoutIntent(OrderRequest orderRequest) {
		orderRequest.checkoutPaymentIntent("CAPTURE");
	}
	
	private OrderRequest setApplicationContext(URI returnUrl, OrderRequest orderRequest) {
		return orderRequest.applicationContext(new ApplicationContext().returnUrl(returnUrl.toString()));
	}
	
	private void setPurchaseUnits(Double totalAmount, OrderRequest orderRequest) {
	    final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
	            .amountWithBreakdown(new AmountWithBreakdown().currencyCode("EUR").value(totalAmount.toString()));
	    orderRequest.purchaseUnits(Arrays.asList(purchaseUnitRequest));
	}
	
	@Override
	public void captureOrder(String orderId){
	    final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
	    try {
		    final HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
		    logger.info("Order Capture Status: {}",httpResponse.result().status());
	    } catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	private LinkDescription extractApprovalLink(Order order) {
	    LinkDescription approveUri = order.links().stream()
	            .filter(link -> APPROVE_LINK_REL.equals(link.rel()))
	            .findFirst()
	            .orElseThrow(NoSuchElementException::new);
	    return approveUri;
	}
	
	
}

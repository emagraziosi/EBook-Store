package com.emanuele.ebookStore.model.entity;

import java.net.URI;


public class PayPalOrder {
	
	private final String orderId;
	private final URI approvalLink;
	
	public PayPalOrder(String id, URI create) {
		this.orderId = id;
		this.approvalLink = create;
	}

	public String getOrderId() {
		return orderId;
	}

	public URI getApprovalLink() {
		return approvalLink;
	}
	
	
}

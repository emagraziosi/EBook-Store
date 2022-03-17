package com.emanuele.ebookStore.model.entity;

public class Response<T> {

	private int status;
	private String message;
	private T object;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	
	public Response(int status, String message, T object) {
		super();
		this.status = status;
		this.message = message;
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", object=" + object + "]";
	}
}

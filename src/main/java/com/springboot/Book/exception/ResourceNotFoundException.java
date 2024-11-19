package com.springboot.Book.exception;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String msg;
	public ResourceNotFoundException(String msg) {
		super();
		this.msg=msg;
	}
	public String getMessage() {
		return msg;
	}

}

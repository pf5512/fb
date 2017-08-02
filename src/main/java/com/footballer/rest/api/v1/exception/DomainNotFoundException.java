package com.footballer.rest.api.v1.exception;

/**
 * Created by ian on 8/21/14.
 */
public class DomainNotFoundException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

    public DomainNotFoundException() {

    }

    public DomainNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

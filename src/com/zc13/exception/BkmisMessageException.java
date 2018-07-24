package com.zc13.exception;


public class BkmisMessageException extends Exception{
	/**
	 * Constructor with error message.
	 * 
	 * @param msg the error message associated with the exception
	 */
	public BkmisMessageException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor with error message and root cause.
	 * 
	 * @param msg the error message associated with the exception
	 * @param cause the root cause of the exception
	 */
	public BkmisMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

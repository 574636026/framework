package com.injedu.bookproduce.exception;

/**
 * 
 * transfer exception
 *
 * @author joy.zhou
 * @date 2016年8月11日
 * @version 1.0
 *
 */
public class TransferException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TransferException(String message) {
		super(message);
	}

	public TransferException(String message, Throwable cause) {
		super(message, cause);
	}
}

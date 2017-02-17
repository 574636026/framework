package com.injedu.supports.push.exception;

/**
 * 
 * 推送消息异常
 *
 * @author joy.zhou
 * @date 2015年11月2日
 * @version 1.0
 *
 */
public class PushException extends Throwable {

	private static final long serialVersionUID = -4165827177285138941L;

	public PushException(String message) {
		super(message);
	}

	public PushException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.injedu.core.exception;

import org.springframework.util.StringUtils;

import com.injedu.core.constant.ResConst;
import com.injedu.core.tools.MessageTools;

/**
 * 基础异常
 *
 * @author joy.zhou
 * @date 2015年4月25日
 * @version 1.0
 *
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 错误码 */
	private int code;

	/** 错误消息码 */
	private String message;

	/** 错误码对应的参数 */
	private Object[] args;

	/**
	 * 
	 * @param code
	 *            错误码
	 */
	public AppException(int code) {
		this.code = code;
	}

	/**
	 * 
	 * @param code
	 *            错误码
	 * @param message
	 *            信息
	 * @param args
	 *            信息参数
	 * @param cause
	 *            异常信息
	 */
	public AppException(int code, String message, Throwable cause, Object... args) {
		super(cause);
		this.code = code;
		this.message = message;
		this.args = args;
	}

	/**
	 * 
	 * @param code
	 *            错误码
	 * @param message
	 *            信息
	 * @param args
	 *            信息参数
	 */
	public AppException(int code, String message, Object... args) {
		this(code, message, null, args);
	}

	/**
	 * 
	 * @param message
	 *            信息
	 * @param args
	 *            信息参数
	 * @param cause
	 *            异常信息
	 */
	public AppException(String message, Throwable cause, Object... args) {
		this(ResConst.ResultCode.ERROR, message, cause, args);
	}

	/**
	 * 
	 * @param message
	 *            信息
	 * @param args
	 *            信息参数
	 */
	public AppException(String message, Object... args) {
		this(ResConst.ResultCode.ERROR, message, args);
	}

	@Override
	public String getMessage() {
		String message = null;
		if (!StringUtils.isEmpty(code)) {
			message = MessageTools.message(this.message, args);
		}
		if (message == null) {
			message = this.message;
		}
		return message;
	}

	public int getCode() {
		return code;
	}

}

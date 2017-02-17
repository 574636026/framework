package com.injedu.bookproduce.exception;

/**
 * 
 * writer exception
 *
 * @author joy.zhou
 * @date 2016年8月11日
 * @version 1.0
 *
 */
public class WriterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String source;

	public WriterException(String message) {
		super(message);
	}

	public WriterException(String message, String source, Throwable cause) {
		this("书本生成异常: 来源 - " + source + ",异常描述:" + message, cause);
	}

	public WriterException(String message, String source) {
		this(message, source, null);
	}

	public WriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}

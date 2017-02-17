package com.injedu.core.dto;

/**
 * 
 * 验证消息
 *
 * @author joy.zhou
 * @date 2015年12月14日
 * @version 1.0
 *
 */
public class ValidateMessageDto {
	/** 验证状态 */
	private Status status = Status.success;
	/** 消息 */
	private String msg;
	
	public ValidateMessageDto(){}

	public ValidateMessageDto(Status status) {
		this.status = status;
	}

	public ValidateMessageDto(Status status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/** 消息状态 */
	public enum Status {
		success, faild
	}

}

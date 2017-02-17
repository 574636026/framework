package com.injedu.core.dto.wrapper;

import java.io.Serializable;

/**
 * 
 * 响应消息体
 *
 * @author joy.zhou
 * @date 2015年11月12日
 * @version 1.0
 *
 */
public class ResultWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 响应结果码 */
	private Integer resultCode;
	/** 响应结果信息 */
	private String resultMessage;
	/** 响应结果 */
	private Object result;

	public ResultWrapper() {
	}

	/**
	 * 
	 * @param resultCode
	 * @param resultMessage
	 */
	public ResultWrapper(Integer resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	/**
	 * 
	 * @param resultCode
	 * @param resultMessage
	 * @param result
	 */
	public ResultWrapper(Integer resultCode, String resultMessage,
			Object result) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.result = result;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}

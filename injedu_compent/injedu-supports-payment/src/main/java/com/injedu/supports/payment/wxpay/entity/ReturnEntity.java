package com.injedu.supports.payment.wxpay.entity;

/**
 * 
 * 微信返回信息
 *
 * @author joy.zhou
 * @date 2016年1月8日
 * @version 1.0
 *
 */
public class ReturnEntity {

	/** 返回状态码 */
	private String return_code;
	/** 返回信息 */
	private String return_msg;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	@Override
	public String toString() {
		return "ReturnEntity [return_code=" + return_code + ", return_msg=" + return_msg + "]";
	}

}

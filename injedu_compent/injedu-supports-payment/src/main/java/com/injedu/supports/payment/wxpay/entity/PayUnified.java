package com.injedu.supports.payment.wxpay.entity;

/**
 *
 * 预支付订单返回信息
 * 
 * @author joy.zhou
 * @date 2016年2月2日
 * @version 1.0
 *
 */
public class PayUnified extends WxBaseEntity {

	private static final long serialVersionUID = -2026997038487793927L;
	/** 设备号 */
	private String deviceInfo;
	/** 公众账号 */
	private String appid;
	/** 商户号 */
	private String mchId;
	/** 随机字符串 */
	private String nonceStr;
	/** 签名 */
	private String sign;
	/** 业务结果 */
	private String resultCode;
	/** 错误代码 */
	private String errCode;
	/** 错误代码描述 */
	private String errCodeDes;
	/** 交易类型 */
	private String tradeType;
	/** 预支付交易会话标识 */
	private String prepayId;
	/** 二维码链接 */
	private String codeUrl;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

}

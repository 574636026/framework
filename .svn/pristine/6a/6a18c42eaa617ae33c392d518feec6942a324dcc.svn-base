package com.injedu.mvc.convert;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.injedu.core.constant.ResConst;
import com.injedu.core.dto.wrapper.ApiRequestWrapper;
import com.injedu.core.exception.AppException;
import com.injedu.utils.encrypt.DES;

/**
 * 
 * 消息加密处理器
 *
 * @author joy.zhou
 * @date 2016年1月25日
 * @version 1.0
 *
 */
public class EncryptMessageProvider {

	protected static final Logger logger = LoggerFactory.getLogger(EncryptMessageProvider.class);

	/** token */
	private String token;
	/** 加密key */
	private String encryptKey;

	public EncryptMessageProvider() {
	}

	public EncryptMessageProvider(String token, String encryptKey) {
		this.token = token;
		this.encryptKey = encryptKey;
	}

	/**
	 * 消息解密
	 * 
	 * @param message
	 *            消息字符串
	 * @return
	 */
	public String decryptMessage(String message) throws AppException {

		String decry = this.decrypt(message);
		logger.debug("the decrypt msg:" + decry);

		// 请求参数解密
		ApiRequestWrapper result = null;
		try {

			result = JSON.parseObject(decry, ApiRequestWrapper.class);

		} catch (Exception e) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_DECRYPT_ERROR);
		}

		if (!validateToken(result.getToken())) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_TOKEN_ERROR);
		}

		return decry;
	}

	/**
	 * 消息解密
	 * 
	 * @param message
	 *            消息字符串
	 * @param cls
	 *            解密后的对象类型
	 * @return
	 */
	public <T extends ApiRequestWrapper> T decryptMessage(String message, Class<T> cls) throws AppException {

		String decry = this.decrypt(message);
		logger.debug("the decrypt msg:" + decry);

		// 请求参数解密
		T result = null;
		try {
			result = JSON.parseObject(decry, cls);
		} catch (Exception e) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_DECRYPT_ERROR);
		}

		if (!validateToken(result.getToken())) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_TOKEN_ERROR);
		}

		return result;
	}

	protected String decrypt(String message) throws AppException {

		logger.debug("the encrypt msg:" + message);

		// 说明token不存在
		if (StringUtils.isBlank(message)) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_TOKEN_ERROR);
		}

		try {
			return DES.decryptDES(message, encryptKey);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_DECRYPT_ERROR);
		}

	}

	/**
	 * 消息加密
	 * 
	 * @param message
	 * @return
	 * @throws AppException
	 */
	public String encryptMessage(String message) throws AppException {

		try {
			return DES.encryptDES(message, encryptKey);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			throw new AppException(ResConst.ResultCode.ILLEGAL_REQUEST, ResConst.Message.API_ENCRYPT_ERROR);
		}

	}

	/**
	 * token 校验
	 * 
	 * @param token
	 * @return
	 */
	protected boolean validateToken(String token) {

		return token != null && this.token.equals(token);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

}

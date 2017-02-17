package com.injedu.mvc.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.injedu.core.dto.wrapper.ApiRequestWrapper;

/**
 * 
 * 加密参数转换
 *
 * @author joy.zhou
 * @date 2016年1月25日
 * @version 1.0
 *
 */
public class EncryptConvertor implements ConverterFactory<String, ApiRequestWrapper> {

	private EncryptMessageProvider encryptMessageTools;

	public EncryptConvertor() {
	}

	public EncryptConvertor(EncryptMessageProvider encryptMessageTools) {
		this.encryptMessageTools = encryptMessageTools;
	}

	@Override
	public <T extends ApiRequestWrapper> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToRequest<T>(targetType);
	}

	private class StringToRequest<T extends ApiRequestWrapper> implements Converter<String, T> {

		private final Class<T> reqType;

		public StringToRequest(Class<T> reqType) {
			this.reqType = reqType;
		}

		@Override
		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}

			return encryptMessageTools.decryptMessage(source, reqType);
		}
	}

	public void setEncryptMessageTools(EncryptMessageProvider encryptMessageTools) {
		this.encryptMessageTools = encryptMessageTools;
	}

}

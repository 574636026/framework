package com.injedu.mvc.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.alibaba.fastjson.JSON;
import com.injedu.core.dto.wrapper.ApiRequestWrapper;

/**
 * 
 * fast json to Object
 * 
 * @author joy.zhou
 * @date 2016年12月7日
 * @version 1.0
 */
public class FastJsonConvertor implements ConverterFactory<String, ApiRequestWrapper> {

	@Override
	public <T extends ApiRequestWrapper> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToRequest<>(targetType);
	}

	private class StringToRequest<T> implements Converter<String, T> {

		private final Class<T> reqType;

		public StringToRequest(Class<T> reqType) {
			this.reqType = reqType;
		}

		@Override
		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}

			return JSON.parseObject(source, reqType);
		}
	}

}

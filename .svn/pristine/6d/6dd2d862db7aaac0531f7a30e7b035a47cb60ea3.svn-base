package com.injedu.mvc.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.injedu.utils.enums.EnumDetail;
import com.injedu.utils.enums.EnumDetailUtils;

/**
 * 
 * EnmuDetail 转换
 *
 * @author joy.zhou
 * @date 2016年2月2日
 * @version 1.0
 *
 */
public class EnumDetailConvertor implements ConverterFactory<Integer, EnumDetail> {

	@Override
	public <T extends EnumDetail> Converter<Integer, T> getConverter(Class<T> targetType) {
		return new IntegerToEnum<T>(targetType);
	}

	private class IntegerToEnum<T extends EnumDetail> implements Converter<Integer, T> {

		private final Class<T> enumType;

		public IntegerToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		public T convert(Integer source) {
			if (source == null) {
				return null;
			}
			return EnumDetailUtils.valueOf(enumType, source);
		}
	}

}

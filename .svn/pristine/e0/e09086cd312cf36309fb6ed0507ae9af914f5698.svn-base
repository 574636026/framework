package com.injedu.mvc.convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.injedu.core.dto.DateRangeDto;

/**
 * 
 * 日期范围 转换
 *
 * @author joy.zhou
 * @date 2016年2月2日
 * @version 1.0
 *
 */
public class DataRangeConvertor implements Converter<String, DateRangeDto> {

	private String separate = " ~ ";

	@Override
	public DateRangeDto convert(String source) {

		if (StringUtils.isBlank(source)) {
			return null;
		}

		String[] str = source.split(separate);

		return new DateRangeDto(str[0], str[1]);
	}

	public void setSeparate(String separate) {
		this.separate = separate;
	}

}

package com.injedu.mvc.supports.fastjson.converter;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.injedu.core.constant.ResConst;
import com.injedu.core.dto.wrapper.JsonpResultWrapper;
import com.injedu.core.dto.wrapper.ResultWrapper;

/**
 * 
 * 接口数据转换器(JSON)
 * 
 * @description 对返回结果进行包装
 * @author joy.zhou
 * @date 2015年11月11日
 * @version 1.0
 *
 */
public class FastJsonWrapperHttpMessageConverter extends FastJsonHttpMessageConverter {

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// 返回值包装

		OutputStream out = outputMessage.getBody();
		String text = "";
		if (obj instanceof JsonpResultWrapper) {

			JsonpResultWrapper jsonp = ((JsonpResultWrapper) obj);
			text = jsonpWrapper(jsonp.getCallback(), JSON.toJSONString(jsonp.getResultWrapper(), this.getFeatures()));

		} else {
			if (!(obj instanceof ResultWrapper)) {
				obj = new ResultWrapper(ResConst.ResultCode.SUCCESS, "", obj);
			}
			text = JSON.toJSONString(obj, this.getFeatures());
		}

		byte[] bytes = text.getBytes(this.getCharset());
		out.write(bytes);
	}

	private String jsonpWrapper(String callback, String text) {
		return callback + "(" + text + ")";
	}

}

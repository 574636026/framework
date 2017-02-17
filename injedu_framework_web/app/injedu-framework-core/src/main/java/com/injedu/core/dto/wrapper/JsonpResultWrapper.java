package com.injedu.core.dto.wrapper;

import java.io.Serializable;

/**
 * Jsonp支持
 * 
 * @author joy.zhou
 * @date 2016年11月4日
 * @version 1.0
 */
public class JsonpResultWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	public JsonpResultWrapper() {
	}

	public JsonpResultWrapper(String callback, ResultWrapper resultWrapper) {
		this.callback = callback;
		this.resultWrapper = resultWrapper;
	}

	private String callback;

	private ResultWrapper resultWrapper;

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public ResultWrapper getResultWrapper() {
		return resultWrapper;
	}

	public void setResultWrapper(ResultWrapper resultWrapper) {
		this.resultWrapper = resultWrapper;
	}

}

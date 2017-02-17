package com.injedu.supports.push.handle.ym.filter;

import com.injedu.supports.push.handle.ym.constant.FilterType;

/**
 * 筛选条件实体
 *
 * @author joy.zhou
 * @date 2016年1月15日
 * @version 1.0
 *
 */
public class FilterEntity {
	/** 条件类型 */
	private FilterType type;
	/** 条件值 */
	private String value;
	/** 是否不包含 */
	private boolean isNot;

	public FilterEntity(FilterType type, String value) {
		this.type = type;
		this.value = value;
	}

	public FilterEntity(FilterType type, String value, boolean isNot) {
		this.type = type;
		this.value = value;
		this.isNot = isNot;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isNot() {
		return isNot;
	}

	public void setNot(boolean isNot) {
		this.isNot = isNot;
	}

}

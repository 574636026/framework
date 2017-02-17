package com.injedu.core.domain;

/**
 *
 * 具有排序字段的实体
 * 
 * @author joy.zhou
 * @date 2015年12月18日
 * @version 1.0
 *
 */
public class BaseSortedDomain extends BaseSimpleDomain {

	private static final long serialVersionUID = 8702990396050683910L;

	/** 排序号 */
	private Float sort;

	public Float getSort() {
		return sort;
	}

	public void setSort(Float sort) {
		this.sort = sort;
	}

}

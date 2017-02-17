package com.injedu.core.dto.wrapper;

import java.util.List;

import com.injedu.core.domain.BaseSimpleDomain;

/**
 * 
 * 传递数组请求参数包装类
 *
 * @author joy.zhou
 * @date 2015年12月1日
 * @version 1.0
 *
 */
public class ListRequestWrapper<T extends BaseSimpleDomain> {

	private List<T> entitys;

	public List<T> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<T> entitys) {
		this.entitys = entitys;
	}

}

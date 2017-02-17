package com.injedu.mvc.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.injedu.core.domain.BaseSortedDomain;
import com.injedu.core.service.IBaseSortedService;

/**
 * 
 * 排序controller,继承此类,则拥有crud、sort功能
 *
 * @author joy.zhou
 * @date 2015年12月16日
 * @version 1.0
 *
 */
public abstract class BaseSortedController<T extends BaseSortedDomain, M extends T> extends BaseCRUDController<T, M> {

	/**
	 * 
	 * 数据排序
	 * 
	 * @param id
	 *            数据ID
	 * @param sort
	 *            排序号
	 * @return
	 */
	@RequestMapping(value = "sort/{id}", method = RequestMethod.POST)
	public Object sort(@PathVariable Long id, @RequestParam Float sort, T entity) {

		entity.setId(id);
		entity.setSort(sort);
		getService().update(entity, getOperationId());

		return success();
	}

	/**
	 * 数据置顶
	 * 
	 * @param id
	 *            数据ID
	 * @return
	 */
	@RequestMapping(value = "sortTop/{id}", method = RequestMethod.POST)
	public Object sortTop(@PathVariable Long id, T entity) {

		entity.setId(id);

		((IBaseSortedService<T>) getService()).updateSortTop(entity, getOperationId());

		return success();
	}

	/**
	 * 数据置底
	 * 
	 * @param id
	 *            数据ID
	 * @return
	 */
	@RequestMapping(value = "sortDown/{id}", method = RequestMethod.POST)
	public Object sortDown(@PathVariable Long id, T entity) {

		entity.setId(id);

		((IBaseSortedService<T>) getService()).updateSortDown(entity, getOperationId());

		return success();
	}
}

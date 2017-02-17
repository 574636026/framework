package com.injedu.core.service;

import com.injedu.core.domain.BaseSortedDomain;
import com.injedu.core.exception.AppException;

/**
 * 
 * 排序Service接口
 * 
 * @author joy.zhou
 * @date 2015年12月18日
 * @version 1.0
 *
 */
public interface IBaseSortedService<T extends BaseSortedDomain> extends IBaseService<T> {

	/**
	 * 得到最大排序号
	 * 
	 * @return
	 * @throws AppException
	 */
	public Float getMaxSort() throws AppException;

	/**
	 * 得到最小排序号
	 * 
	 * @return
	 * @throws AppException
	 */
	public Float getMinSort() throws AppException;

	/**
	 * 排序置顶
	 * 
	 * @throws AppException
	 */
	public void updateSortTop(T entity, Long operationId) throws AppException;

	/**
	 * 排序置底
	 * 
	 * @throws AppException
	 */
	public void updateSortDown(T entity, Long operationId) throws AppException;

}

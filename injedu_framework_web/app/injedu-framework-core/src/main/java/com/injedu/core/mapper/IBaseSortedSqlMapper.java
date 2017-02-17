package com.injedu.core.mapper;

import com.injedu.core.domain.BaseSortedDomain;
import com.injedu.core.exception.AppException;

/**
 * 
 * Mapper进行接口封装,提供排序相关方法
 *
 * @author joy.zhou
 * @date 2015年11月11日
 * @version 1.0
 *
 */
public interface IBaseSortedSqlMapper<T extends BaseSortedDomain> extends IBaseSqlMapper<T> {

	/**
	 * 获取排序号最大值
	 * 
	 * @return
	 * @throws AppException
	 */
	public Float getMaxSort() throws AppException;

	/**
	 * 获取排序号最小值
	 * 
	 * @return
	 * @throws AppException
	 */
	public Float getMinSort() throws AppException;
}

package com.injedu.core.dao;

import com.injedu.core.domain.BaseSortedDomain;
import com.injedu.core.exception.AppException;
import com.injedu.core.mapper.IBaseSortedSqlMapper;

/**
 * 
 * 提供通用的排序方法
 *
 * @author joy.zhou
 * @date 2016年2月25日
 * @version 1.0
 *
 */
public abstract class BaseSortedDao<T extends BaseSortedDomain> extends BaseDao<T> {

	/**
	 * 获取排序号最大值
	 * 
	 * @return
	 * @throws AppException
	 */
	public Float getMaxSort() throws AppException {

		return ((IBaseSortedSqlMapper<T>) getMapper()).getMaxSort();
	}

	/**
	 * 获取排序号最小值
	 * 
	 * @return
	 * @throws AppException
	 */
	public Float getMinSort() throws AppException {

		return ((IBaseSortedSqlMapper<T>) getMapper()).getMinSort();
	}

}

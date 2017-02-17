package com.injedu.core.service.impl;

import com.injedu.core.dao.BaseSortedDao;
import com.injedu.core.domain.BaseSortedDomain;
import com.injedu.core.exception.AppException;
import com.injedu.core.service.IBaseSortedService;

/**
 * 
 * 排序service实现
 *
 * @author joy.zhou
 * @date 2016年2月25日
 * @version 1.0
 *
 */
public abstract class BaseSortedServiceImpl<T extends BaseSortedDomain> extends BaseServiceImpl<T>
		implements IBaseSortedService<T> {

	@Override
	public Float getMaxSort() throws AppException {
		Float max = ((BaseSortedDao<T>) getDao()).getMaxSort();

		return max == null ? 0f : max;
	}

	@Override
	public Float getMinSort() throws AppException {
		Float min = ((BaseSortedDao<T>) getDao()).getMinSort();
		return min == null ? 0f : min;
	}

	@Override
	public void updateSortTop(T entity, Long operationId) throws AppException {

		Float min = getMinSort();

		entity.setSort(min > 1 ? min - 1 : 0);

		getDao().update(entity, operationId);

	}

	@Override
	public void updateSortDown(T entity, Long operationId) throws AppException {

		Float max = getMaxSort();

		entity.setSort(max + 1);

		getDao().update(entity, operationId);
	}
}

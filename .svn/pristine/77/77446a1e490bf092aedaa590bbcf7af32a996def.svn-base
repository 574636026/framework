package com.injedu.core.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.injedu.core.dao.BaseDao;
import com.injedu.core.domain.BaseSimpleDomain;
import com.injedu.core.dto.order.Ordered;
import com.injedu.core.dto.pager.BasePagerDto;
import com.injedu.core.dto.pager.Pager;
import com.injedu.core.exception.AppException;
import com.injedu.core.service.IBaseService;

/**
 * 
 * 基础service实现
 *
 * @author joy.zhou
 * @date 2015年4月25日
 * @version 1.0
 *
 */
public abstract class BaseServiceImpl<T extends BaseSimpleDomain> implements IBaseService<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected abstract BaseDao<T> getDao();

	@Override
	public void save(T entity, Long... operationId) throws AppException {

		getDao().save(entity, operationId);
	}

	@Override
	public void save(List<T> entitis, Long... operationId) throws AppException {

		if (entitis == null || entitis.isEmpty()) {
			return;
		}
		for (T entity : entitis) {
			this.save(entity, operationId);
		}
	}

	@Override
	public void saveBatch(List<T> entities, Long... operationId) throws AppException {

		getDao().save(entities, operationId);
	}

	@Override
	public void update(T entity, Long... operationId) throws AppException {
		getDao().update(entity, operationId);
	}

	@Override
	public Long saveOrUpdate(T entity, Long... operationId) throws AppException {
		return getDao().saveOrUpdate(entity, operationId);
	}

	@Override
	public void remove(Long id) throws AppException {
		Assert.notNull(id);
		getDao().remove(id);
	}

	@Override
	public void remove(Long[] ids) throws AppException {
		if (ids == null || ids.length <= 0) {
			return;
		}
		for (Long id : ids) {
			this.remove(id);
		}
	}

	@Override
	public void delete(Long id) throws AppException {
		Assert.notNull(id);
		getDao().delete(id);
	}

	@Override
	public void delete(Long[] ids) throws AppException {

		if (ids == null || ids.length <= 0) {
			return;
		}
		for (Long id : ids) {
			this.delete(id);
		}
	}

	@Override
	public Integer count(Map<String, Object> query) throws AppException {
		return getDao().count(query);
	}

	@Override
	public T get(Map<String, Object> query) throws AppException {
		return getDao().get(query);
	}

	@Override
	public T getById(Long id) throws AppException {
		Assert.notNull(id);
		return getDao().getById(id);
	}

	@Override
	public List<T> getList(Map<String, Object> query, Ordered... orders) throws AppException {
		return getDao().getList(query, orders);
	}

	@Override
	public BasePagerDto<T> getListByPage(Map<String, Object> query, Pager pager, Ordered... orders)
			throws AppException {
		return getDao().getListByPage(query, pager, orders);
	}

}

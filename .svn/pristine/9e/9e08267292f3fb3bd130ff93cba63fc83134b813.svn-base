package com.injedu.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.injedu.core.constant.QueryConstant;
import com.injedu.core.domain.BaseSimpleDomain;
import com.injedu.core.exception.AppException;
import com.injedu.core.exception.constant.ExceptionConstants;
import com.injedu.core.mapper.IBaseSqlMapper;
import com.injedu.utils.date.DateUtils;

/**
 * 
 * 包装mapper,提供常用的封装方法
 *
 * @author joy.zhou
 * @date 2015年4月25日
 * @version 1.0
 *
 */
public abstract class BaseDao<T extends BaseSimpleDomain> extends BaseQueryDao<T> {

	protected abstract IBaseSqlMapper<T> getMapper();

	/**
	 * 添加实体
	 * 
	 * @param entity
	 *            实体对象
	 * @param operationId
	 *            操作人
	 */
	public void save(T entity, Long... operationId) throws AppException {

		try {
			entityDefaultValueProcess(entity, operationId);
			getMapper().save(entity);
		} catch (Exception e) {
			if (e instanceof org.springframework.dao.DuplicateKeyException) {
				throw new AppException(ExceptionConstants.DATA_INSERT_DUPLICATEKEY, e);
			} else {
				throw new AppException(ExceptionConstants.DATA_INSERT_ERROR, e);
			}
		}
	}

	/**
	 * 批量添加实体
	 * 
	 * @param entitys
	 *            实体对象
	 * @param operationId
	 *            操作人
	 * @throws AppException
	 */
	public void save(List<T> entitys, Long... operationId) throws AppException {

		if (entitys == null || entitys.size() <= 0) {
			return;
		}
		entityDefaultValueProcess(entitys, operationId);
		getMapper().saveBatch(entitys);
	}

	/**
	 * 更新或删除实体
	 * 
	 * @param entity
	 *            实体对象
	 * @param operationId
	 *            操作人
	 * @return
	 */
	public Long saveOrUpdate(T entity, Long... operationId) {
		if (null == entity.getId()) {
			this.save(entity, operationId);
		} else {
			this.update(entity, operationId);
		}
		return entity.getId();
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体对象
	 * @param operationId
	 *            操作人
	 */
	public void update(T entity, Long... operationId) throws AppException {

		try {
			Assert.notNull(entity.getId());
			entityDefaultValueProcess(entity, operationId);
			getMapper().update(entity);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_UPDATE_ERROR, e);
		}
	}

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	public void remove(Long id) throws AppException {

		if (id == null) {
			return;
		}
		try {
			getMapper().remove(id);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_DELETE_ERROR, e);
		}
	}

	/**
	 * 标记删除实体
	 * 
	 * @param entity
	 */
	public void delete(Long id) throws AppException {

		if (id == null) {
			return;
		}
		try {
			getMapper().delete(id);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_DELETE_ERROR, e);
		}
	}

	/**
	 * 获取实体数量
	 * 
	 * @param query
	 *            查询参数
	 * @return
	 */
	public Integer count(Map<String, Object> query) throws AppException {
		try {
			return getMapper().count(query);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_SELECT_ERROR, e);
		}
	}

	/**
	 * 获取实体对象
	 * 
	 * @param query
	 *            查询参数
	 * @return BasePagerDto
	 */
	public T get(Map<String, Object> query) throws AppException {
		try {
			return getMapper().get(query);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_SELECT_ERROR, e);
		}
	}

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T getById(Long id) throws AppException {

		Map<String, Object> query = new HashMap<String, Object>();
		query.put(QueryConstant.QUERY_ID, id);
		try {
			return getMapper().get(query);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_SELECT_ERROR, e);
		}

	}

	public List<T> queryList(Map<String, Object> query) throws AppException {

		return getMapper().getList(query);
	}

	/**
	 * 
	 * 新增/修改实体时默认数据处理
	 * 
	 * @param domain
	 *            实体对象
	 * @param operationId
	 *            操作人
	 */
	protected void entityDefaultValueProcess(T domain, Long... operationId) {

		entityDefaultValueProcess(domain, DateUtils.nowTime(), operationId);

	}

	/**
	 * 新增/修改实体时默认数据处理
	 * 
	 * @param domain
	 *            实体对象
	 * @param time
	 *            操作时间
	 * @param operationId
	 *            操作人
	 */
	protected void entityDefaultValueProcess(T domain, String time, Long... operationId) {

		if (domain.getId() == null) {
			domain.setIsDelete(false);
			domain.setCreateTime(time);
			domain.setLastUpdateTime(time);
			if (operationId.length > 0) {
				domain.setCreateBy(operationId[0]);
				domain.setLastUpdateBy(operationId[0]);
			}
		} else {
			domain.setLastUpdateTime(time);
			if (operationId.length > 0) {
				domain.setLastUpdateBy(operationId[0]);
			}
		}

	}

	/**
	 * 
	 * 新增/修改实体时默认数据处理
	 * 
	 * @param domain
	 *            实体对象列表
	 */
	protected void entityDefaultValueProcess(List<T> domains, Long... operationId) {

		String time = DateUtils.nowTime();

		for (T domain : domains) {
			entityDefaultValueProcess(domain, time, operationId);
		}
	}

}

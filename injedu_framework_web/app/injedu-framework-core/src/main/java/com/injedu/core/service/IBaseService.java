package com.injedu.core.service;

import java.util.List;
import java.util.Map;

import com.injedu.core.domain.BaseSimpleDomain;
import com.injedu.core.dto.order.Ordered;
import com.injedu.core.dto.pager.BasePagerDto;
import com.injedu.core.dto.pager.Pager;
import com.injedu.core.exception.AppException;

/**
 * 
 * 基础Service接口
 * 
 * @author joy.zhou
 * @date 2015年12月18日
 * @version 1.0
 *
 */
public interface IBaseService<T extends BaseSimpleDomain> {

	/**
	 * 添加实体
	 * 
	 * @param entity
	 *            数据实体
	 * @param operationId
	 *            操作人
	 */
	void save(T entity, Long... operationId) throws AppException;

	/**
	 * 添加实体
	 * 
	 * @param entity
	 *            数据实体
	 * @param operationId
	 *            操作人
	 */
	void save(List<T> entity, Long... operationId) throws AppException;
	/**
	 * 批量添加实体
	 * 
	 * @param entities
	 *            数据实体列表
	 * @param operationId
	 *            操作人
	 * @throws AppException
	 */
	void saveBatch(List<T> entities, Long... operationId) throws AppException;

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            数据实体
	 * @param operationId
	 *            操作人
	 */
	void update(T entity, Long... operationId) throws AppException;

	/**
	 * 添加或者更新实体
	 * 
	 * @param entity
	 *            数据实体
	 * @param operationId
	 *            操作人
	 * @return
	 */
	Long saveOrUpdate(T entity, Long... operationId) throws AppException;

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            数据实体ID
	 */
	void remove(Long id) throws AppException;

	/**
	 * 删除实体
	 * 
	 * @param ids
	 *            数据实体ID数组
	 */
	void remove(Long[] ids) throws AppException;

	/**
	 * 标记删除实体
	 * 
	 * @param id
	 *            数据实体ID
	 */
	void delete(Long id) throws AppException;

	/**
	 * 标记删除实体
	 * 
	 * @param ids
	 *            数据实体ID数组
	 */
	void delete(Long[] ids) throws AppException;

	/**
	 * 获取实体数量
	 * 
	 * @param query
	 *            查询参数
	 * @return 数量
	 */
	Integer count(Map<String, Object> query) throws AppException;

	/**
	 * 获取实体对象
	 * 
	 * @param query
	 *            查询参数
	 * @return BasePagerDto 分页对象
	 */
	T get(Map<String, Object> query) throws AppException;

	/**
	 * 
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            ID
	 * @return entity 数据实体
	 */
	T getById(Long id) throws AppException;

	/**
	 * 获取实体对象列表
	 * 
	 * @param query
	 *            查询参数
	 * @param orders
	 *            排序参数
	 * @return List 实体列表
	 */
	List<T> getList(Map<String, Object> query, Ordered... orders) throws AppException;

	/**
	 * 获取分页实体对象列表
	 * 
	 * @param query
	 *            查询参数
	 * @param pager
	 *            分页参数
	 * @param orders
	 *            排序参数
	 * @return BasePagerDto 分页数据
	 */
	BasePagerDto<T> getListByPage(Map<String, Object> query, Pager pager, Ordered... orders) throws AppException;

}

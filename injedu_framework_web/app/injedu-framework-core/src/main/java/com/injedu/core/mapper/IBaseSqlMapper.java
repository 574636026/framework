package com.injedu.core.mapper;

import java.util.List;
import java.util.Map;

import com.injedu.core.domain.BaseSimpleDomain;

/**
 * 
 * Mapper进行接口封装，提供常用的增删改查组件
 *
 * @author joy.zhou
 * @date 2015年11月11日
 * @version 1.0
 *
 */
public interface IBaseSqlMapper<T extends BaseSimpleDomain> extends IBaseMapper {

	/**
	 * 添加实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 批量添加实体
	 * 
	 * @param entities
	 */
	void saveBatch(List<T> entities);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	int update(T entity);

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	void remove(Long id);

	/**
	 * 标记删除
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 获取实体数量
	 * 
	 * @param query
	 *            查询参数
	 * @return
	 */
	Integer count(Map<String, Object> query);

	/**
	 * 获取实体对象
	 * 
	 * @param query
	 *            查询参数
	 * @return BasePagerDto
	 */
	T get(Map<String, Object> query);

	/**
	 * 获取实体对象
	 * 
	 * @param query
	 *            查询参数
	 * @return List
	 */
	List<T> getList(Map<String, Object> query);

}

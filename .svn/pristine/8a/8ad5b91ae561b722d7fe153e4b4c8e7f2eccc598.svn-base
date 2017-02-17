package com.injedu.core.mapper;

import java.util.List;

import com.injedu.core.domain.BaseSimpleDomain;

/**
 * 
 * Mapper 多对多映射对象进行接口封装
 *
 * @author joy.zhou
 * @date 2015年11月11日
 * @version 1.0
 *
 */
public interface IBaseMappingSqlMapper<T extends BaseSimpleDomain> extends IBaseMapper {

	/**
	 * 添加实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 批量添加
	 * 
	 * @param entities
	 */
	void saveBatch(List<T> entities);

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	void remove(T entity);

}

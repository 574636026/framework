package com.injedu.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.injedu.core.constant.QueryConstant;
import com.injedu.core.dto.order.Ordered;
import com.injedu.core.dto.pager.BasePagerDto;
import com.injedu.core.dto.pager.Pager;
import com.injedu.core.exception.AppException;
import com.injedu.core.exception.constant.ExceptionConstants;

/**
 * 
 * 提供分页、排序查询组件
 *
 * @author joy.zhou
 * @date 2016年4月29日
 * @version 1.0
 *
 */
public abstract class BaseQueryDao<T> {

	/**
	 * 查询实体列表
	 * 
	 * @param query
	 * @return
	 */
	protected abstract List<T> queryList(Map<String, Object> query);

	/**
	 * 查询实体数量
	 * 
	 * @param query
	 *            查询参数
	 * @return
	 */
	public abstract Integer count(Map<String, Object> query);

	/**
	 * 获取分页实体对象列表
	 * 
	 * @param query
	 *            查询参数
	 * @param pager
	 *            分页对象
	 * @param order
	 *            排序对象
	 * @return
	 * @throws AppException
	 */
	public BasePagerDto<T> getListByPage(Map<String, Object> query, Pager pager, Ordered... orders)
			throws AppException {
		if (query == null) {
			query = new HashMap<String, Object>();
		}
		this.setOrderQuery(query, orders);
		return this.getListByPage(query, pager.getCurrentPage(), pager.getPageSize());
	}

	/**
	 * 获取分页实体对象列表
	 * 
	 * @param query
	 *            查询参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            查询条数
	 * @return
	 */
	public BasePagerDto<T> getListByPage(Map<String, Object> query, int currentPage, int pageSize) throws AppException {

		BasePagerDto<T> result = new BasePagerDto<T>();

		if (pageSize > 0) {

			result.setResultList(this.getList(query, currentPage, pageSize));
			Pager page = new Pager(currentPage, pageSize);
			page.rebuild(this.count(query));
			result.setPager(page);

		} else {
			List<T> list = this.getList(query);
			result.setResultList(list);
			Pager page = new Pager(currentPage, pageSize);
			page.rebuild(list.size());
			result.setPager(page);
		}

		return result;
	}

	/**
	 * 获取实体对象列表
	 * 
	 * @param query
	 *            查询参数
	 * @param order
	 *            排序参数
	 * @return
	 * @throws AppException
	 */
	public List<T> getList(Map<String, Object> query, Ordered... orders) throws AppException {
		if (query == null) {
			query = new HashMap<String, Object>();
		}

		query.put(QueryConstant.QUERY_IS_DELETE, false);

		this.setOrderQuery(query, orders);

		try {
			return queryList(query);
		} catch (Exception e) {
			throw new AppException(ExceptionConstants.DATA_SELECT_ERROR, e);
		}

	}

	/**
	 * 获取分页实体对象列表
	 * 
	 * @param query
	 *            查询参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @param order
	 *            排序参数
	 * @return List
	 */
	public List<T> getList(Map<String, Object> query, int currentPage, int pageSize, Ordered... orders)
			throws AppException {
		if (query == null) {
			query = new HashMap<String, Object>();
		}
		Pager pager = new Pager(currentPage, pageSize);
		query.put(QueryConstant.QUERY_PAGE, pager);
		query.put(QueryConstant.QUERY_PAGE_ENABLED, true);
		return this.getList(query, orders);
	}

	protected void setOrderQuery(Map<String, Object> query, Ordered... orders) {

		if (orders == null || orders.length <= 0) {
			return;
		}
		if (orders.length > 1) {
			query.put(QueryConstant.ORDERED_LIST, orders);
		} else if (orders.length == 1) {
			query.put(QueryConstant.ORDERED, orders[0]);
		}
	}
}

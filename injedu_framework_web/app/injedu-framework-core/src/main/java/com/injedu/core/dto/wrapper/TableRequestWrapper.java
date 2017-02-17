package com.injedu.core.dto.wrapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.injedu.core.dto.order.Ordered;
import com.injedu.core.dto.pager.Pager;

/**
 * 
 * Table 请求参数实体(datatables)
 *
 * @author joy.zhou
 * @date 2015年11月26日
 * @version 1.0
 *
 */
public class TableRequestWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 分页参数 */
	private Pager pager;
	/** 排序参数 */
	private Ordered order;
	/** 查询参数 */
	private Map<String, Object> query = new HashMap<String, Object>(0);

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Ordered getOrder() {
		return order;
	}

	public void setOrder(Ordered order) {
		this.order = order;
	}

	public Map<String, Object> getQuery() {
		return query;
	}

	public void setQuery(Map<String, Object> query) {
		this.query = query;
	}

}

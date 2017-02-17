package com.injedu.core.dto.wrapper;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 分页响应对象
 *
 * @author joy.zhou
 * @date 2016年1月26日
 * @version 1.0
 *
 */
public class ApiPageResponseWrapper<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 总页码 */
	private int totalPages;
	/** 总数量 */
	private int totalSize;
	/** 结果列表 */
	private List<T> result;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

}

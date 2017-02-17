package com.injedu.core.dto.pager;

/**
 * 
 * 分页信息对象
 *
 * @author joy.zhou
 * @date 2015年11月26日
 * @version 1.0
 *
 */
public class Pager {
	/** 当前页码 */
	private int currentPage = 1;
	/** 每页数量 */
	private int pageSize = 10;
	/** 总页数 */
	private int totalPages;
	/** 总数 */
	private int totalSize;

	public Pager() {
	}

	public Pager(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	/**
	 * get current page number.
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * set current page number.
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {

		if (currentPage < 1) {
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}

	/**
	 * get page size
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * set page size
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * get total pages
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * set total pages
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @param totalPages
	 *            the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * get total size
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @return the totalSize
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * set total size
	 * 
	 * @Date : 2011-3-4
	 * 
	 * @param totalSize
	 *            the totalSize to set
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * According to total size and page size, rebuild current page and total
	 * pages.
	 * 
	 * @Date : 2011-3-23
	 * @param totalSize
	 *            - total size of records
	 */
	public void rebuild(int totalSize) {
		setTotalSize(totalSize);
		setTotalPages((totalSize - 1) / getPageSize() + 1);
		if (this.currentPage < 1) {
			setCurrentPage(1);
		}
		if (this.currentPage > this.totalPages) {
			setCurrentPage(this.totalPages);
		}
	}

	public int getCurrentPageFormRow() {
		return (currentPage - 1) * pageSize;
	}

	public void setStart(int start) {
		setCurrentPage(start / pageSize + 1);
	}

	public static Pager create(int start, int pageSize) {
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setStart(start > 0 ? start : 0);
		return pager;
	}

	@Override
	public String toString() {
		return "Pager [currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalPages=" + totalPages
				+ ", totalSize=" + totalSize + "]";
	}

}

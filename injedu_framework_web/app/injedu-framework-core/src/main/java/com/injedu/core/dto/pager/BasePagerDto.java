package com.injedu.core.dto.pager;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Page Info Dto
 *
 * @author joy.zhou
 * @date 2015年4月25日
 * @version 1.0
 *
 */
public class BasePagerDto<T> implements Serializable {

	private static final long serialVersionUID = 5628075185898802523L;
	/** 分页信息 */
	private Pager pager = new Pager();
	/** 数据对象 */
	private List<T> resultList;

	public BasePagerDto() {
	}

	public BasePagerDto(Pager pager, List<T> resultList) {
		this.pager = pager;
		this.resultList = resultList;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

}

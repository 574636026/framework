package com.injedu.core.domain;

import java.io.Serializable;

public class BaseDomain<ID extends Serializable> extends AbstractDomain<ID> {

	private static final long serialVersionUID = 1L;
	/** ID */
	private ID id;
	/** 创建人ID */
	private ID createBy;
	/** 创建时间 */
	private String createTime;
	/** 修改人ID */
	private ID lastUpdateBy;
	/** 修改时间 */
	private String lastUpdateTime;
	/** 是否删除 */
	private Boolean isDelete;

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public void setId(ID id) {
		this.id = id;
	}

	public ID getCreateBy() {
		return createBy;
	}

	public void setCreateBy(ID createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public ID getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(ID lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}

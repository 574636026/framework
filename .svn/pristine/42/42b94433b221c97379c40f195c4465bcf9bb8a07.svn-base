package com.injedu.core.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 合并数据(实体对象)
 *
 * @author joy.zhou
 * @date 2014年12月29日
 * @version 1.0
 *
 */
public class MergeDto<T> {

	/** 需要保存的数据 */
	private Collection<T> saveData = new ArrayList<T>(0);
	/** 需要删除的数据 */
	private Collection<T> deleteData = new ArrayList<T>(0);

	public Collection<T> getSaveData() {
		return saveData;
	}

	public void setSaveData(Collection<T> saveData) {
		this.saveData = saveData;
	}

	public Collection<T> getDeleteData() {
		return deleteData;
	}

	public void setDeleteData(Collection<T> deleteData) {
		this.deleteData = deleteData;
	}

}

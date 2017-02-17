package com.injedu.core.dto.order;

/**
 * 
 * 排序对象
 *
 * @author joy.zhou
 * @date 2015年11月24日
 * @version 1.0
 *
 */
public class Ordered {

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	/** 字段名 (为数据库字段对应的name,具体查看mapper中的<sql id="orderInclude">配置) */
	private String column;
	/** 排序类型 asc 、 desc */
	private String sortType = ASC;

	public Ordered() {
	}

	public Ordered(String column) {
		this.column = column;
	}

	public Ordered(String column, String sortType) {
		this.column = column;
		this.sortType = sortType;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		if (!ASC.equals(sortType) || !DESC.equals(sortType)) {
			this.sortType = ASC;
		}
		this.sortType = sortType;
	}

	@Override
	public String toString() {
		return "Ordered [column=" + column + ", sortType=" + sortType + "]";
	}

}

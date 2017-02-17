package com.injedu.core.supports.excel.dto;

/**
 *
 * Excel数据基类
 *
 */
public abstract class BaseExcelDataDto {
	protected String reservedField;
	protected Integer rowIndex;

	public String getReservedField() {
		return reservedField;
	}

	public void setReservedField(String reservedField) {
		this.reservedField = reservedField;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

}

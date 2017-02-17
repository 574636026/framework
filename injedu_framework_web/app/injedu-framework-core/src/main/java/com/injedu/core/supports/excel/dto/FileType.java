package com.injedu.core.supports.excel.dto;

/**
 *
 * Excel文件类型
 *
 */
public enum FileType {

	xlsx(".xlsx"), xls(".xls");

	private String value;

	private FileType(String value) {
		this.value = value;
	}

	public String getValue() {

		return value;
	}

}

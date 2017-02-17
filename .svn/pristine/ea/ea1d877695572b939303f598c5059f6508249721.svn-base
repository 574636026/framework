/******************************************************************************
 * @File name   :      BaseImportHandler.java
 * @Author      :      XINLYU
 * @Date        :      2013-5-28
 * @Copyright Notice: 
 * Copyright (c) 2012 Capgemini, Inc. All  Rights Reserved.
 * ----------------------------------------------------------------------------
 * Date                   Who         Version        Comments
 * 2013-5-28 下午3:55:23        XINLYU     1.0            Initial Version
 *****************************************************************************/
package com.injedu.core.supports.excel.handler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.injedu.core.exception.AppException;
import com.injedu.core.supports.excel.dto.BaseExcelDataDto;

/**
 *
 * @author joy.zhou
 * @date 2016年2月18日
 * @version 1.0
 *
 */
abstract public class BaseImportHandler<T extends BaseExcelDataDto> {

	/** 导入文件名 */
	private String fileName;
	/** 导入文件流 */
	private InputStream fileStream;

	/** 错误信息 */
	private String errorMsg;

	/** 生成实体列表 */
	protected List<T> resultList = new ArrayList<T>(0);

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 执行导入
	 * 
	 * @return
	 * @throws AppException
	 */
	abstract public Boolean doImport() throws AppException;

}

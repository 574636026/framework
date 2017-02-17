package com.injedu.core.supports.excel.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.injedu.core.exception.AppException;

/**
 *
 * Excel解析
 *
 * @author joy.zhou
 * @date 2016年2月17日
 * @version 1.0
 *
 */
public interface ExcelParser {

	/**
	 * 解析文件
	 * 
	 * @param importExcelInputStream
	 *            导入文件流
	 * @param mappingFilePath
	 *            模板路径
	 * @param templateName
	 *            模板名称
	 * @throws AppException
	 */
	void parse(InputStream importExcelInputStream, String mappingFilePath, String templateName) throws AppException;

	/**
	 * 获取解析数据
	 * 
	 * @return
	 */
	Map<String, List<?>> getDataMap();

	/**
	 * 获取解析错误信息
	 * 
	 * @return
	 */
	Map<String, Map<String, List<String>>> getErrorMap();
}

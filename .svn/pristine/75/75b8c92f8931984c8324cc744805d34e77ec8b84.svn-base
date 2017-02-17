package com.injedu.core.supports.excel.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSON;
import com.injedu.core.exception.AppException;
import com.injedu.core.supports.excel.constant.ExcelConstant;
import com.injedu.core.supports.excel.domain.ExcelMapping;
import com.injedu.core.supports.excel.domain.ExcelMappings;

/**
 * 
 * Excel模板映射
 *
 * @author joy.zhou
 * @date 2016年2月18日
 * @version 1.0
 *
 */
public class ExcelMappingGenerator {

	private Map<String, ExcelMapping> excelMappingMap;

	private static ExcelMappingGenerator instance = new ExcelMappingGenerator();

	private ExcelMappingGenerator() {

	}

	public static ExcelMappingGenerator getInstance() {
		return instance;
	}

	public ExcelMapping getExcelMapping(InputStream mappingFileInputStream, String templateName) throws AppException {

		if (StringUtils.isBlank(templateName)) {
			throw new AppException(ExcelConstant.Error.EXCEL_TEMPLATE_NAME_EMPTY);
		}

		if (null == excelMappingMap) {
			excelMappingMap = new HashMap<String, ExcelMapping>();
		}

		ExcelMapping returnedExcelMapping = excelMappingMap.get(templateName);
		if (null != returnedExcelMapping) {
			return returnedExcelMapping;
		}

		if (null == mappingFileInputStream) {
			throw new AppException(ExcelConstant.Error.EXCEL_FILE_MAPPING_EMPTY);
		}

		ExcelMappings excelMappings = parseJsonMapping(mappingFileInputStream);

		if (null == excelMappings) {
			return null;	
		}

		List<ExcelMapping> excelMappingList = excelMappings.getExcelMappings();

		for (ExcelMapping excelMapping : excelMappingList) {
			if (templateName.equals(excelMapping.getTemplateName())) {
				returnedExcelMapping = excelMapping;
				break;
			}
		}
		excelMappingMap.put(returnedExcelMapping.getTemplateName(), returnedExcelMapping);

		return returnedExcelMapping;
	}

	private ExcelMappings parseJsonMapping(InputStream mappingFileInputStream) {

		try {
			String body = StreamUtils.copyToString(mappingFileInputStream, Charset.forName("utf-8"));
			return JSON.parseObject(body, ExcelMappings.class);
		} catch (IOException e) {
			throw new AppException(e.getMessage(), e);
		}

	}

//	private ExcelMappings parseXmlMapping(InputStream mappingFileInputStream) {
//
//		XStream xstream = new XStream(new DomDriver());
//
//		xstreamBinding(xstream);
//
//		return (ExcelMappings) xstream.fromXML(mappingFileInputStream);
//
//	}
//
//	private void xstreamBinding(XStream xstream) {
//		xstream.alias("excelMappings", ExcelMappings.class);
//		xstream.aliasAttribute(ExcelMappings.class, "namespace", "namespace");
//		xstream.addImplicitCollection(ExcelMappings.class, "excelMappings");
//
//		xstream.alias("excelMapping", ExcelMapping.class);
//		xstream.aliasAttribute(ExcelMapping.class, "templateName", "templateName");
//		xstream.addImplicitCollection(ExcelMapping.class, "sheetMappings");
//
//		xstream.alias("sheetMapping", SheetMapping.class);
//		xstream.aliasAttribute(SheetMapping.class, "name", "name");
//		xstream.aliasAttribute(SheetMapping.class, "startRowIndex", "startRowIndex");
//		xstream.aliasAttribute(SheetMapping.class, "className", "className");
//		xstream.addImplicitCollection(SheetMapping.class, "columnMappings");
//
//		xstream.alias("columnMapping", ColumnMapping.class);
//		xstream.aliasAttribute(ColumnMapping.class, "header", "header");
//		xstream.aliasAttribute(ColumnMapping.class, "columnIndex", "columnIndex");
//		xstream.aliasAttribute(ColumnMapping.class, "field", "field");
//		xstream.aliasAttribute(ColumnMapping.class, "type", "type");
//		xstream.aliasAttribute(ColumnMapping.class, "msgkey", "msgkey");
//
//	}

}

package com.injedu.core.supports.excel.util;

import org.apache.commons.lang3.StringUtils;

import com.injedu.core.supports.excel.service.ExcelParser;
import com.injedu.core.supports.excel.service.impl.Excel2003ParserImpl;
import com.injedu.core.supports.excel.service.impl.Excel2007ParserImpl;

/**
 *
 * Excel解析工厂方法
 *
 */
public final class ExcelParserFactory {

	private static final String EXCEL2007_SUFFIX = "xlsx";
	private static final String EXCEL2003_SUFFIX = "xls";

	public static ExcelParser getExcelParser(String orginalFileName) {

		if (StringUtils.isBlank(orginalFileName)) {
			return null;
		}
		if (orginalFileName.endsWith(EXCEL2003_SUFFIX)) {
			return new Excel2003ParserImpl();
		}
		if (orginalFileName.endsWith(EXCEL2007_SUFFIX)) {
			return new Excel2007ParserImpl();
		}
		return null;
	}

}

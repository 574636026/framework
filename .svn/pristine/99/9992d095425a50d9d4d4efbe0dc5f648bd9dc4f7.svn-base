package com.injedu.core.supports.excel.service;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.injedu.core.exception.AppException;
import com.injedu.core.supports.excel.constant.ExcelConstant;
import com.injedu.core.supports.excel.domain.ColumnMapping;
import com.injedu.core.supports.excel.domain.ExcelMapping;
import com.injedu.core.supports.excel.domain.SheetMapping;
import com.injedu.core.supports.excel.util.ExcelMappingGenerator;
import com.injedu.core.tools.MessageTools;

/**
 * 
 * Abstract Excel Parser
 *
 */
public abstract class AbstractExcelParser implements ExcelParser {

	protected static final String PREFIX_GETTER = "get";

	protected static final String PREFIX_SETTER = "set";

	protected Map<String, Map<String, List<String>>> errorMap;
	protected Map<String, List<?>> dataMap;

	protected static final String COLON = ": ";
	protected static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 解析Excel文件
	 * 
	 * @param importExcelInputStream
	 *            导入文件流
	 * @param excelMapping
	 *            模板配置信息
	 * @throws AppException
	 */
	protected abstract void parseExcel(InputStream importExcelInputStream, ExcelMapping excelMapping)
			throws AppException;

	@Override
	public void parse(InputStream importExcelInputStream, String mappingFileName, String templateName)
			throws AppException {
		InputStream mappingFileInputStream = AbstractExcelParser.class.getClassLoader()
				.getResourceAsStream(mappingFileName);
		parseFile(importExcelInputStream, mappingFileInputStream, templateName);
	}

	protected void parseFile(InputStream importExcelInputStream, InputStream mappingFileInputStream,
			String templateName) throws AppException {

		ExcelMapping excelMapping = ExcelMappingGenerator.getInstance().getExcelMapping(mappingFileInputStream,
				templateName);

		if (null == excelMapping) {
			throw new AppException(ExcelConstant.Error.EXCEL_FILE_MAPPING_EMPTY);
		}

		if (null == errorMap) {
			errorMap = new HashMap<String, Map<String, List<String>>>();
		}
		if (null == dataMap) {
			dataMap = new HashMap<String, List<?>>();
		}

		Map<String, List<String>> validationErrorList = validateExcelMapping(excelMapping);
		if (null != validationErrorList && validationErrorList.size() > 0) {
			errorMap.put(ExcelConstant.Error.EXCEL_VALIDATION_LIST_ERROR, validationErrorList);
		}

		parseExcel(importExcelInputStream, excelMapping);
	}

	/**
	 * Excel 模板校验
	 * 
	 * @param excelMapping
	 * @return
	 */
	private Map<String, List<String>> validateExcelMapping(ExcelMapping excelMapping) {

		Map<String, List<String>> validationErrorMap = new HashMap<String, List<String>>();
		List<String> validationErrorList = new LinkedList<String>();

		List<SheetMapping> sheetMappings = excelMapping.getSheetMappings();
		if (null == sheetMappings || sheetMappings.size() == 0) {
			validationErrorList.add("");
		}

		for (SheetMapping sheetMapping : sheetMappings) {
			if (sheetMapping.getStartRowIndex() < 0) {
				validationErrorList.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_CONFIGFILE_ERROR));
			}
			Class<?> clazz = null;
			try {
				clazz = Class.forName(sheetMapping.getClassName());
			} catch (ClassNotFoundException e) {
				validationErrorList.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_DOMAINCLASS_NOTFOUND));
			}

			if (null != clazz) {
				List<ColumnMapping> columnMappings = sheetMapping.getColumnMappings();

				if (null == columnMappings || columnMappings.size() == 0) {
					validationErrorList
							.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_COLUMNMAPPING_NOTFOUND));
				}

				for (ColumnMapping columnMapping : columnMappings) {

					if (StringUtils.isBlank(columnMapping.getHeader())) {
						validationErrorList
								.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_ATTR_HEADER_NOTFOUND));
					}

					if (columnMapping.getColumnIndex() < 0) {
						validationErrorList
								.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_ATTR_COLUMNINDEX_NEGATIVE));
					}

					String field = columnMapping.getField();

					if (StringUtils.isBlank(field)) {
						validationErrorList
								.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_ATTR_FIELD_EMPTY));
					}
					String getterMethodName = generateMethodName(field, PREFIX_GETTER);
					String setterMethodName = generateMethodName(field, PREFIX_SETTER);
					try {
						Method[] methods = clazz.getMethods();
						if (!doesMethodExist(getterMethodName, methods)
								|| !doesMethodExist(setterMethodName, methods)) {
							validationErrorList
									.add(MessageTools.message(ExcelConstant.Error.EXCEL_PARSER_DOMAINCLASS_FIELD_ERROR,
											sheetMapping.getClassName(), field));
						}
					} catch (SecurityException e) {
						validationErrorList.add(MessageTools.message(
								ExcelConstant.Error.EXCEL_PARSER_DOMAINCLASS_FIELD_ERROR, sheetMapping.getClassName()));
					}
				}

			}
		}

		validationErrorMap.put(ExcelConstant.Error.EXCEL_VALIDATION_LIST_ERROR, validationErrorList);

		return validationErrorMap;

	}

	private boolean doesMethodExist(String methodName, Method[] methods) {
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				return true;
			}
		}
		return false;
	}

	protected String generateMethodName(String field, String prefix) {
		return prefix + StringUtils.capitalize(field);
	}

	@Override
	public Map<String, List<?>> getDataMap() {
		return dataMap;
	}

	@Override
	public Map<String, Map<String, List<String>>> getErrorMap() {
		return errorMap;
	}

}

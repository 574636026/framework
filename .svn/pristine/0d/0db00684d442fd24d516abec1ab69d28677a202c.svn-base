package com.injedu.core.supports.excel.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cglib.beans.BulkBean;
import org.springframework.core.convert.ConversionService;

import com.injedu.core.exception.AppException;
import com.injedu.core.supports.excel.constant.ExcelConstant;
import com.injedu.core.supports.excel.constant.StringTypeRegistry;
import com.injedu.core.supports.excel.domain.ColumnMapping;
import com.injedu.core.supports.excel.domain.ExcelMapping;
import com.injedu.core.supports.excel.domain.SheetMapping;
import com.injedu.core.supports.excel.service.AbstractExcelParser;
import com.injedu.core.supports.excel.validator.ExcelDtoValidator;
import com.injedu.core.tools.MessageTools;
import com.injedu.core.tools.SpringTools;

public class Excel2007ParserImpl extends AbstractExcelParser {

	@Override
	public void parseExcel(InputStream importExcelInputStream, ExcelMapping excelMapping) throws AppException {
		if (errorMap.keySet().size() > 1) {
			return;
		}

		try {

			ConversionService conversionService = SpringTools.getBean(ConversionService.class);

			XSSFWorkbook workbook = new XSSFWorkbook(importExcelInputStream);

			List<SheetMapping> sheetMappings = excelMapping.getSheetMappings();

			for (SheetMapping sheetMapping : sheetMappings) {

				List<ColumnMapping> columnMappings = sheetMapping.getColumnMappings();

				int size = columnMappings.size();

				int[] columnIndexArray = new int[size];
				String[] getters = new String[size];
				String[] setters = new String[size];
				Class<?>[] types = new Class<?>[size];

				for (int i = 0; i < size; i++) {
					ColumnMapping columnMapping = columnMappings.get(i);

					columnIndexArray[i] = columnMapping.getColumnIndex();
					getters[i] = generateMethodName(columnMapping.getField(), PREFIX_GETTER);
					setters[i] = generateMethodName(columnMapping.getField(), PREFIX_SETTER);
					types[i] = StringTypeRegistry.getClass(columnMapping.getType());
				}

				XSSFSheet sheet = workbook.getSheet(sheetMapping.getName());

				if (null == sheet) {
					errorMap.get(ExcelConstant.Error.EXCEL_VALIDATION_LIST_ERROR)
							.get(ExcelConstant.Error.EXCEL_VALIDATION_LIST_ERROR).add(MessageTools.message(
									ExcelConstant.Error.EXCEL_VALIDATION_SHEET_NOTFOUND, sheetMapping.getName()));
					continue;
				}

				int rowIndex = sheetMapping.getStartRowIndex();
				int total = sheet.getLastRowNum();

				Map<String, List<String>> rowErrorMap = new LinkedHashMap<String, List<String>>();
				List<Object> sheetDataList = new LinkedList<Object>();

				while (rowIndex > -1) {

					if (rowIndex > total) {
						break;
					}

					Object dtoInstance = null;
					Map<String, String> dtoFieldMap = new HashMap<String, String>();

					List<String> dtoValidationList = new ArrayList<String>();
					int rowNum = rowIndex + 1;
					try {
						Object[] propValues = new Object[size];
						String[] cellValues = new String[size];
						for (int i = 0; i < columnIndexArray.length; i++) {
							String cellValue = getCellValue(workbook, sheet, columnIndexArray[i], rowIndex);
							cellValues[i] = cellValue;
							propValues[i] = conversionService.convert(cellValue, types[i]);
						}
						if (isEmptyRowInSheet(cellValues) && rowIndex == sheetMapping.getStartRowIndex()) {
							errorMap.get(ExcelConstant.Error.EXCEL_VALIDATION_LIST_ERROR)
									.get(ExcelConstant.Error.EXCEL_VALIDATION_LIST_ERROR)
									.add(MessageTools.message(ExcelConstant.Error.EXCEL_VALIDATION_CONTENT_EMPTY));
						}

						if (isEmptyRowInSheet(cellValues)) {
							rowIndex++;
							continue;
						}

						Class<?> clazz = Class.forName(sheetMapping.getClassName());
						dtoInstance = clazz.newInstance();

						BulkBean bulkBean = BulkBean.create(clazz, getters, setters, types);

						bulkBean.setPropertyValues(dtoInstance, propValues);
						ExcelDtoValidator excelDtoValidator = (ExcelDtoValidator) dtoInstance;
						dtoFieldMap = excelDtoValidator.validate();
						dtoValidationList = getRowValidationInfoList(dtoFieldMap, columnMappings);
					} catch (ClassNotFoundException | InstantiationException e) {
						dtoValidationList.add(MessageTools.message(
								ExcelConstant.Error.EXCEL_VALIDATION_DOMAINCLASS_ERROR, sheetMapping.getClassName()));
					}
					if (null != dtoValidationList && dtoValidationList.size() > 0) {
						rowErrorMap.put(MessageTools.message(ExcelConstant.Error.EXCEL_VALIDATION_ROW_ERROR, rowNum),
								dtoValidationList);
					}
					if (null != dtoInstance) {
						sheetDataList.add(dtoInstance);
					}
					rowIndex++;

				}
				dataMap.put(sheetMapping.getName(), sheetDataList);
				errorMap.put(sheetMapping.getName(), rowErrorMap);

			}

		} catch (IOException e) {
			throw new AppException(ExcelConstant.Error.EXCEL_FILE_READ_ERROR);
		} catch (IllegalAccessException e) {

		}
	}

	private List<String> getRowValidationInfoList(Map<String, String> dtoFieldMap, List<ColumnMapping> columnMappings) {
		List<String> rowValidationInfoList = new ArrayList<String>();

		for (int i = 0; i < columnMappings.size(); i++) {
			ColumnMapping columnMapping = columnMappings.get(i);
			String field = columnMapping.getField();
			if (dtoFieldMap.containsKey(field)) {
				if (StringUtils.isNotBlank(columnMapping.getHeader())) {
					rowValidationInfoList.add(columnMapping.getHeader() + COLON + dtoFieldMap.get(field));
				} else {
					int columnNum = columnMapping.getColumnIndex() + 1;
					rowValidationInfoList
							.add(MessageTools.message(ExcelConstant.Error.EXCEL_VALIDATION_COLUMN_ERROR, columnNum)
									+ dtoFieldMap.get(field));
				}
			}
		}

		return rowValidationInfoList;
	}

	private String getCellValue(XSSFWorkbook workbook, XSSFSheet sheet, int col, int row) {
		XSSFRow sheetRow = sheet.getRow(row);

		if (null == sheetRow) {
			return "";
		}

		XSSFCell cell = sheetRow.getCell(col);
		if (null == cell) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			try {
				if (DateUtil.isCellDateFormatted(cell)) {
					double val = cell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(val);
					return SDF_YYYY_MM_DD.format(date);
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					return StringUtils.trimToEmpty(cell.getStringCellValue());
				}
			} catch (Exception e) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return StringUtils.trimToEmpty(cell.getStringCellValue());
			}
		case Cell.CELL_TYPE_FORMULA:
			XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(workbook);
			CellValue cellVal = evaluator.evaluate(cell);
			switch (cellVal.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return StringUtils.trimToEmpty(cell.getStringCellValue());
			case Cell.CELL_TYPE_STRING:
				if (StringUtils.isBlank(cell.getStringCellValue())) {
					return "";
				}
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return StringUtils.trimToEmpty(cell.getStringCellValue());
			case Cell.CELL_TYPE_ERROR:
				return "";
			default:
				return StringUtils.trimToEmpty(cell.getStringCellValue());
			}
		default:
			return StringUtils.trimToEmpty(cell.getStringCellValue());
		}
	}

	private boolean isEmptyRowInSheet(String[] cellValues) {
		for (int i = 0; i < cellValues.length; i++) {
			if (StringUtils.isNotBlank(cellValues[i])) {
				return false;
			}
		}
		return true;
	}

}

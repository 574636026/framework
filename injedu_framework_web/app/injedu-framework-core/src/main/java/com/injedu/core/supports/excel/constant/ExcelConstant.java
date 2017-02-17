package com.injedu.core.supports.excel.constant;

/**
 * 
 * Excel Constant
 * 
 * @author joy.zhou
 * @date 2016年2月17日
 * @version 1.0
 *
 */
public class ExcelConstant {

	/**
	 * 
	 * Excel导入常量
	 *
	 * @author joy.zhou
	 * @date 2016年2月18日
	 * @version 1.0
	 *
	 */
	public interface Import {
		/** excel导入分隔符 */
		public static final String EXCEL_IMPORT_LINE_BREAK = ";";
		/** excel导入配置默认目录 */
		public static final String EXCEL_IMPORT_DIR = "excel/mappings/";
		/** 导入模板文件类型 */
		public static final String EXCEL_IMPORT_TEMPLATE_SUFFIX = ".json";
		/** 导入模板默认sheet名称 */
		public static final String EXCEL_IMPORT_DEFAULT_SHEET = "Sheet1";
		/** 导入成功信息 */
		public static final String EXCEL_IMPORT_SUCCESS_MESSAGE = "excel.import.message";

	}

	/**
	 *
	 * Excel错误常量
	 *
	 * @author joy.zhou
	 * @date 2016年2月17日
	 * @version 1.0
	 *
	 */
	public interface Error {

		/** Excel异常：模板名称为空 */
		final String EXCEL_TEMPLATE_NAME_EMPTY = "excel.template.empty";
		/** Excel异常：文件不存在 */
		final String EXCEL_FILE_MAPPING_EMPTY = "excel.file.empty";
		/** Excel异常：文件读取失败 */
		final String EXCEL_FILE_READ_ERROR = "excel.file.readerror";
		/** Excel异常：文件导入失败 */
		final String EXCEL_FILE_IMPORT_ERROR = "excel.file.importerror";
		/** Excel异常：校验错误 */
		final String EXCEL_VALIDATION_LIST_ERROR = "excel.validation.error";
		/** Excel异常：sheet未找到 */
		final String EXCEL_VALIDATION_SHEET_NOTFOUND = "excel.validation.sheet.notfound";
		/** Excel异常：内容为空 */
		final String EXCEL_VALIDATION_CONTENT_EMPTY = "excel.validation.content.empty";
		/** Excel异常：实体类错误 */
		final String EXCEL_VALIDATION_DOMAINCLASS_ERROR = "excel.validation.domainclass.error";
		/** Excel异常：行错误 */
		final String EXCEL_VALIDATION_ROW_ERROR = "excel.validation.row.error";
		/** Excel异常：列错误 */
		final String EXCEL_VALIDATION_COLUMN_ERROR = "excel.validation.column.error";
		/** Excel解析异常： 配置sheet错误 */
		final String EXCEL_PARSER_CONFIGFILE_ERROR = "excel.parser.conigfile.error";
		/** Excel解析异常： 实体文件未找到 */
		final String EXCEL_PARSER_DOMAINCLASS_NOTFOUND = "excel.parser.domainclass.notfound";
		/** Excel解析异常： 实体文件字段错误(可能是未找到getter、setter) */
		final String EXCEL_PARSER_DOMAINCLASS_FIELD_ERROR = "excel.parser.domainclass.fielderror";
		/** Excel解析异常： 字段映射未找到 */
		final String EXCEL_PARSER_COLUMNMAPPING_NOTFOUND = "excel.parser.columnmapping.notfound";
		/** Excel解析异常： 属性表头未找到 */
		final String EXCEL_PARSER_ATTR_HEADER_NOTFOUND = "excel.parser.attrheader.notfound";
		/** Excel解析异常： 属性索引异常 */
		final String EXCEL_PARSER_ATTR_COLUMNINDEX_NEGATIVE = "excel.parser.attrcolumnindex.negative";
		/** Excel解析异常： 属性字段为空 */
		final String EXCEL_PARSER_ATTR_FIELD_EMPTY = "excel.parser.attrfield.empty";
	}

}

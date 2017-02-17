package com.injedu.core.supports.excel.handler;

import com.injedu.core.exception.AppException;
import com.injedu.core.supports.excel.constant.ExcelConstant;
import com.injedu.core.supports.excel.dto.BaseExcelDataDto;
import com.injedu.core.supports.excel.util.FileImportUtils;

/**
 * 
 * Excel导入处理器(基类)
 *
 * @author joy.zhou
 * @date 2016年2月18日
 * @version 1.0
 *
 */
public abstract class AbstractSimpleImportHandler<T extends BaseExcelDataDto> extends BaseImportHandler<T> {

	@Override
	public Boolean doImport() throws AppException {

		String templateName = getTemplateName();

		String templatePath = getTemplateDir() + templateName + ExcelConstant.Import.EXCEL_IMPORT_TEMPLATE_SUFFIX;

		try {
			this.resultList = FileImportUtils.getImportedRecords(getFileName(), getFileStream(), templatePath,
					templateName, ExcelConstant.Import.EXCEL_IMPORT_DEFAULT_SHEET);
		} catch (Exception e) {
			this.setErrorMsg(e.getMessage());
			throw new AppException(e.getMessage(), e);
		}

		if (this.resultList == null) {
			return false;
		}

		boolean flag = execute();

		// 执行完毕,销毁
		this.resultList.clear();

		return flag;
	}

	/**
	 * 获取模板目录
	 * 
	 * @return
	 */
	protected String getTemplateDir() {
		return ExcelConstant.Import.EXCEL_IMPORT_DIR;
	}

	/**
	 * 获取模板名称
	 * 
	 * @return
	 */
	protected abstract String getTemplateName();

	/**
	 * 执行导入
	 * 
	 * @return
	 */
	public abstract boolean execute();
}

package com.injedu.core.supports.excel.dto;

import java.util.HashMap;
import java.util.Map;

import com.injedu.core.supports.excel.validator.ExcelDtoValidator;

/**
 * S
 *
 * @author joy.zhou
 * @date 2016年2月18日
 * @version 1.0
 *
 */
public class DefaultExcelDataDto extends BaseExcelDataDto implements ExcelDtoValidator {

	@Override
	public Map<String, String> validate() {
		return new HashMap<String, String>();
	}

}

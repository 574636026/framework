package com.injedu.core.exception.utils;

import java.util.Collection;

import com.injedu.core.constant.ResConst;
import com.injedu.core.exception.AppException;

/**
 * 
 * 校验工具
 *
 * @author joy.zhou
 * @date 2016年3月17日
 * @version 1.0
 *
 */
public class AppAssert {

	/**
	 * 断言数据已找到
	 * 
	 * @param object
	 *            数据对象
	 * @param id
	 *            数据标识ID
	 */
	public static void HasFount(Object object, String id) {

		if (object == null) {
			throw new AppException(ResConst.ResultCode.DATA_NOT_FOUND, ResConst.Message.DATA_NOT_FOUND, id);
		}
	}

	/**
	 * 断言数据已找到
	 * 
	 * @param object
	 *            数据对象
	 * @param id
	 *            数据标识ID
	 */
	public static void HasFount(Object[] object, String id) {

		if (object == null || object.length <= 0) {
			throw new AppException(ResConst.ResultCode.DATA_NOT_FOUND, ResConst.Message.DATA_NOT_FOUND, id);
		}
	}

	/**
	 * 断言数据已找到
	 * 
	 * @param object
	 *            数据对象
	 * @param id
	 *            数据标识ID
	 */
	public static void HasFount(Collection<?> object, String id) {

		if (object == null || object.size() <= 0) {
			throw new AppException(ResConst.ResultCode.DATA_NOT_FOUND, ResConst.Message.DATA_NOT_FOUND, id);
		}
	}

}

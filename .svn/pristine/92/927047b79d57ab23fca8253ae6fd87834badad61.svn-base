package com.injedu.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.injedu.core.constant.ResConst;
import com.injedu.core.dto.ValidateMessageDto;
import com.injedu.core.dto.wrapper.ResultWrapper;
import com.injedu.utils.fileupload.model.FileEntity;

/**
 * 
 * 基础Rest controller
 * 
 * @author joy.zhou
 * @date 2015年11月10日
 * @version 1.0
 *
 */
public class BaseRestController extends BaseController {

	/**
	 * 空值返回
	 * 
	 * @return
	 */
	protected ResultWrapper success() {

		return new ResultWrapper(ResConst.ResultCode.SUCCESS, "");
	}

	/**
	 * 返回成功消息
	 * 
	 * @param message
	 * @return
	 */
	protected ResultWrapper success(String message) {

		return new ResultWrapper(ResConst.ResultCode.SUCCESS, "", Maps.newHashMap().put("message", message));
	}

	/**
	 * 返回包装信息
	 * 
	 * @param object
	 * @return
	 */
	protected <T> ResultWrapper wrapper(T object) {
		return new ResultWrapper(ResConst.ResultCode.SUCCESS, "", object);
	}

	/**
	 * 返回失败消息
	 * 
	 * @param message
	 * @return
	 */
	protected ResultWrapper faild(String message) {

		return new ResultWrapper(ResConst.ResultCode.ERROR, message);
	}

	/**
	 * 
	 * 返回验证成功
	 * 
	 * @return
	 */
	protected ValidateMessageDto validateSuccess() {
		return new ValidateMessageDto();
	}

	/**
	 * 返回验证失败
	 * 
	 * @param msg
	 *            失败消息
	 * @return
	 */
	protected ValidateMessageDto validateFaild(String msg) {
		return new ValidateMessageDto(ValidateMessageDto.Status.faild, msg);
	}

	/**
	 * 返回验证失败
	 * 
	 * @return
	 */
	protected ValidateMessageDto validateFaild() {
		return this.validateFaild("");
	}

	/**
	 * 返回文件上传消息
	 * 
	 * @param list
	 *            文件上传列表
	 * @return
	 */
	protected Map<String, Object> uploadMessage(List<FileEntity> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploads", list);
		return map;
	}
}

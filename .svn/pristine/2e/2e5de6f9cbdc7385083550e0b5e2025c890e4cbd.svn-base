package com.injedu.mvc.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.injedu.core.domain.BaseSimpleDomain;

/**
 * 
 * 增删改查controller,继承此类则拥有crud功能
 * 
 * @author joy.zhou
 * @date 2015年12月1日
 * @version 1.0
 *
 */
public class BaseSecurityController extends BaseRestController {

	/**
	 * 
	 * 获取当前登录用户
	 * 
	 */
	protected BaseSimpleDomain getCurrentUser() {

		return (BaseSimpleDomain) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * 
	 * 获取操作人ID
	 * 
	 */
	protected Long getOperationId() {
		return getCurrentUser().getId();
	}
}

package com.injedu.mvc.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.alibaba.fastjson.JSONObject;
import com.injedu.core.constant.ResConst;
import com.injedu.core.dto.wrapper.ResultWrapper;
import com.injedu.utils.net.RequestUtils;

/**
 * 
 * 自定义访问拒绝处理(403)
 *
 * @author joy.zhou
 * @date 2015年12月11日
 * @version 1.0
 *
 */
public class AccessDecisionHandler implements AccessDeniedHandler {

	protected static Logger logger = LoggerFactory.getLogger(AccessDecisionHandler.class);

	private String accessDeniedUrl;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException, ServletException {

		if (RequestUtils.isAjax(request)) {

			try {
				response.setContentType("application/json");
				PrintWriter writer = response.getWriter();
				ResultWrapper result = new ResultWrapper();
				result.setResultCode(ResConst.ResultCode.ACCESS_DENIED);
				writer.write(JSONObject.toJSONString(result));
				writer.flush();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		} else {
			request.setAttribute("exception", ex);
			request.getRequestDispatcher(accessDeniedUrl).forward(request, response);
		}

	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

}

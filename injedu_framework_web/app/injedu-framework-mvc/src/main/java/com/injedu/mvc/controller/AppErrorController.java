package com.injedu.mvc.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import com.google.common.collect.Lists;
import com.injedu.core.constant.ResConst;
import com.injedu.core.dto.wrapper.ResultWrapper;
import com.injedu.core.exception.AppException;
import com.injedu.utils.net.RequestUtils;

/**
 * 
 * 默认错误处理
 *
 * @author joy.zhou
 * @date 2016年9月9日
 * @version 1.0
 *
 */
@ControllerAdvice
public class AppErrorController {

	private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);

	/**
	 * 处理系统内置的Exception
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ResultWrapper> exception(HttpServletRequest request, Throwable ex) {
		return handleError(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	/**
	 * 处理请求异常
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class })
	public ResponseEntity<ResultWrapper> badRequest(HttpServletRequest request, ServletException ex) {
		return handleError(request, HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(HttpStatusCodeException.class)
	public ResponseEntity<ResultWrapper> restTemplateException(HttpServletRequest request, HttpStatusCodeException ex) {
		return handleError(request, ex.getStatusCode(), ex);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResultWrapper> accessDeny(HttpServletRequest request, AccessDeniedException ex) {
		return handleError(request, HttpStatus.FORBIDDEN, ex);
	}

	// http session exception
	@ExceptionHandler(HttpSessionRequiredException.class)
	public ResponseEntity<ResultWrapper> httpsession(HttpServletRequest request, HttpSessionRequiredException ex) {
		return handleError(request, ResConst.ResultCode.NOT_LOGIN, new AppException("login.need"));
	}

	// 处理自定义Exception
	@ExceptionHandler({ AppException.class })
	public ResponseEntity<ResultWrapper> badRequest(HttpServletRequest request, AppException ex) {
		return handleError(request, ex.getCode(), ex);
	}

	protected ResponseEntity<ResultWrapper> handleError(HttpServletRequest request, HttpStatus status, Throwable ex) {
		return handleError(request, status.value(), ex);
	}

	protected ResponseEntity<ResultWrapper> handleError(HttpServletRequest request, int code, Throwable ex) {

		this.printLoggerError(request, code, ex);
		ResultWrapper result = new ResultWrapper(code, resolveExceptionMessage(ex));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(result, headers, HttpStatus.OK);
	}

	/** 忽略错误码 */
	private static final List<Integer> INGORE_ERROR_CODES = Lists.newArrayList(HttpStatus.NOT_FOUND.value(),
			ResConst.ResultCode.INSIDE_CHECK_ERROR, ResConst.ResultCode.NOT_LOGIN);

	/**
	 * 
	 * 打印异常日志
	 * 
	 * @param request
	 * @param code
	 * @param ex
	 */
	protected void printLoggerError(HttpServletRequest request, int code, Throwable ex) {

		if (INGORE_ERROR_CODES.contains(code)) {
			return;
		}
		logger.error(RequestUtils.resolveRequest(request));
		try {
			if (RequestUtils.isAjax(request)) {
				String body = StreamUtils.copyToString(request.getInputStream(),
						Charset.forName(request.getCharacterEncoding()));
				logger.error("body : [" + body + "]");
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		logger.error(ex.getMessage(), ex);

	}

	/**
	 * 
	 * 异常消息解析
	 * 
	 * @param ex
	 * @return
	 */
	protected String resolveExceptionMessage(Throwable ex) {

		String message = ex.getMessage();

		if (ex instanceof BindException) { // 解析参数绑定异常
			BindException e = ((BindException) ex);
			if (e.hasErrors()) {
				StringBuilder sb = new StringBuilder();
				for (FieldError error : e.getFieldErrors()) {
					sb.append("[").append(error.getField()).append("]").append(error.getDefaultMessage()).append(",");
				}
				sb.delete(sb.length() - 1, sb.length());
				message = sb.toString();
			}
		} else if (ex instanceof MethodArgumentNotValidException) {// 解析参数绑定异常
			MethodArgumentNotValidException e = ((MethodArgumentNotValidException) ex);
			if (e.getBindingResult().hasErrors()) {
				StringBuilder sb = new StringBuilder();
				for (FieldError error : e.getBindingResult().getFieldErrors()) {
					sb.append("[").append(error.getField()).append("]").append(error.getDefaultMessage()).append(",");
				}
				sb.delete(sb.length() - 1, sb.length());
				message = sb.toString();
			}
		}
		return message;
	}

}

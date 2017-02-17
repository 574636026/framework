package com.injedu.mvc.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 自定义Security过滤器
 *
 * @author joy.zhou
 * @date 2014年12月25日
 * @version 1.0
 *
 */
public class HttpSecurityFilter extends AbstractSecurityInterceptor implements Filter {

	private static final String FILTER_APPLIED = "__spring_security_httpSecurityFilter_filterApplied";

	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	private boolean observeOncePerRequest = true;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		logger.debug("init self filter...");

		FilterInvocation fi = new FilterInvocation(req, resp, chain);

		invoke(fi);
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {

		if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
				&& observeOncePerRequest) {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} else {

			// first time this request being called, so perform security
			// checking
			if (fi.getRequest() != null) {
				fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}

			InterceptorStatusToken token = super.beforeInvocation(fi);

			try {
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			} finally {
				super.finallyInvocation(token);
			}

			super.afterInvocation(token, null);
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	/**
	 * AccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
	 */
	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

}

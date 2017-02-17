package com.injedu.mvc.security;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * Security 访问权限验证
 *
 * @author joy.zhou
 * @date 2014年12月25日
 * @version 1.0
 *
 */
public class HttpAccessDecisionManager implements AccessDecisionManager {

	private static Logger logger = LoggerFactory.getLogger(HttpAccessDecisionManager.class);

	@Override
	public void decide(Authentication authentication, Object obj, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个角色)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			// 访问所请求资源所需要的角色
			String needPermission = configAttribute.getAttribute();
			logger.debug("需要角色:" + needPermission);
			// 用户所拥有的角色authentication
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needPermission.equals(ga.getAuthority())) {
					return;
				}
			}
		}

		throw new AccessDeniedException("needPermission..");

	}

	@Override
	public boolean supports(ConfigAttribute attrs) {
		return true;
	}

	@Override
	public boolean supports(Class<?> cls) {
		return true;
	}

}

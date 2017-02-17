package com.injedu.core.supports.cache.key;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 
 * redis key 生成方法
 *
 * @author joy.zhou
 * @date 2016年7月12日
 * @version 1.0
 *
 */
public class DefaultKeyGenerator implements KeyGenerator {
	/** 分隔符:目标、方法 */
	public static final String DELIMIT_TARGET = ":";
	/** 分隔符:参数 */
	public static final String DELIMIT_PARAMS = "_";

	@Override
	public Object generate(Object target, Method method, Object... params) {

		StringBuilder sb = new StringBuilder();
		sb.append(target.getClass().getSimpleName());
		sb.append(DELIMIT_TARGET);
		sb.append(method.getName());
		sb.append(DELIMIT_TARGET);
		sb.append(generateKey(params));

		return sb.toString();
	}

	/**
	 * Generate a key based on the specified parameters.
	 */
	public static String generateKey(Object... params) {
		if (params.length == 0) {
			return "";
		}
		return StringUtils.join(params, DELIMIT_PARAMS);
	}

}

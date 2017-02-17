package com.injedu.easycode.maintain.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.context.EasyCodeException;

/**
 * velocity辅助类
 *
 * User: liyd Date: 13-11-20 Time: 下午5:06
 */
public class VelocityUtils {

	/**
	 * 初始始化velocity引擎
	 */
	static {
		// 初始化参数
		Properties properties = new Properties();
		// 设置velocity资源加载方式为class
		properties.setProperty("resource.loader", "class");
		// 设置velocity资源加载方式为file时的处理类
		properties.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		properties.setProperty("input.encoding", "utf-8");
		properties.setProperty("output.encoding", "utf-8");
		properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
		properties.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		properties.setProperty("log4j.logger.org.apache.velocity", "error");
		try {
			Velocity.init(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析模板文件
	 * 
	 * @param template
	 * @param map
	 * @return
	 */
	public static String parseTemplate(String template, Map<String, ?> map) {

		try {
			VelocityContext context = new VelocityContext();

			for (Map.Entry<String, ?> entry : map.entrySet()) {

				String key = entry.getKey();

				if (StringUtils.startsWith(key, EasyCodeContext.CONSTANT_KEY)) {
					key = StringUtils.substring(key, EasyCodeContext.CONSTANT_KEY.length());
				}
				context.put(key, entry.getValue());
			}
			String encoding = EasyCodeContext.getConstant("encoding");
			StringWriter stringWriter = new StringWriter();
			Velocity.mergeTemplate(template, (encoding == null ? "UTF-8" : encoding), context, stringWriter);

			return stringWriter.toString();

		} catch (Exception e) {
			throw new EasyCodeException("velocity解析模板失败");
		}
	}

	/**
	 * 解析字符串
	 * 
	 * @param content
	 * @param context
	 * @return
	 */
	public static String parseString(String content, VelocityContext context) {

		StringWriter stringWriter = new StringWriter();
		try {
			Velocity.evaluate(context, stringWriter, "easyCode", content);
		} catch (IOException e) {

		}
		return stringWriter.toString();
	}

	/**
	 * 获取velocity context，包含常量
	 * 
	 * @return
	 */
	public static VelocityContext getVelocityContext() {

		VelocityContext context = new VelocityContext();

		for (Map.Entry<String, ?> entry : EasyCodeContext.getAllConstant().entrySet()) {

			String key = entry.getKey();

			context.put(key, entry.getValue());
		}

		return context;
	}

}

package com.injedu.easycode.maintain.config;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.injedu.easycode.maintain.constant.ContextConstant;
import com.injedu.easycode.maintain.generator.EasyCodeGenerator;
import com.injedu.easycode.maintain.plugins.EasyCodePlugin;
import com.injedu.easycode.maintain.utils.PropertyUtils;

/**
 * 代码生成任务 . User: liyd Date: 13-11-28 Time: 下午5:26
 */
public class Task {

	/** 任务名称 */
	private String name;

	/** 任务处理class */
	private String clazz;

	/** 任务处理class的实例 */
	private EasyCodeGenerator classInstance;
	/** 前置插件列表 */
	private Map<String, EasyCodePlugin> beforePluginMap;
	/** 后置插件列表 */
	private Map<String, EasyCodePlugin> afterPluginMap;

	/** 属性参数 */
	private Map<String, Property> properties;

	/**
	 * 获取任务生成包名
	 * 
	 * @return
	 */
	public String getPackageName() {

		return this.getPropertyValue("package");
	}

	/**
	 * 获取生成目录
	 * 
	 * @return
	 */
	public String getSrcDir() {
		return this.getPropertyValue("srcdir");
	}

	/**
	 * 获取模块目录
	 * 
	 * @return
	 */
	public String getModuleDir() {
		return this.getPropertyValue("moduledir");
	}

	/**
	 * 获取模板
	 * 
	 * @return
	 */
	public String getTemplate() {
		String template = this.getPropertyValue("template");
		if (StringUtils.equals(template, this.name)) {
			template += ".vm";
		}
		return template;
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return
	 */
	private String getPropertyValue(String key) {
		Property property = this.getProperties().get(key);
		if (property == null) {
			return this.getName();
		}
		return property.getValue();
	}

	/**
	 * 获取生成的类名称，含包名
	 * 
	 * @param tableName
	 * @return
	 */
	public String getGeneratedReferenceClassName(String tableName) {

		String packageName = PropertyUtils.getValue(properties
				.get(ContextConstant.PACKAGE));
		String beginFix = PropertyUtils.getValue(properties
				.get(ContextConstant.BEGIN_FIX));
		String endFix = PropertyUtils.getValue(properties
				.get(ContextConstant.END_FIX));
		// String camelTableName = NameUtils.getCamelName(tableName);
		// camelTableName = NameUtils.getFirstUpperName(camelTableName);

		StringBuilder sb = new StringBuilder();
		sb.append(packageName);
		sb.append(".");
		sb.append(beginFix);
		// sb.append(camelTableName);
		sb.append(tableName);
		sb.append(endFix);
		return sb.toString();
	}

	/**
	 * 获取生成文件名，含包路径
	 * 
	 * @param tableName
	 * @return
	 */
	public String getGeneratedFileName(String tableName) {
		String generatedReferenceClassName = this
				.getGeneratedReferenceClassName(tableName);
		generatedReferenceClassName = StringUtils.replace(
				generatedReferenceClassName, ".", "/");
		String suffix = PropertyUtils.getValue(properties
				.get(ContextConstant.SUFFIX));
		return generatedReferenceClassName + suffix;
	}

	/**
	 * 获取生成的类名称，不含包名
	 * 
	 * @param tableName
	 * @return
	 */
	public String getGeneratedShotClassName(String tableName) {
		String generatedReferenceClassName = this
				.getGeneratedReferenceClassName(tableName);
		int index = StringUtils.lastIndexOf(generatedReferenceClassName, ".");
		return StringUtils.substring(generatedReferenceClassName, index + 1);
	}

	/**
	 * 获取生成的类名称，不包含包名与前缀
	 * 
	 * @param tableName
	 * @return
	 */
	public String getGeneratedShotClassNameNoBeingFix(String tableName) {
		String generatedReferenceClassName = this
				.getGeneratedShotClassName(tableName);
		String beginFix = PropertyUtils.getValue(properties
				.get(ContextConstant.BEGIN_FIX));
		return StringUtils.isNotBlank(beginFix) ? generatedReferenceClassName
				.substring(beginFix.length()) : generatedReferenceClassName;
	}

	public Map<String, EasyCodePlugin> getBeforePluginMap() {
		return beforePluginMap;
	}

	public void setBeforePluginMap(Map<String, EasyCodePlugin> beforePluginMap) {
		this.beforePluginMap = beforePluginMap;
	}

	public Map<String, EasyCodePlugin> getAfterPluginMap() {
		return afterPluginMap;
	}

	public void setAfterPluginMap(Map<String, EasyCodePlugin> afterPluginMap) {
		this.afterPluginMap = afterPluginMap;
	}

	public EasyCodeGenerator getClassInstance() {
		return classInstance;
	}

	public void setClassInstance(EasyCodeGenerator classInstance) {
		this.classInstance = classInstance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Map<String, Property> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
	}
}

package com.injedu.easycode.maintain.generator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.maintain.config.Column;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;
import com.injedu.easycode.maintain.constant.ContextConstant;
import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.plugins.EasyCodePlugin;
import com.injedu.easycode.maintain.utils.FileUtils;
import com.injedu.easycode.maintain.utils.VelocityUtils;
import com.injedu.easycode.maintain.utils.XmlMapperParser;

/**
 * 代码生成接口抽象实现
 * 
 * User: liyd Date: 13-12-6 Time: 下午4:56
 */
public abstract class AbstractEasyCodeGenerator implements EasyCodeGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractEasyCodeGenerator.class);

	/**
	 * 代码生成方法
	 *
	 * @param table
	 *            the table
	 * @param task
	 *            the task
	 * @param context
	 *            the context
	 */
	@Override
	public void doGenerate(Table table, Task task, VelocityContext context) {

		// 获取生成文件路径
		String filePath = getFilePath(table, task);
		StringBuilder sbTemp = new StringBuilder(FileUtils.getTemplate(task.getTemplate()));
		// 运行前置插件
		this.executePlugin(table, task, context, sbTemp, ContextConstant.PLUGIN_TYPE_BEFORE);
		// 执行代码生成器
		this.generate(table, task, context, sbTemp);
		// 是否覆盖原有代码
		if (!Boolean.parseBoolean(EasyCodeContext.getConstant("overrideCode")) && (new File(filePath).exists())) {
			LOG.warn("已存在文件：" + filePath);
			return;
		}
		// 模板解析
		String template = VelocityUtils.parseString(sbTemp.toString(), context);
		// 写入文件
		FileUtils.writeFile(filePath, template);
		// 运行后置插件
		this.executePlugin(table, task, context, sbTemp, ContextConstant.PLUGIN_TYPE_AFTER);
	}

	@Override
	public void updateXmlMapper(Table table, Task task, VelocityContext context) {
		// 获取生成文件路径
		String filePath = getFilePath(table, task);

		FileInputStream source = null;
		try {
			source = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			LOG.warn("文件不存在：" + filePath);
			return;
		}

		StringBuilder sbTemp = new StringBuilder(FileUtils.getTemplate(task.getTemplate()));
		// 执行代码生成器
		this.generate(table, task, context, sbTemp);
		// 模板解析
		String template = VelocityUtils.parseString(sbTemp.toString(), context);
		// 替换文件
		FileUtils.writeFile(filePath, XmlMapperParser.parseReplaceXml(FileUtils.readInStream(source), template));
	}

	/**
	 * 获取生成文件路径
	 * 
	 * @param table
	 * @param task
	 * @return
	 */
	private String getFilePath(Table table, Task task) {

		String moduleDir = task.getModuleDir();
		moduleDir += "/";
		String srcDir = task.getSrcDir();
		srcDir = StringUtils.isBlank(srcDir) ? "" : srcDir + "/";
		String packageFileDir = task.getGeneratedFileName(table.getAlais());
		return moduleDir + srcDir + packageFileDir;
	}

	/**
	 * 运行插件
	 * 
	 * @param table
	 * @param task
	 * @param context
	 * @param sbTemp
	 */
	private void executePlugin(Table table, Task task, VelocityContext context, StringBuilder sbTemp,
			String pluginType) {

		Map<String, EasyCodePlugin> pluginMap = null;

		if (StringUtils.equalsIgnoreCase(pluginType, ContextConstant.PLUGIN_TYPE_BEFORE)) {
			pluginMap = task.getBeforePluginMap();
		} else if (StringUtils.equalsIgnoreCase(pluginType, ContextConstant.PLUGIN_TYPE_AFTER)) {
			pluginMap = task.getAfterPluginMap();
		}

		if (pluginMap == null || pluginMap.size() == 0) {
			return;
		}

		for (EasyCodePlugin easyCodePlugin : pluginMap.values()) {
			easyCodePlugin.execute(table, task, sbTemp, context);
		}
	}

	/**
	 * 添加字段类型需要导入的包
	 *
	 * @param columns
	 *            the columns
	 * @return the columns import class
	 */
	protected Set<String> getColumnsImportClass(List<Column> columns) {

		Set<String> importSet = new HashSet<String>();

		List<String> baseColumnList = EasyCodeContext.getAllBaseColumnList();

		for (Column column : columns) {

			if (baseColumnList.size() > 0 && baseColumnList.contains(column.getName())) {
				continue;
			}

			if (EasyCodeContext.getDataConvertType(column.getDbType()) != null) {
				addImportClass(importSet, EasyCodeContext.getDataConvertType(column.getDbType()).getJavaClass());
			} else {
				addImportClass(importSet, column.getJavaClass());
			}
		}
		return importSet;
	}

	/**
	 * 添加引用的类
	 *
	 * @param importSet
	 * @param className
	 */
	private void addImportClass(Set<String> importSet, String className) {

		if (StringUtils.startsWith(className, "java.lang")) {
			return;
		}
		importSet.add(className);
	}

	/**
	 * 代码生成方法
	 *
	 * @param table
	 *            the table
	 * @param task
	 *            the task
	 * @param context
	 *            the context
	 * @param template
	 *            the template
	 */
	public abstract void generate(Table table, Task task, VelocityContext context, StringBuilder template);

}

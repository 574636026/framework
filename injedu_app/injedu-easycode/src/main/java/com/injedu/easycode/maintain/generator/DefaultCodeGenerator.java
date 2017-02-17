package com.injedu.easycode.maintain.generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.injedu.easycode.maintain.config.Column;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;
import com.injedu.easycode.maintain.constant.ContextConstant;
import com.injedu.easycode.maintain.constant.ImportClass;
import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.utils.NameUtils;

/**
 * 默认代码生成实现类
 * 
 * User: liyd Date: 13-12-16 Time: 下午4:28
 */
public class DefaultCodeGenerator extends AbstractEasyCodeGenerator {

	/**
	 * 代码生成方法
	 *
	 * @param table
	 * @param task
	 * @param context
	 * @param template
	 */
	@Override
	public void generate(Table table, Task task, VelocityContext context, StringBuilder template) {

		// 表排序处理
		for (Column column : table.getColumns()) {
			if ("sort".equals(column.getCamelName())) {
				table.setSort(true);
				EasyCodeContext.addBaseColumn(column.getCamelName());
			}
		}

		context.put("baseColumns", EasyCodeContext.getAllBaseColumnList());
		context.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		context.put("table", table);
		context.put("task", task);
		context.put("packageName", task.getPackageName());
		String longClassKey = task.getName() + "GeneratedLongClassName";
		String shortClassKey = task.getName() + "GeneratedShotClassName";
		String firstLowerClassKey = task.getName() + "FirstLowerGeneratedClassName";
		String firstLowerNoBeginClassKey = task.getName() + "FirstLowerNoBeginGeneratedClassName";
		String tableName = table.getAlais();
		String generatedShotClassName = task.getGeneratedShotClassName(tableName);

		context.put(longClassKey, task.getGeneratedReferenceClassName(tableName));
		context.put(shortClassKey, generatedShotClassName);
		context.put(firstLowerClassKey, NameUtils.getFirstLowerName(generatedShotClassName));
		context.put(firstLowerNoBeginClassKey,
				NameUtils.getFirstLowerName(task.getGeneratedShotClassNameNoBeingFix(tableName)));

		// 引入包
		Set<String> importSet = new HashSet<String>();
		// 获得生成源码
		String sourceCode = template.toString();
		// 引入常用包
		importCommonLib(importSet, sourceCode, task, context);
		// 引入依赖实体包
		importEntityLib(importSet, sourceCode, task, context);
		// 引入实体类型包
		if (StringUtils.equalsIgnoreCase("domain", task.getName())
				|| StringUtils.equalsIgnoreCase("dto", task.getName())) {
			importSet.addAll(super.getColumnsImportClass(table.getColumns()));
		}

		if (importSet.size() > 0) {
			context.put("importList", importSet);
		}

	}

	/**
	 * 引入常用包
	 * 
	 * @param importSet
	 *            引入包
	 * @param sourceCode
	 *            源码
	 */
	private void importCommonLib(Set<String> importSet, String sourceCode, Task task, VelocityContext context) {

		if (ImportClass.imports == null || ImportClass.imports.isEmpty()) {
			return;
		}

		for (Entry<String, String> entry : ImportClass.imports.entrySet()) {

			if (StringUtils.indexOf(sourceCode, entry.getKey()) != -1
					&& StringUtils.indexOf(sourceCode, entry.getValue()) == -1) {
				importSet.add(entry.getValue());
			}
		}

	}

	/**
	 * 引入依赖实体包
	 * 
	 * @param importSet
	 *            引入包
	 * @param sourceCode
	 *            源码
	 */
	private void importEntityLib(Set<String> importSet, String sourceCode, Task task, VelocityContext context) {

		@SuppressWarnings("unchecked")
		Queue<String> allTasks = (Queue<String>) context.get(ContextConstant.ALL_TASKS);

		for (String name : allTasks) {

			String shotClassNameKey = name + "GeneratedShotClassName";
			String longClassNameKey = name + "GeneratedLongClassName";

			if (!StringUtils.equals(task.getName(), name)
					&& StringUtils.indexOf(sourceCode, "${" + shotClassNameKey + "}") != -1
					&& StringUtils.indexOf(sourceCode, "${" + longClassNameKey + "}") == -1) {

				Object generatedLongClassName = context.get(longClassNameKey);
				importSet.add(generatedLongClassName == null ? "" : generatedLongClassName.toString());
			}

		}

	}

}

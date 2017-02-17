package com.injedu.easycode.maintain.generator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;
import com.injedu.easycode.maintain.constant.ContextConstant;
import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.database.DatabaseProvider;
import com.injedu.easycode.maintain.database.DatabaseProviderFactory;
import com.injedu.easycode.maintain.utils.DBUtils;
import com.injedu.easycode.maintain.utils.VelocityUtils;

/**
 * 代码生成业务组织者
 * 
 * User: liyd Date: 13-12-6 Time: 下午4:01
 */
public class GenerationOrganizer {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(GenerationOrganizer.class);

	/**
	 * 代码生成
	 */
	public void codeGenerate() {

		// 所有表配置信息
		Map<String, Table> tableMap = EasyCodeContext.getAllTable();
		// 所有的任务配置信息
		Map<String, Task> tasks = EasyCodeContext.getAllTask();
		// 默认任务配置信息
		Queue<String> allTasks = new LinkedList<String>(tasks.keySet());

		DBUtils.createConnection();

		for (Map.Entry<String, Table> entry : tableMap.entrySet()) {

			Table table = entry.getValue();
			if (CollectionUtils.isEmpty(table.getColumns())) {
				DatabaseProvider provider = DatabaseProviderFactory.buildProvider();
				Table metaData = provider.getTableMetaData(table.getName());
				if (StringUtils.isBlank(table.getDesc())) {
					table.setDesc(metaData.getDesc());
				}
				table.setColumns(metaData.getColumns());
			}

			// 获取包含了所有常量的velocityContext
			VelocityContext context = VelocityUtils.getVelocityContext();
			context.put(ContextConstant.ALL_TASKS, allTasks);
			// 表的任务
			Queue<String> tableTasks = table.getTasks();

			// 如果没有配置tasks,则按照所有的任务配置
			// 若table不配置任意task请添加空的tasks结点
			if (tableTasks == null) {
				tableTasks = new LinkedList<String>(allTasks);
			}

			String tableTask;

			while ((tableTask = tableTasks.poll()) != null) {
				Task task = tasks.get(tableTask);
				if (task == null) {
					LOG.info("不存在配置任务{}", tableTask);
					continue;
				}
				EasyCodeGenerator codeGenerator = task.getClassInstance();
				codeGenerator.doGenerate(table, task, context);
			}

		}
		DBUtils.closeConnection();

	}

	/**
	 * 
	 * 更新实体
	 * 
	 * 1. mybatis xml mapper
	 * (resultMap节点、insert(id="save")节点、update(id="update")节点、sql(id="orderInclude")节点)
	 * 
	 * 2. domain
	 * 
	 */
	public void updateEntity() {

		// 所有表配置信息
		Map<String, Table> tableMap = EasyCodeContext.getAllTable();
		// 所有的任务配置信息
		Map<String, Task> tasks = EasyCodeContext.getAllTask();
		// 默认任务配置信息
		Queue<String> allTasks = new LinkedList<String>();
		for (String task : tasks.keySet()) {
			if ("entity".equals(task) || "xmlMapper".equals(task)) {
				allTasks.add(task);
			}
		}
		DBUtils.createConnection();
		for (Map.Entry<String, Table> entry : tableMap.entrySet()) {

			Table table = entry.getValue();
			if (CollectionUtils.isEmpty(table.getColumns())) {
				DatabaseProvider provider = DatabaseProviderFactory.buildProvider();
				Table metaData = provider.getTableMetaData(table.getName());
				if (StringUtils.isBlank(table.getDesc())) {
					table.setDesc(metaData.getDesc());
				}
				table.setColumns(metaData.getColumns());
			}
			// 获取包含了所有常量的velocityContext
			VelocityContext context = VelocityUtils.getVelocityContext();
			context.put(ContextConstant.ALL_TASKS, allTasks);
			// 表的任务
			Queue<String> tableTasks = new LinkedList<String>(allTasks);

			String tableTask;

			while ((tableTask = tableTasks.poll()) != null) {
				Task task = tasks.get(tableTask);
				if (task == null) {
					LOG.info("不存在配置任务{}", tableTask);
					continue;
				}
				EasyCodeGenerator codeGenerator = task.getClassInstance();
				if ("xmlMapper".equals(tableTask)) {
					codeGenerator.updateXmlMapper(table, task, context);
				} else {
					codeGenerator.doGenerate(table, task, context);
				}

			}

		}
		DBUtils.closeConnection();

	}

}

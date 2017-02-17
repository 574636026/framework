package com.injedu.easycode.maintain.generator;

import org.apache.velocity.VelocityContext;

import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;

/**
 * 代码生成接口
 *
 * User: liyd Date: 13-11-28 Time: 下午5:35
 */
public interface EasyCodeGenerator {

	/**
	 * 代码生成方法
	 * 
	 * @param table
	 *            表格数据
	 * @param task
	 *            任务配置
	 * @param context
	 *            velocity 上下文
	 */
	public void doGenerate(Table table, Task task, VelocityContext context);

	/**
	 * 更新 xml mapper
	 * 
	 * @param table
	 * @param task
	 * @param context
	 */
	public void updateXmlMapper(Table table, Task task, VelocityContext context);
}

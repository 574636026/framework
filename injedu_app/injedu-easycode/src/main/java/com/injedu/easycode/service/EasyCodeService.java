package com.injedu.easycode.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.gui.model.DataModel;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.generator.GenerationOrganizer;
import com.injedu.easycode.maintain.utils.XmlParser;

/**
 *
 * @author joy.zhou
 * @date 2016年2月22日
 * @version 1.0
 *
 */
public class EasyCodeService {

	private static final Logger LOG = LoggerFactory.getLogger(GenerationOrganizer.class);

	protected GenerationOrganizer generation = new GenerationOrganizer();

	static final String XML_PATH = "default.xml";

	private void parseConfig(DataModel dataModel) {

		EasyCodeContext.clear();

		if (dataModel != null) {
			// 配置数据库
			EasyCodeContext.addJdbcConfig("dialect", dataModel.getDbType());
			if ("mysql".equals(dataModel.getDbType())) {
				EasyCodeContext.addJdbcConfig("driverClassName", "com.mysql.jdbc.Driver");
			}
			EasyCodeContext.addJdbcConfig("url", dataModel.getDburl());
			EasyCodeContext.addJdbcConfig("username", dataModel.getDbuser());
			EasyCodeContext.addJdbcConfig("password", dataModel.getDbpwd());

			// 配置常量信息
			Map<String, String> constantParams = new HashMap<String, String>();
			constantParams.put("overrideCode", dataModel.isOverride().toString());
			constantParams.put("targetDir", dataModel.getTargetPath());
			constantParams.put("srcDir", dataModel.getSrcmainjava());
			constantParams.put("srcResourse", dataModel.getSrcmainresourse());
			constantParams.put("testSrcDir", dataModel.getSrctestjava());
			constantParams.put("testSrcResourse", dataModel.getSrctestresourse());
			constantParams.put("basePackage", dataModel.getBasePackage());
			constantParams.put("modelDir", "");
			EasyCodeContext.addConstant(constantParams);

			// 配置表信息
			Table table = new Table();
			table.setName(dataModel.getTable());
			if (StringUtils.isNotBlank(dataModel.getAlais())) {
				table.setAlais(dataModel.getAlais());
			}
			if (StringUtils.isNotBlank(dataModel.getDesc())) {
				table.setDesc(dataModel.getDesc());
			}

			if (dataModel.getTasks() != null && dataModel.getTasks().contains("domain")) {
				Queue<String> tasks = new LinkedList<String>();
				tasks.add("entity");
				for (String task : dataModel.getTasks()) {
					tasks.add(task);
				}
				dataModel.setTasks(tasks);
			}
			table.setTasks(dataModel.getTasks());
			EasyCodeContext.addTable(dataModel.getTable(), table);
		}

		// 解析配置文件
		XmlParser.parseConfigXml(XML_PATH);
	}

	/**
	 * 生成代码
	 */
	public void codeGenerate() {

		try {

			parseConfig(null);
			// 生成代码
			generation.codeGenerate();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

	/**
	 * 生成代码
	 * 
	 * @param dataModel
	 */
	public void codeGenerate(DataModel dataModel) {
		try {

			// 解析配置文件
			parseConfig(dataModel);
			// 生成代码
			generation.codeGenerate();

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

	/**
	 * 更新实体
	 */
	public void updateEntity() {
		try {
			parseConfig(null);
			generation.updateEntity();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

	/**
	 * 更新实体
	 */
	public void updateEntity(DataModel dataModel) {
		try {
			parseConfig(dataModel);
			generation.updateEntity();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}

package com.injedu.easycode.maintain.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.injedu.easycode.maintain.config.Column;
import com.injedu.easycode.maintain.config.ConvertType;
import com.injedu.easycode.maintain.config.Property;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;
import com.injedu.easycode.maintain.constant.ContextConstant;
import com.injedu.easycode.maintain.constant.XmlTag;
import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.context.EasyCodeException;
import com.injedu.easycode.maintain.plugins.EasyCodePlugin;

/**
 * XML配置文件解析类
 *
 * User: liyd Date: 13-11-20 Time: 下午2:53
 */
public class XmlParser {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(XmlParser.class);

	/** 配置文件名 */
	private static final String CONFIG_XML = "easyCode.xml";

	/** 文档构建对象 */
	private static DocumentBuilder documentBuilder;

	/**
	 * 获取doc builder
	 */
	private synchronized static DocumentBuilder getDocBuilder() {

		try {

			if (documentBuilder == null) {
				// 创建xml解析doc对象
				documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			}

			return documentBuilder;
		} catch (ParserConfigurationException e) {
			LOG.error("创建xml解析对象失败", e);
			throw new EasyCodeException("创建xml解析对象失败");
		}
	}

	/**
	 * 解析配置文件
	 *
	 * @param configFile
	 */
	public static void parseConfigXml(String configFile) {

		LOG.info("开始解析配置文件{}", configFile);

		Document doc;
		try {
			// 创建xml解析doc对象
			doc = getDocBuilder().parse(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile));

			// 解析常量
			Map<String, String> constantMap = parseConstant(doc);
			EasyCodeContext.addConstant(constantMap);

			// 解析配置文件中的常量使用
			String context = VelocityUtils.parseTemplate(configFile, EasyCodeContext.getAllConstant());

			// 重新构建处理过的配置文件doc对象
			doc = getDocBuilder().parse(new ByteArrayInputStream(context.getBytes("UTF-8")));

			NodeList childNodes = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < childNodes.getLength(); i++) {

				Node node = childNodes.item(i);

				if (node.getNodeType() == Node.TEXT_NODE) {
					continue;
				}

				if (StringUtils.equalsIgnoreCase(node.getNodeName(), XmlTag.JDBC_TAG)) {
					// 解析数据库配置
					String[] strings = parseJdbcConfig(node);
					EasyCodeContext.addJdbcConfig(strings[0], strings[1]);
				} else if (StringUtils.equalsIgnoreCase(node.getNodeName(), XmlTag.CONVERT_TAG)) {
					// 解析数据转换配置
					String[] strings = parseConvertConfig(node);
					ConvertType convertType = new ConvertType();
					convertType.setDbType(strings[0]);
					convertType.setJdbcType(strings[1]);
					convertType.setJavaClass(strings[2]);
					EasyCodeContext.addDataConvertType(strings[0], convertType);
				} else if (StringUtils.equalsIgnoreCase(node.getNodeName(), XmlTag.INCLUDE_TAG)) {
					// 解析include标签
					parseIncludeFile(node);
				} else if (StringUtils.equalsIgnoreCase(node.getNodeName(), XmlTag.TABLE_TAG)) {
					// 解析表配置
					Table table = parseTableConfig(node);
					EasyCodeContext.addTable(table.getName(), table);
				} else if (StringUtils.equalsIgnoreCase(node.getNodeName(), XmlTag.TASKS_TAG)) {

					// 解析任务配置
					Map<String, Task> taskMap = parseTaskConfig(node);
					EasyCodeContext.addTask(taskMap);
				} else if (StringUtils.equalsIgnoreCase(node.getNodeName(), XmlTag.BASECOLUMNS_TAG)) {
					// 解析实体公用字段
					parseBaseColumnsCase(node);
				}

			}

		} catch (Exception e) {
			LOG.info("配置文件解析失败", e);
			throw new EasyCodeException("配置文件解析失败");
		}
	}

	/**
	 * 解析配置文件
	 */
	public static void parseConfigXml() {
		parseConfigXml(CONFIG_XML);
	}

	/**
	 * 解析任务配置
	 *
	 * @param node
	 *            the node
	 * @return the map
	 */
	private static Map<String, Task> parseTaskConfig(Node node) {

		NodeList taskList = node.getChildNodes();

		if (taskList == null || taskList.getLength() == 0) {
			LOG.info("没有定义代码生成任务");
			throw new EasyCodeException("没有定义代码生成任务");
		}
		Map<String, Task> taskMap = new HashMap<String, Task>();
		for (int i = 0; i < taskList.getLength(); i++) {

			Node taskNode = taskList.item(i);

			if (taskNode.getNodeType() == Node.TEXT_NODE) {
				continue;
			}

			NamedNodeMap namedNodeMap = taskNode.getAttributes();

			String taskName = namedNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

			String clazz = namedNodeMap.getNamedItem(XmlTag.CLASS_ATTR).getNodeValue();

			Task task = new Task();
			task.setName(taskName);
			task.setClazz(clazz);
			task.setClassInstance(ClassUtils.getGeneratorInstance(clazz));

			// 解析子标签配置信息
			parseChildConfig(taskNode, task);
			taskMap.put(taskName, task);
		}

		return taskMap;
	}

	/**
	 * 解析公用实体字段
	 * 
	 * @param node
	 */
	private static void parseBaseColumnsCase(Node node) {

		NodeList columnList = node.getChildNodes();
		if (columnList == null || columnList.getLength() == 0) {
			LOG.info("公用实体字段");
			return;
		}

		for (int i = 0; i < columnList.getLength(); i++) {

			Node columnNode = columnList.item(i);
			if (columnNode.getNodeType() == Node.TEXT_NODE) {
				continue;
			}

			NamedNodeMap namedNodeMap = columnNode.getAttributes();

			String columnName = namedNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

			EasyCodeContext.addBaseColumn(columnName);
		}

	}

	/**
	 * 解析属性
	 *
	 * @param node
	 *            the node
	 * @param task
	 *            the task
	 */
	private static void parseChildConfig(Node node, Task task) {

		NodeList nodeList = node.getChildNodes();

		Map<String, Property> propertyMap = new HashMap<String, Property>();
		Map<String, EasyCodePlugin> beforePluginMap = new HashMap<String, EasyCodePlugin>();
		Map<String, EasyCodePlugin> afterPluginMap = new HashMap<String, EasyCodePlugin>();
		task.setProperties(propertyMap);
		task.setBeforePluginMap(beforePluginMap);
		task.setAfterPluginMap(afterPluginMap);
		if (nodeList == null || nodeList.getLength() == 0) {
			return;
		}

		// 处理属性配置信息
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node childNode = nodeList.item(i);

			if (childNode.getNodeType() == Node.TEXT_NODE || childNode.getNodeType() == Node.COMMENT_NODE) {
				continue;
			}

			NamedNodeMap propertyNodeMap = childNode.getAttributes();

			// 属性名称
			String propertyName = propertyNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

			// 属性值
			String propertyValue = propertyNodeMap.getNamedItem(XmlTag.VALUE_ATTR).getNodeValue();

			// 类型
			Node typeNode = propertyNodeMap.getNamedItem(XmlTag.TYPE_ATTR);
			String propertyType = ContextConstant.PLUGIN_TYPE_BEFORE;
			if (typeNode != null) {
				propertyType = typeNode.getNodeValue();
			}

			if (StringUtils.equalsIgnoreCase(childNode.getNodeName(), XmlTag.PLUGIN_TAG)) {
				// 插件对象
				EasyCodePlugin pluginInstance = ClassUtils.getPluginInstance(propertyValue);

				if (StringUtils.equalsIgnoreCase(propertyType, ContextConstant.PLUGIN_TYPE_BEFORE)) {
					beforePluginMap.put(propertyName, pluginInstance);
				} else if (StringUtils.equalsIgnoreCase(propertyType, ContextConstant.PLUGIN_TYPE_AFTER)) {
					afterPluginMap.put(propertyName, pluginInstance);
				}

			} else if (StringUtils.equalsIgnoreCase(childNode.getNodeName(), XmlTag.PROPERTY_TAG)) {
				// 属性对象
				Property property = new Property();
				property.setName(propertyName);
				property.setValue(propertyValue);
				propertyMap.put(propertyName, property);
			}
		}
	}

	/**
	 * 解析表配置
	 *
	 * @param node
	 *            the node
	 * @return the table
	 */
	private static Table parseTableConfig(Node node) {

		NamedNodeMap tableNodeMap = node.getAttributes();

		// 表名称
		String tableName = tableNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

		if (StringUtils.isBlank(tableName)) {
			LOG.info("没有指定表名");
			throw new EasyCodeException("没有指定表名");
		}

		Node descNode = tableNodeMap.getNamedItem(XmlTag.DESC_ATTR);

		Node alaisNode = tableNodeMap.getNamedItem(XmlTag.ALAIS_ATTR);

		Table table = new Table();
		table.setName(tableName);

		if (descNode != null) {
			table.setDesc(descNode.getNodeValue());
		}

		if (alaisNode != null) {
			table.setAlais(alaisNode.getNodeValue());
		}

		// 解析表的子属性配置信息
		parseTableChildConfig(node, table);

		return table;
	}

	/**
	 * 解析表的子属性配置信息
	 * 
	 * @param tableNode
	 * @param table
	 */
	private static void parseTableChildConfig(Node tableNode, Table table) {

		// 表的子标签列表
		NodeList childNodes = tableNode.getChildNodes();

		if (childNodes == null || childNodes.getLength() == 0) {
			LOG.info("没有配置表的具体生成信息，按默认生成");
			return;
		}

		for (int i = 0; i < childNodes.getLength(); i++) {

			// 子节点
			Node node = childNodes.item(i);

			String nodeName = node.getNodeName();

			if (StringUtils.equalsIgnoreCase(XmlTag.COLUMNS_TAG, nodeName)) {

				parseColumns(node, table);

			} else if (StringUtils.equalsIgnoreCase(XmlTag.TASKS_TAG, nodeName)) {

				parseTasks(node, table);
			}

		}
	}

	/**
	 * 解析表的tasks
	 * 
	 * @param tasksNode
	 * @param table
	 */
	private static void parseTasks(Node tasksNode, Table table) {

		// task子标签
		NodeList taskNodes = tasksNode.getChildNodes();

		if (taskNodes == null || taskNodes.getLength() == 0) {
			LOG.info("没有配置表的任务信息");
			return;
		}

		// 初始化任务
		table.setTasks(new LinkedList<String>());

		for (int i = 0; i < taskNodes.getLength(); i++) {

			// 节点
			Node node = taskNodes.item(i);

			if (node.getNodeType() == Node.TEXT_NODE) {
				continue;
			}
			NamedNodeMap columnNodeMap = node.getAttributes();

			if (columnNodeMap == null) {
				continue;
			}
			String taskName = columnNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

			if (StringUtils.equalsIgnoreCase(XmlTag.TASK_TAG, node.getNodeName())) {

				table.addTask(taskName);
			}
		}
	}

	/**
	 * 解析表的column
	 *
	 * @param columnsNode
	 * @param table
	 */
	private static void parseColumns(Node columnsNode, Table table) {

		// column子标签
		NodeList columnNodes = columnsNode.getChildNodes();

		if (columnNodes == null || columnNodes.getLength() == 0) {
			LOG.info("没有配置表的列信息，按数据库生成");
			return;
		}

		// 初始化子column
		table.setColumns(new ArrayList<Column>());

		for (int i = 0; i < columnNodes.getLength(); i++) {

			// column节点
			Node columnNode = columnNodes.item(i);

			if (columnNode.getNodeType() == Node.TEXT_NODE) {
				continue;
			}

		}
	}

	/**
	 * 解析数据库配置
	 *
	 * @param node
	 *            the node
	 */
	private static String[] parseJdbcConfig(Node node) {

		NamedNodeMap dbNodeMap = node.getAttributes();

		String name = dbNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

		String value = dbNodeMap.getNamedItem(XmlTag.VALUE_ATTR).getNodeValue();

		return new String[] { name, value };
	}

	/**
	 * 解析数据转换配置
	 * 
	 * @param node
	 * @return
	 */
	private static String[] parseConvertConfig(Node node) {

		NamedNodeMap dbNodeMap = node.getAttributes();

		String dbType = dbNodeMap.getNamedItem(XmlTag.DB_TYPE_ATTR).getNodeValue();

		String jdbcType = dbNodeMap.getNamedItem(XmlTag.JDBC_TYPE_ATTR).getNodeValue();

		String javaType = dbNodeMap.getNamedItem(XmlTag.JAVA_TYPE_ATTR).getNodeValue();

		return new String[] { dbType, jdbcType, javaType };
	}

	/**
	 * 解析常量配置
	 *
	 * @param doc
	 */
	private static Map<String, String> parseConstant(Document doc) {

		Map<String, String> constantMap = new HashMap<String, String>();
		NodeList jdbcConfigList = doc.getElementsByTagName(XmlTag.CONSTANT_TAG);

		if (jdbcConfigList == null || jdbcConfigList.getLength() == 0) {
			return constantMap;
		}

		for (int i = 0; i < jdbcConfigList.getLength(); i++) {

			Node dbNode = jdbcConfigList.item(i);

			NamedNodeMap dbNodeMap = dbNode.getAttributes();

			String name = dbNodeMap.getNamedItem(XmlTag.NAME_ATTR).getNodeValue();

			String value = dbNodeMap.getNamedItem(XmlTag.VALUE_ATTR).getNodeValue();

			constantMap.put(name, value);
		}
		return constantMap;
	}

	/**
	 * 解析include的xml
	 *
	 * @param node
	 */
	private static void parseIncludeFile(Node node) {

		// 处理include文件
		NamedNodeMap includeNodeMap = node.getAttributes();

		String includeFile = includeNodeMap.getNamedItem(XmlTag.FILE_ATTR).getNodeValue();

		// 递归解析
		parseConfigXml(includeFile);
	}

}

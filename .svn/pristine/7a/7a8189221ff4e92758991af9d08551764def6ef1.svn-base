package com.injedu.easycode.maintain.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.injedu.easycode.maintain.config.ConvertType;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;

/**
 * 配置信息
 *
 * User: liyd Date: 13-11-20 Time: 下午4:06
 */
public class EasyCodeContext {

	/** jdbc标识key */
	public static final String JDBC_KEY = "jdbc_";

	/** 常量标识key */
	public static final String CONSTANT_KEY = "constant_";

	/** 常量map */
	private static Map<String, String> constantMap = new HashMap<String, String>();

	/** 表配置map */
	private static Map<String, Table> tableMap = new HashMap<String, Table>();

	/** 任务配置 */
	private static Map<String, Task> taskMap = new LinkedHashMap<String, Task>();

	/** 数据转换map */
	private static Map<String, ConvertType> dataConvertMap = new HashMap<String, ConvertType>();
	/** 公用实体字段list */
	private static List<String> baseColumnsList = new ArrayList<String>();

	/**
	 * 添加数据转换配置
	 *
	 * @param dbType
	 *            the db type
	 * @param convertType
	 *            the convert type
	 */
	public static void addDataConvertType(String dbType, ConvertType convertType) {
		dataConvertMap.put(StringUtils.upperCase(dbType), convertType);
	}

	/**
	 * 获取数据转换配置
	 * 
	 * @param dbType
	 * @return
	 */
	public static ConvertType getDataConvertType(String dbType) {
		if (StringUtils.isBlank(dbType)) {
			return null;
		}
		return dataConvertMap.get(StringUtils.upperCase(dbType));
	}

	/**
	 * 添加任务
	 *
	 * @param map
	 *            the task map
	 */
	public static void addTask(Map<String, Task> map) {
		taskMap.putAll(map);
	}

	/**
	 * 获取所有任务
	 * 
	 * @return
	 */
	public static Map<String, Task> getAllTask() {
		return taskMap;
	}

	/**
	 * 获取所有常量
	 * 
	 * @return
	 */
	public static Map<String, String> getAllConstant() {
		return constantMap;
	}

	/**
	 * 获取表配置
	 * 
	 * @return
	 */
	public static Map<String, Table> getAllTable() {
		return tableMap;
	}

	/**
	 * 添加表配置
	 * 
	 * @param tableName
	 * @param table
	 */
	public static void addTable(String tableName, Table table) {
		tableMap.put(tableName, table);
	}

	/**
	 * 添加jdbc配置信息
	 *
	 * @param name
	 * @param value
	 */
	public static void addJdbcConfig(String name, String value) {

		constantMap.put(JDBC_KEY + name, value);
	}

	/**
	 * 获取jdbc配置信息
	 * 
	 * @param name
	 * @return
	 */
	public static String getJdbcConfig(String name) {
		return constantMap.get(JDBC_KEY + name);
	}

	/**
	 * 添加常量
	 *
	 * @param map
	 *            the map
	 */
	public static void addConstant(Map<String, String> map) {

		for (Map.Entry<String, String> entry : map.entrySet()) {
			constantMap.put(CONSTANT_KEY + entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 获取常量
	 * 
	 * @param name
	 * @return
	 */
	public static String getConstant(String name) {
		return constantMap.get(CONSTANT_KEY + name);
	}

	/**
	 * 添加公用实体字段
	 * 
	 * @param columnName
	 */
	public static void addBaseColumn(String columnName) {
		baseColumnsList.add(columnName);
	}

	/**
	 * 获取所有公用实体字段
	 * 
	 * @return
	 */
	public static List<String> getAllBaseColumnList() {
		return baseColumnsList;
	}

	/**
	 * 清空配置
	 */
	public static void clear() {
		constantMap.clear();
		tableMap.clear();
		taskMap.clear();
		dataConvertMap.clear();
		baseColumnsList.clear();
	}
}

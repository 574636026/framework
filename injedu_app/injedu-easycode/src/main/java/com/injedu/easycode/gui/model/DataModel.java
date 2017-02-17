package com.injedu.easycode.gui.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;

public class DataModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 数据库类型 */
	private String dbType;
	/** 数据库链接 */
	private String dburl;
	/** 数据库用户 */
	private String dbuser;
	/** 数据库密码 */
	private String dbpwd;
	/** 是否覆盖源代码 */
	private Boolean override = true;
	/** 目标路径 */
	private String targetPath;
	/** src 目录 */
	private String srcmainjava;
	/** recourse 目录 */
	private String srcmainresourse;
	/** test 目录 */
	private String srctestjava;
	/** test recourse 目录 */
	private String srctestresourse;
	/** 基础包名 */
	private String basePackage;
	/** 表明 */
	private String table;
	/** 表别名 */
	private String alais;
	/** 表说明 */
	private String desc;
	/** 任务列表 */
	private Queue<String> tasks = new LinkedList<String>();

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDburl() {
		return dburl;
	}

	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	public String getDbuser() {
		return dbuser;
	}

	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	public String getDbpwd() {
		return dbpwd;
	}

	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}

	public Boolean isOverride() {
		return override;
	}

	public void setOverride(Boolean override) {
		this.override = override;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getSrcmainjava() {
		return srcmainjava;
	}

	public void setSrcmainjava(String srcmainjava) {
		this.srcmainjava = srcmainjava;
	}

	public String getSrctestjava() {
		return srctestjava;
	}

	public void setSrctestjava(String srctestjava) {
		this.srctestjava = srctestjava;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getAlais() {
		return alais;
	}

	public void setAlais(String alais) {
		this.alais = alais;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSrcmainresourse() {
		return srcmainresourse;
	}

	public void setSrcmainresourse(String srcmainresourse) {
		this.srcmainresourse = srcmainresourse;
	}

	public String getSrctestresourse() {
		return srctestresourse;
	}

	public void setSrctestresourse(String srctestresourse) {
		this.srctestresourse = srctestresourse;
	}

	public Queue<String> getTasks() {
		return tasks;
	}

	public void setTasks(Queue<String> tasks) {
		this.tasks = tasks;
	}

	/**
	 * 数据校验
	 * 
	 * @return
	 */
	public List<String> validation() {

		List<String> list = new LinkedList<String>();

		if (StringUtils.isBlank(dbType)) {
			list.add("数据库类型不能为空！");
		}
		if (StringUtils.isBlank(dburl)) {
			list.add("数据库链接不能为空！");
		}
		if (StringUtils.isBlank(dbuser)) {
			list.add("数据库用户不能为空！");
		}
		if (StringUtils.isBlank(dbpwd)) {
			list.add("数据库密码不能为空！");
		}
		if (StringUtils.isBlank(targetPath)) {
			list.add("目标路径不能为空！");
		}
		if (StringUtils.isBlank(srcmainjava)) {
			list.add("src目录不能为空！");
		}
		if (StringUtils.isBlank(srctestjava)) {
			list.add("test目录不能为空！");
		}
		if (StringUtils.isBlank(basePackage)) {
			list.add("基础包名不能为空！");
		}
		if (StringUtils.isBlank(table)) {
			list.add("表名不能为空！");
		}

		return list;
	}

	@Override
	public String toString() {
		return "DataModel [dbType=" + dbType + ", dburl=" + dburl + ", dbuser=" + dbuser + ", dbpwd=" + dbpwd
				+ ", override=" + override + ", targetPath=" + targetPath + ", srcmainjava=" + srcmainjava
				+ ", srctestjava=" + srctestjava + ", basePackage=" + basePackage + ", table=" + table + ", alais="
				+ alais + ", desc=" + desc + "]";
	}

}

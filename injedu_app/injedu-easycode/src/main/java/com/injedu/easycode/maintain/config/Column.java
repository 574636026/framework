package com.injedu.easycode.maintain.config;

import org.apache.commons.lang.StringUtils;

import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.utils.NameUtils;

/**
 * 列信息
 *
 * User: liyd Date: 13-11-28 Time: 下午5:19
 */
public class Column {

	/** 列名 */
	private String name;

	/** 是否主键 */
	private boolean isPrimaryKey;

	/** 列备注 */
	private String comment;

	/** 数据库类型 */
	private String dbType;

	/** jdbc类型 */
	private String jdbcType;

	/** java类型 例：String */
	private String javaType;

	/** java类型class名称例：java.lang.String */
	private String javaClass;

	/**
	 * 获取java类型
	 * 
	 * @return
	 */
	public String getJavaType() {

		if (StringUtils.isBlank(javaType) && EasyCodeContext.getDataConvertType(this.dbType) != null) {
			ConvertType convertType = EasyCodeContext.getDataConvertType(this.dbType);
			this.javaClass = convertType.getJavaClass();
		}
		int index = StringUtils.lastIndexOf(javaClass, ".");
		this.javaType = StringUtils.substring(javaClass, index + 1);
		return this.javaType;
	}

	/**
	 * 获取jdbc类型
	 * 
	 * @return
	 */
	public String getJdbcType() {
		if (EasyCodeContext.getDataConvertType(this.dbType) != null) {
			ConvertType convertType = EasyCodeContext.getDataConvertType(this.dbType);
			this.jdbcType = convertType.getJdbcType();
		}
		return jdbcType;
	}

	/**
	 * 获取java class
	 * 
	 * @return
	 */
	public String getJavaClass() {
		if (EasyCodeContext.getDataConvertType(this.dbType) != null) {
			ConvertType convertType = EasyCodeContext.getDataConvertType(this.dbType);
			this.javaClass = convertType.getJavaClass();
		}
		return javaClass;
	}

	/**
	 * 获取骆驼命名法的列名称
	 * 
	 * @return
	 */
	public String getCamelName() {
		return NameUtils.getCamelName(this.name);
	}

	/**
	 * 获取首字母大写名称
	 * 
	 * @return
	 */
	public String getFirstUpperName() {

		return NameUtils.getFirstUpperName(this.getCamelName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
}

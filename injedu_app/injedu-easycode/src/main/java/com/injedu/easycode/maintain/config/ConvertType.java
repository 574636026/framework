package com.injedu.easycode.maintain.config;

/**
 * 类型转换配置
 * 
 * User: liyd
 * Date: 13-12-26
 * Time: 下午5:05
 */
public class ConvertType {

    /** 数据库类型 */
    private String dbType;

    /** jdbc类型 */
    private String jdbcType;

    /** java类型 例：java.lang.String */
    private String javaClass;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }
}

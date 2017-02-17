package com.injedu.easycode.maintain.database;

import java.sql.Connection;

import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.context.EasyCodeException;
import com.injedu.easycode.maintain.utils.DBUtils;

/**
 * 数据库操作抽象类
 * 
 * User: liyd Date: 13-12-6 Time: 上午11:13
 */
public abstract class AbstractDatabaseProvider implements DatabaseProvider {

	/**
	 * 获取数据库表的信息
	 *
	 * @param tableName
	 *            表名
	 * @return meta data
	 */
	@Override
	public Table getTableMetaData(String tableName) {

		Connection connection = DBUtils.getDefaultConnection();


		return getMetaData(tableName, connection);
	}

	/**
	 * 获取数据库表元信息
	 *
	 * @param tableName
	 *            the table name
	 * @param connection
	 *            the connection
	 * @return meta data
	 */
	public abstract Table getMetaData(String tableName, Connection connection) throws EasyCodeException;
}

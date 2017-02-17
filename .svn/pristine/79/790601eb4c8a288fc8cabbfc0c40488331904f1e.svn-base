package com.injedu.easycode.maintain.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.injedu.easycode.maintain.config.Column;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.context.EasyCodeException;

/**
 * mysql操作类
 *
 * User: liyd Date: 14-1-13 Time: 上午11:40
 */
public class MysqlProvider extends AbstractDatabaseProvider {

	/**
	 * 获取数据库表元信息
	 *
	 * @param tableName
	 *            the table name
	 * @param connection
	 *            the connection
	 * @return meta data
	 */
	@SuppressWarnings("resource")
	@Override
	public Table getMetaData(String tableName, Connection connection) throws EasyCodeException {

		Table table = new Table();
		table.setName(tableName);

		List<Column> columnList = new ArrayList<Column>();

		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rsd = null;
		try {
			// 查询时没有数据，只返回表头信息
			pst = connection.prepareStatement("select * from " + tableName + " where 1=2");
			rsd = pst.executeQuery().getMetaData();

			// 查询表备注
			pst = connection
					.prepareStatement("select table_comment from information_schema.tables where table_name = ?");
			pst.setString(1, tableName);
			rs = pst.executeQuery();

			if (rs.next()) {
				table.setDesc(rs.getString("table_comment"));
			}

			// 查询主键(联合主键)
			List<String> primaryKeyList = new ArrayList<String>();
			pst = connection.prepareStatement(
					"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_NAME='PRIMARY' and TABLE_NAME = ?");
			pst.setString(1, tableName);
			rs = pst.executeQuery();

			while (rs.next()) {
				primaryKeyList.add(rs.getString(1));
			}

			// 查询列备注
			pst = connection.prepareStatement(
					"select column_name, column_comment from information_schema.columns where table_name = ?");
			pst.setString(1, tableName);
			rs = pst.executeQuery();

			// 先将注释放入到map再获取，防止有些列没有注释获取不对应的问题
			Map<String, String> commentMap = new HashMap<String, String>();
			while (rs.next()) {
				commentMap.put(rs.getString("column_name"), rs.getString("column_comment"));
			}

			for (int i = 1; i <= rsd.getColumnCount(); i++) {
				String name = rsd.getColumnName(i);
				String dbType = rsd.getColumnTypeName(i);
				String javaClass = rsd.getColumnClassName(i);
				String comment = commentMap.get(name);

				boolean b = primaryKeyList.contains(name);
				Column column = new Column();
				column.setName(name);
				column.setDbType(dbType);
				column.setJdbcType(dbType);
				column.setJavaClass(javaClass);
				column.setComment(comment);
				column.setIsPrimaryKey(b);

				columnList.add(column);
			}
		} catch (SQLException e) {
			throw new EasyCodeException(e);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {

			}
		}
		table.setColumns(columnList);

		return table;
	}
}

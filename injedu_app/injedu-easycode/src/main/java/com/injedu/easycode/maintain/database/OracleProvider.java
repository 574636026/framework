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

import org.apache.commons.lang.StringUtils;

import com.injedu.easycode.maintain.config.Column;
import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.context.EasyCodeException;

/**
 * Oracle 操作类
 * 
 * User: liyd
 * Date: 13-12-6
 * Time: 上午11:11
 */
public class OracleProvider extends AbstractDatabaseProvider {

    /**
     * 获取数据库表元信息
     *
     * @param tableName the table name
     * @param connection the connection
     * @return meta data
     */
    @SuppressWarnings("resource")
	@Override
    public Table getMetaData(String tableName, Connection connection) {

    	Table table = new Table();
    	
        List<Column> columnList = new ArrayList<Column>();

        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSetMetaData rsd = null;
        try {
            //查询时没有数据，只返回表头信息
            pst = connection.prepareStatement("select * from " + tableName + " where 1=2");
            rsd = pst.executeQuery().getMetaData();

            //查询主键
            String primaryKey = null;
            pst = connection
                .prepareStatement("select col.column_name from user_constraints con, user_cons_columns col where con.constraint_name = col.constraint_name and con.constraint_type = 'P' and col.table_name = ?");
            pst.setString(1, tableName.toUpperCase());
            rs = pst.executeQuery();
            if (rs.next()) {
                primaryKey = rs.getString(1);
            }

            //查询列备注
            pst = connection
                .prepareStatement("SELECT * FROM USER_COL_COMMENTS WHERE TABLE_NAME = ?");
            pst.setString(1, tableName.toUpperCase());
            rs = pst.executeQuery();

            //先将注释放入到map再获取，防止有些列没有注释获取不对应的问题
            Map<String, String> commentMap = new HashMap<String, String>();
            while (rs.next()) {
                commentMap.put(rs.getString("COLUMN_NAME"), rs.getString("COMMENTS"));
            }

            for (int i = 1; i <= rsd.getColumnCount(); i++) {
                String name = rsd.getColumnName(i);
                String dbType = rsd.getColumnTypeName(i);
                String javaClass = rsd.getColumnClassName(i);
                String comment = commentMap.get(name);

                boolean b = StringUtils.equalsIgnoreCase(primaryKey, name) ? true : false;
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

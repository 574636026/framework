package com.injedu.imagesearch.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作辅助类
 *
 * User: liyd Date: 13-12-6 Time: 上午10:34
 */
public final class DBUtils {
	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);

	/** 数据库连接对象 */
	private static Connection connection;

	private static Properties pro;

	static {

		pro = new Properties();
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建数据库连接
	 */
	public synchronized static void createConnection() {

		try {
			if (connection != null && !connection.isClosed()) {
				return;
			}
			String driverClassName = pro.getProperty("jdbc.driver");
			String url = pro.getProperty("jdbc.url");
			String username = pro.getProperty("jdbc.username");
			String password = pro.getProperty("jdbc.password");
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			LOG.error("没有找到数据库驱动类，是否添加了数据库驱动的jar包？", e);
		} catch (SQLException e) {
			LOG.error("创建数据库连接出现错误", e);
		}
	}

	/**
	 * 获取配置文件中配置的默认数据库连接
	 *
	 * @return default connection
	 */
	public static Connection getDefaultConnection() {

		try {
			if (connection == null || connection.isClosed()) {
				createConnection();
			}
			return connection;
		} catch (SQLException e) {
			LOG.error("获取数据库连接出现错误", e);
		}
		return null;
	}

	/**
	 * 关闭数据库连接
	 *
	 */
	public static void closeConnection() {

		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {

		}
	}

	static final Pattern UNAME_REGEXP = Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]{2,20}$");

	public static boolean isIllegalName(String uname) {

		return UNAME_REGEXP.matcher(uname).matches();
	}

	public static void main(String[] args) throws Exception {

		Connection conn = DBUtils.getDefaultConnection();

		 PreparedStatement ps = conn
		 .prepareStatement("insert into weixin_kaixinxue(weixin,userAccount,uname,createTime) values (?,?,?,?)");
		
		 String uname = "This is a smiley \uD83C\uDFA6 face\uD860\uDD5D \uD860\uDE07 \uD860\uDEE2 \uD863\uDCCA \uD863\uDCCD \uD863\uDCD2 \uD867\uDD98";
		
		 ps.setString(1, "xxx");
		 ps.setInt(2, 11111);
		 ps.setString(3, uname);
		 ps.setString(4, "2016-07-26 00:00:00");
		
		 ps.executeUpdate();

//		PreparedStatement ps = conn.prepareStatement("select * from weixin_kaixinxue");
//
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next()) {
//
//			System.out.println(rs.getString(4));
//
//		}

		ps.close();

		DBUtils.closeConnection();

	}

}

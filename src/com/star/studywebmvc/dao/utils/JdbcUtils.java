package com.star.studywebmvc.dao.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
public class JdbcUtils {
	
	/**
	 * Title: releaseConnection
	 * Description: 释放数据连接
	 * @param connection
	 */
	public static void releaseConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static DataSource dataSource = null;	
	static {
		//数据源放在静态代码块中只会被创建一次，节省资源。
		dataSource = new ComboPooledDataSource("mysql");
	}
	
	/**
	 * Title: getConnection
	 * Description: 获取数据库连接
	 * @return
	 * @throws SQLException 
	 *
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
		
	}
	
}

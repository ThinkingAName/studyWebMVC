package com.star.studywebmvc.dao.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
public class JdbcUtils {
	
	/**
	 * Title: releaseConnection
	 * Description: �ͷ���������
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
		//����Դ���ھ�̬�������ֻ�ᱻ����һ�Σ���ʡ��Դ��
		dataSource = new ComboPooledDataSource("mysql");
	}
	
	/**
	 * Title: getConnection
	 * Description: ��ȡ���ݿ�����
	 * @return
	 * @throws SQLException 
	 *
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
		
	}
	
}

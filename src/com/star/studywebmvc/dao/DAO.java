package com.star.studywebmvc.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.star.studywebmvc.dao.utils.JdbcUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * ��װ������CRUD������
 * ������ֱ����dao�л�ȡconnection
 * ʹ��DBUtil�������
 * @author Administrator
 * @param <T> ��ǰDAO �����ʵ����
 */
public class DAO<T> {
	private Class<T> clazz;
	private QueryRunner queryRunner =  new QueryRunner();	
	
	/**
	 * Title: 
	 * Description: ͨ�����죬���������еľ���ĵ�ʵ����
	 */
	public DAO() {
		Type superClass =  getClass().getGenericSuperclass();
		if(superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType)superClass;
			Type[] types = parameterizedType.getActualTypeArguments();
			if(types != null && types.length > 0) {
				if (types[0] instanceof Class) {
					clazz = (Class<T>) types[0];
				}	
			}		
		}
		
	}
	
	/**
	 * Title: getForValue
	 * Description: ����ĳ���ֶε�ֵ
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql,Object...args) {
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			return (E)queryRunner.query(connection, sql, new ScalarHandler(), args);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}		
		
		return null;
	}
	
	/**
	 * Title: getForList
	 * Description: ���ض��ʵ�����
	 * @param sql
	 * @param args
	 * @return
	 *
	 */
	public List<T> getForList(String sql,Object...args){
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}		
		
		return null;
	}
	
	/**
	 * Title: get
	 * Description: ���ص�����Ӧʵ����
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql,Object...args) {
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}		
		return null;
	}
	/**
	 * Title: update
	 * Description: �÷�����װ��insert��delete��update
	 * @param sql:sql���
	 * @param args:ռλ������ֵ
	 */
	public void update(String sql,Object...args) {
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			queryRunner.update(connection, sql, args);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}		
	} 
} 

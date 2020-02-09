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
 * 封装基本的CRUD操作，
 * 操作简单直接在dao中获取connection
 * 使用DBUtil解决方案
 * @author Administrator
 * @param <T> 当前DAO 处理的实体类
 */
public class DAO<T> {
	private Class<T> clazz;
	private QueryRunner queryRunner =  new QueryRunner();	
	
	/**
	 * Title: 
	 * Description: 通过构造，反射子类中的具体的的实例。
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
	 * Description: 返回某个字段的值
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
	 * Description: 返回多个实体对象
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
	 * Description: 返回单个对应实体类
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
	 * Description: 该方法封装了insert、delete、update
	 * @param sql:sql语句
	 * @param args:占位符参数值
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

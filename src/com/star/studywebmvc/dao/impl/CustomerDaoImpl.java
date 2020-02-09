package com.star.studywebmvc.dao.impl;

import java.util.List;

import com.star.studywebmvc.dao.CustomerDAO;
import com.star.studywebmvc.dao.DAO;
import com.star.studywebmvc.pojo.CriteriaCustomer;
import com.star.studywebmvc.pojo.Customer;

public class CustomerDaoImpl extends DAO<Customer> implements CustomerDAO{

	
	
	@Override
	public List<Customer> getAll() {
		String sql = "select id,name,address,phone from customers";
		return getForList(sql);
	}

	@Override
	public Customer getById(Integer id) {
		String sql = "select id,name,address,phone from customers where id=?";
		return get(sql, id);
	}

	@Override
	public void save(Customer customer) {
		String sql = "insert into customers(name,address,phone)values(?,?,?)";
		update(sql,customer.getName(),customer.getAddress(),customer.getPhone());
		
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "delete from customers where id=?";
		update(sql, id);
	}
	@Override
	public long getCountWithName(String name) {
		String sql = "select count(id) from customers where name=?";
		return getForValue(sql, name);
	}

	@Override
	public List<Customer> getListWithCriteriaCustomer(CriteriaCustomer criteriaCustomer) {
		String sql = "select id,name,address,phone from customers where name"
				+ " like ? and address like ? and phone like ?";
		
		return getForList(sql, criteriaCustomer.getName(),criteriaCustomer.getAddress(),criteriaCustomer.getPhone());
	}

	@Override
	public void update(Customer customer) {
		String sql = "update customers set name=?,address=?,phone = ? where id =?";
		update(sql, customer.getName(),customer.getAddress(),customer.getPhone(),customer.getId());
	}

}

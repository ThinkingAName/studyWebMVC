package com.star.studywebmvc.dao;

import java.util.List;

import com.star.studywebmvc.pojo.CriteriaCustomer;
import com.star.studywebmvc.pojo.Customer;

public interface CustomerDAO {
	
	public void update(Customer customer);
	
	/**
	 * Title: getListWithCriteriaCustomer
	 * Description: 模糊查询方法
	 * @param criteriaCustomer
	 * @return
	 *
	 */
	public List<Customer> getListWithCriteriaCustomer(CriteriaCustomer criteriaCustomer);

	public List<Customer> getAll();
	
	public Customer getById(Integer id);
	
	public void save(Customer customer);
	
	public void deleteById(Integer id);
	
	/**
	 * Title: getCountWithName
	 * Description: 返回相同名字的条数
	 * @param name
	 * @return
	 */
	public long getCountWithName(String name);
 	
}

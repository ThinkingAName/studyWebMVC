package com.star.studywebmvc.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.star.studywebmvc.dao.CustomerDAO;
import com.star.studywebmvc.dao.impl.CustomerDaoImpl;
import com.star.studywebmvc.pojo.Customer;

class CustomerDaoImplTest {
	public CustomerDAO customerDAO = new CustomerDaoImpl();

	@Test
	void testGetAll() {
		List<Customer> lists = customerDAO.getAll();
		for (Customer customer : lists) {
			System.out.println(customer.toString());
		}	
	}

	@Test
	void testGetById() {
		System.out.println(customerDAO.getById(1).toString());
		assertNotNull(customerDAO.getById(1));
	}

	@Test
	void testSave() {
		String random = String.valueOf((Math.random()*1000)).split("\\.")[0];
		Customer customer = new Customer();
		customer.setName("Tom" + random);
		customer.setAddress("xianÎ÷°²" + random);
		customer.setPhone("13455566" + random);
		customerDAO.save(customer);
	}

	@Test
	void testDeleteById() {
		customerDAO.deleteById(2);
		System.out.println(customerDAO.getById(2));
		assertNull(customerDAO.getById(2));
	}

	@Test
	void testGetCountWithName() {
		System.out.println(customerDAO.getCountWithName("Tom762"));
	}

}

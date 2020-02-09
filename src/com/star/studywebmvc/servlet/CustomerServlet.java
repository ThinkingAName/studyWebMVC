package com.star.studywebmvc.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.star.studywebmvc.dao.CustomerDAO;
import com.star.studywebmvc.dao.impl.CustomerDaoImpl;
import com.star.studywebmvc.pojo.CriteriaCustomer;
import com.star.studywebmvc.pojo.Customer;

public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO= new CustomerDaoImpl();
       
    public CustomerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取servletPath
		String servletPaht = request.getServletPath();
		System.out.println("servletPaht: " + servletPaht);
		//2.去除/和.do
		String methodName = servletPaht.substring(1, servletPaht.length()-3);
		System.out.println("methodName: " + methodName);
		try {
			//3.利用反射获取对应mehtodName对应的方法
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class
					,HttpServletResponse.class);
			//4.利用反射，调取对应的方法
			method.invoke(this, request,response);
		} catch (Exception e) {
			response.sendRedirect("error.jsp");
		}
		
	}

	private void queryCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		CriteriaCustomer criteriaCustomer = new CriteriaCustomer(name,address,phone);		
		List<Customer> customers = customerDAO.getListWithCriteriaCustomer(criteriaCustomer);
		request.setAttribute("customers", customers);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		//response.sendRedirect("/index.jsp);
	}
	
	private void deleteCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String customerId = request.getParameter("id");
		int id = 0;
		
		try {
			//防止获取的id不能转为int类型，抛异常。
			id = Integer.parseInt(customerId);
			customerDAO.deleteById(id);
			//request.getRequestDispatcher("/queryCustomer.do").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("queryCustomer.do");

	}
	
	private void addCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1.获取请求参数
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		//2.检验name字段是否已在数据库存在
		//2.1调用customerDAO.getCountWithName(name);，
		long count = customerDAO.getCountWithName(name);
		if (count>0) {
			//2.2若返回值大于0则证明已存在,转发到addCustomer页面，并在该页面的设置message属性提示，并回显填写数据
			request.setAttribute("message", "该用户名已存在，请重新输入");
			request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
			return;
		}else if (count == 0) {
			//3.若等于0，则不存在，封装customer对象
			Customer customer = new Customer(name,address,phone);
			System.out.println("---->" + customer);
			//4.调用customerDAO.save(customer);
			customerDAO.save(customer);
		}
		//5.重定向到success.jsp页面
		response.sendRedirect("success.jsp");
	}
	
	private void editCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1.获取编辑用的信息
		String idStr = request.getParameter("id");
		String forwardPath = "/error.jsp";
		int customerId = 0;
		try {
			customerId = Integer.parseInt(idStr);
			Customer customer = customerDAO.getById(customerId);
			if (customer!=null) {
				//添加回显数据
				request.setAttribute("customer", customer);
				forwardPath = "/editCustomer.jsp";
			}
		} catch (Exception e) {}
		//2.返回编辑页面，回显编辑信息
		request.getRequestDispatcher(forwardPath).forward(request, response);
		
	}

	private void updateCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String oldName = request.getParameter("oldName");
		String newName = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		long count = customerDAO.getCountWithName(newName);
		Customer customer = new Customer(newName,address,phone);
		customer.setId(Integer.parseInt(idStr));
		if (!oldName.equalsIgnoreCase(newName)&&count>0) {
			//2.2若返回值大于0则证明已存在,转发到addCustomer页面，并在该页面的设置message属性提示，并回显填写数据
			request.setAttribute("message", "该用户名已存在，请重新输入");
			customer.setName(oldName);
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
			return;
		}
		customerDAO.update(customer);
		response.sendRedirect("queryCustomer.do");
	}
}

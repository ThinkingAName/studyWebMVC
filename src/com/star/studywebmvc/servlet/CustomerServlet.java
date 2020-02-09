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
		//1.��ȡservletPath
		String servletPaht = request.getServletPath();
		System.out.println("servletPaht: " + servletPaht);
		//2.ȥ��/��.do
		String methodName = servletPaht.substring(1, servletPaht.length()-3);
		System.out.println("methodName: " + methodName);
		try {
			//3.���÷����ȡ��ӦmehtodName��Ӧ�ķ���
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class
					,HttpServletResponse.class);
			//4.���÷��䣬��ȡ��Ӧ�ķ���
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
			//��ֹ��ȡ��id����תΪint���ͣ����쳣��
			id = Integer.parseInt(customerId);
			customerDAO.deleteById(id);
			//request.getRequestDispatcher("/queryCustomer.do").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("queryCustomer.do");

	}
	
	private void addCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ�������
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		//2.����name�ֶ��Ƿ��������ݿ����
		//2.1����customerDAO.getCountWithName(name);��
		long count = customerDAO.getCountWithName(name);
		if (count>0) {
			//2.2������ֵ����0��֤���Ѵ���,ת����addCustomerҳ�棬���ڸ�ҳ�������message������ʾ����������д����
			request.setAttribute("message", "���û����Ѵ��ڣ�����������");
			request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
			return;
		}else if (count == 0) {
			//3.������0���򲻴��ڣ���װcustomer����
			Customer customer = new Customer(name,address,phone);
			System.out.println("---->" + customer);
			//4.����customerDAO.save(customer);
			customerDAO.save(customer);
		}
		//5.�ض���success.jspҳ��
		response.sendRedirect("success.jsp");
	}
	
	private void editCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ�༭�õ���Ϣ
		String idStr = request.getParameter("id");
		String forwardPath = "/error.jsp";
		int customerId = 0;
		try {
			customerId = Integer.parseInt(idStr);
			Customer customer = customerDAO.getById(customerId);
			if (customer!=null) {
				//��ӻ�������
				request.setAttribute("customer", customer);
				forwardPath = "/editCustomer.jsp";
			}
		} catch (Exception e) {}
		//2.���ر༭ҳ�棬���Ա༭��Ϣ
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
			//2.2������ֵ����0��֤���Ѵ���,ת����addCustomerҳ�棬���ڸ�ҳ�������message������ʾ����������д����
			request.setAttribute("message", "���û����Ѵ��ڣ�����������");
			customer.setName(oldName);
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
			return;
		}
		customerDAO.update(customer);
		response.sendRedirect("queryCustomer.do");
	}
}

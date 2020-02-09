<%@page import="com.star.studywebmvc.pojo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
</head>
<body>
	<!--回显数据 -->
	<%
		Customer customer = (Customer)request.getAttribute("customer");
		Integer id = customer.getId();
		String name = customer.getName() == null ? "" : customer.getName();
		String address =customer.getAddress() == null ? "": customer.getAddress();
		String phone = customer.getPhone() == null ? "" : customer.getPhone();
	%>
	<p style ="color:red">
	<%=request.getAttribute("message") == null?"":request.getAttribute("message")%>
	</p>
	<form action="updateCustomer.do" method="post">
		<table>
			<tr>
				<td>name :</td>
				<td><input type="text" name="name" value="<%=name%>"/></td>
				<input type="hidden" name="id" value="<%=id%>"/>
				<input type="hidden" name="oldName" value="<%=name%>"/>
			</tr>
			<tr>
				<td>address:</td>
				<td><input type="text" name="address" value="<%=address%>" /></td>
			</tr>
			<tr>
				<td>phone:</td>
				<td><input type="text" name="phone" value="<%=phone%>"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>	

</body>
</html>
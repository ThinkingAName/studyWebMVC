<%@page import="com.star.studywebmvc.pojo.Customer"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="scripts/JQuery/jquery-3.1.0.js"></script>
<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			var content = $(this).parent().parent().find("td:eq(1)").text();
			var flag = confirm("确定要删除客户: "+content+" 的信息");
			return flag;
		});
	});

</script>
<title>首页面</title>
</head>
<body>

	<form action="queryCustomer.do" method="post">
		<table>
			<tr>
				<td>name :</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>address:</td>
				<td><input type="text" name="address" /></td>
			</tr>
			<tr>
				<td>phone:</td>
				<td><input type="text" name="phone" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Search" /></td>
				<td><a href="addCustomer.jsp">添加客户</a></td>
			</tr>
		</table>
	</form>
	<br>
	<br>
	<hr>
	<%
		List<Customer> customers = (List<Customer>) request.getAttribute("customers");
		if (customers != null) {
	%>
	<table border="1" cellpadding="10" cellspacing="0">
		<tr>
			<th>id</th>
			<th>name</th>
			<th>address</th>
			<th>phone</th>
			<th>编辑</th>
			<th>删除</th>
		</tr>
		<%
			for (Customer customer : customers) {
		%>
		<tr>
			<td><%=customer.getId()%></td>
			<td><%=customer.getName()%></td>
			<td><%=customer.getAddress()%></td>
			<td><%=customer.getPhone()%></td>
			<th><a href="editCustomer.do?id=<%=customer.getId()%>">Edit</a></th>
			<th><a href="deleteCustomer.do?id=<%=customer.getId()%>" class="delete">Delete</a></th>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>



</body>
</html>
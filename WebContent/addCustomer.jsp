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
		String name = (String)(request.getParameter("name") == null ? "" : request.getParameter("name"));
		String address =(String)(request.getParameter("address") == null ? "": request.getParameter("address"));
		String phone = (String)(request.getParameter("phone") == null ? "" : request.getParameter("phone"));
	%>
	<p style ="color:red">
	<%=request.getAttribute("message") == null?"":request.getAttribute("message")%>
	</p>
	<form action="addCustomer.do" method="post">
		<table>
			<tr>
				<td>name :</td>
				<td><input type="text" name="name" value="<%=name%>"/></td>
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
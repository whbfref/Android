<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" content="0.5;url=http://localhost:8080/ShoppingServer/xiugaikucun.jsp">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="adbean" scope="request" class="zJSPServlet.AdminBean"/>
	
	<%
		String huojiaid = request.getParameter("huojiaid");
		String yifuid = request.getParameter("yifuid");
		String kucunliang = request.getParameter("kucunliang");
		
		String sqlquery = "select * from Store";
		ResultSet rs = adbean.query(sqlquery);
		
		while(rs.next()){
			String aa = rs.getString("storeId");
			String bb = rs.getString("ClothesId");
			if(huojiaid.equals(aa)&&yifuid.equals(bb)){
				String sql = "update Store set ClothesSave='"+kucunliang+"'where storeId='"+huojiaid+"'and ClothesId='"+yifuid+"'";
				String sql1 = "update Clothes set ClothesSave='"+kucunliang+"'where ClothesId='"+yifuid+"'";
				adbean.update(sql);
				adbean.update(sql1);
				break;
			}
		}
	%>
		
</body>
</html>
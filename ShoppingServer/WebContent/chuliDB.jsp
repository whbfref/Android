<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>
</head>
<body>		

	<jsp:useBean id="adbean" scope="request" class="zJSPServlet.AdminBean"/>
	<%
		String sql = "backup database ShoppingClothes to ShoppingClothes with init";
		adbean.doexe(sql);
		FileInputStream fis = new FileInputStream("C:\\Program Files\\Microsoft SQL Server\\MSSQL10_50.MSSQLSERVER\\MSSQL\\Backup\\ShoppingClothes.bak");
		FileOutputStream fos = new FileOutputStream("E:\\ShoppingClothes.bak");
		byte[] buf = new byte[1024];
		int len = 0;
		while((len=fis.read(buf)) != -1){
			fos.write(buf,0,len);
		}	
		fis.close();
		fos.close();
	%>
	
	<p>备份完成...保存在 <b style="color:#0000FF">E</b> 盘目录下</p>
</body>
</html>
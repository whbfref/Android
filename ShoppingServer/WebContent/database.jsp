<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>

<script language="javascript">
 function fin(){
 
 	var a = qq.value;
	alert(a);
 }

</script>

</head>
<body>
	<form action="chuliDB.jsp" method="post">
		<input type="submit" value="数据库备份"/>
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>
 <style type="text/css">  
  body {  
    width: 600px;  
    margin: 6px auto;  
    font-family: 'trebuchet MS', 'Lucida sans', Arial;  
    font-size: 14px;  
    color: #444;  
    }  
      
    table {  
    *border-collapse: collapse; /* IE7 and lower */  
    border-spacing: 0;  
    width: 100%;  
    }       
    /*----------------------*/  
      
    </style>  


</head>
<body bgcolor="#CCCCCC">
	<form action="chulitable1" method="post">
	<table>
		<tr>
			<td>货架id:</td>
			<td><input type="text" name="huojiaid"/></td>
			<td>衣服id:</td>
			<td><input type="text" name="yifuid"/></td>
		</tr>
		<tr>
			<td>库存量:</td>
			<td><input type="text" name="kucunliang"/></td>
		</tr>
		<tr>
			<td><input type="reset" value="重置"/></td>
			<td><input type="submit" value="修改" onClick=""/></td>
		</tr>
	</table>
	</form>
</body>
</html>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>
      <style>  
      
    body {  
    width: 600px;  
    margin: 40px auto;  
    font-family: 'trebuchet MS', 'Lucida sans', Arial;  
    font-size: 14px;  
    color: #444;  
    }  
      
    table {  
    *border-collapse: collapse; /* IE7 and lower */  
    border-spacing: 0;  
    width: 100%;  
    }  
      
    .bordered {  
    border: solid #ccc 1px;  
    -moz-border-radius: 6px;  
    -webkit-border-radius: 6px;  
    border-radius: 6px;  
    -webkit-box-shadow: 0 1px 1px #ccc;  
    -moz-box-shadow: 0 1px 1px #ccc;  
    box-shadow: 0 1px 1px #ccc;  
    }  
      
    .bordered tr:hover {  
    background: #fbf8e9;  
    -o-transition: all 0.1s ease-in-out;  
    -webkit-transition: all 0.1s ease-in-out;  
    -moz-transition: all 0.1s ease-in-out;  
    -ms-transition: all 0.1s ease-in-out;  
    transition: all 0.1s ease-in-out;  
    }  
      
    .bordered td, .bordered th {  
    border-left: 1px solid #ccc;  
    border-top: 1px solid #ccc;  
    padding: 10px;  
    text-align: left;  
    }  
      
    .bordered th {  
    background-color: #dce9f9;  
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));  
    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);  
    background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);  
    background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);  
    background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);  
    background-image: linear-gradient(top, #ebf3fc, #dce9f9);  
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;  
    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;  
    border-top: none;  
    text-shadow: 0 1px 0 rgba(255,255,255,.5);  
    }  
      
    .bordered td:first-child, .bordered th:first-child {  
    border-left: none;  
    }  
      
    .bordered th:first-child {  
    -moz-border-radius: 6px 0 0 0;  
    -webkit-border-radius: 6px 0 0 0;  
    border-radius: 6px 0 0 0;  
    }  
      
    .bordered th:last-child {  
    -moz-border-radius: 0 6px 0 0;  
    -webkit-border-radius: 0 6px 0 0;  
    border-radius: 0 6px 0 0;  
    }  
      
    .bordered th:only-child{  
    -moz-border-radius: 6px 6px 0 0;  
    -webkit-border-radius: 6px 6px 0 0;  
    border-radius: 6px 6px 0 0;  
    }  
      
    .bordered tr:last-child td:first-child {  
    -moz-border-radius: 0 0 0 6px;  
    -webkit-border-radius: 0 0 0 6px;  
    border-radius: 0 0 0 6px;  
    }  
      
    .bordered tr:last-child td:last-child {  
    -moz-border-radius: 0 0 6px 0;  
    -webkit-border-radius: 0 0 6px 0;  
    border-radius: 0 0 6px 0;  
    }  
      
    /*----------------------*/  
      
    </style>  

</head>
<body>
	<jsp:useBean id="adbean" scope="request" class="zJSPServlet.AdminBean"/>
	
	<table border="1" cellspacing="0px" class="bordered">
		<tr>
			<th>订单号</th>
			<th>用户名</th>
			<th>衣服编号</th>
			<th>购买数量</th>
			<th>总价</th>
		</tr>
	<% 
		String sql = "select orderId,username,orderGoods.ClothesId,orderGoods.ClothesBuy,ClothesPrice from orderGoods,Clothes where orderGoods.ClothesId=Clothes.ClothesId";
		ResultSet rs = adbean.query(sql);
		while(rs.next()){
			out.println("<tr>");
				out.println("<td>"+rs.getString("orderId")+"</td>");
				out.println("<td>"+rs.getString("username")+"</td>");
				out.println("<td>"+rs.getString("ClothesId")+"</td>");
				out.println("<td>"+rs.getString("ClothesBuy")+"</td>");
				out.println("<td>"+Integer.parseInt((String)rs.getString("ClothesBuy"))*Integer.parseInt((String)rs.getString("ClothesPrice"))+"</td>");
			out.println("</tr>");
		}
	%>
	</table>
	
</body>
</html>
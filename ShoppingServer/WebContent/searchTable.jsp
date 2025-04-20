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
	
	<table border="1" cellspacing="0px" class="bordered" id="mytable">
		<tr>
			<th>货架id</th>
			<th>衣服id</th>
			<th>库存量</th>
			<th>成本</th>
			<th>总价</th>
		</tr>
	<% 
		String fanwei1 = request.getParameter("fanwei1");
		String fanwei2 = request.getParameter("fanwei2");
		
		request.setAttribute("fanwei1",fanwei1);
		request.setAttribute("fanwei2",fanwei2);
		request.getSession().setAttribute("fanwei1",fanwei1);
		request.getSession().setAttribute("fanwei2",fanwei2);
		/*if(fanwei1=="" || fanwei2==""){
			fanwei1 = adbean.getFanw1();
			fanwei2 = adbean.getFanw2();
		}
		else{
			adbean.setFanw1(fanwei1);
			adbean.setFanw2(fanwei2);
		}*/
		String sql = "select * from Store where storeId between "+fanwei1+" and "+fanwei2;
		ResultSet rs = adbean.query(sql);
		while(rs.next()){
			out.println("<tr>");
				out.println("<td>"+rs.getString("storeId")+"</td>");
				out.println("<td>"+rs.getString("ClothesId")+"</td>");
				if(Integer.parseInt(rs.getString("ClothesSave"))<=10){
					out.println("<td style=\"color:#FF0000\">"+rs.getString("ClothesSave")+"</td>");
				}
				else{
					out.println("<td>"+rs.getString("ClothesSave")+"</td>");
				}
				out.println("<td>"+rs.getString("ClothesCheng")+"</td>");
				out.println("<td>"+Integer.parseInt((String)rs.getString("ClothesSave"))*Integer.parseInt((String)rs.getString("ClothesCheng"))+"</td>");
			out.println("</tr>");
		}
	%>
	</table>
</body>
</html>
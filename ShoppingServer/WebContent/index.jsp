<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<style type="text/css">
	body {
	margin-left: 0px;
	margin-top: 0px;
}

    .t1{
	margin-left:30px;
	margin-top:30px;
	}
	.t2{
	font-family:Verdana, Arial, Helvetica, sans-serif;
	}
    </style>
	
<script language="javascript">
<!--
  function fun(){
  if(form1.username.value==""){
     alert("请输入用户名！");
	 form1.text1.focus();
  }
  
  else if(form1.password.value==""){
          alert("请输入密码！");
		  form1.text2.focus();
  }
  else {
         form1.submit();
  }
  }
-->
</script>
  </head>
  
  <body background="Zsrc/image/7363496_101642441130_2.jpg">
    <div> 
      <div><span><img src="Zsrc/image/1403281465_805099.png" width="260" height="58" align="middle"/></span></div>
      <div><img src="Zsrc/image/1403185823_417589.png" width="455" height="63" align="middle"/></div>
    </div>
	<form  action="jsp_loginServlet" name="form1" method="post">
	<table class="t1">
		<tr>
			<td class="t2">username:</td>
			<td><input type="text" name="username"/></td>
		</tr>
			<td class="t2">password:</td>
			<td><input type="password" name="password"/></td>
		<tr>
			<td><input type="reset" value="reset"/></td>
			<td><input type="button" onClick="fun()" value="login"/></td>
		</tr>
	</table>
	</form>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>
<style type="text/css">
a{	
	font-family:Geneva, Arial, Helvetica, sans-serif

}

a:LINK{
	text-decoration: none;
	color: #0000FF;
}
a:VISITED {
	text-decoration: none;
	color: #0000FF;
}
</style>
<script language="javascript">
<!--
  function fun(){
  if(form1.fanwei1.value==""||form1.fanwei2.value==""){
     alert("请输入范围");
	 form1.fanwei1.focus();
  }
  else if(form1.fanwei1.value>form1.fanwei2.value){
   	 alert("请输入正确的范围");
	 form1.fanwei1.focus(); 
  }
  else {
         form1.submit();
 	 }
  }
-->
</script>


</head>
<body bgcolor="#CCCCCC">
		<form action="searchTable.jsp" method="post" target="storemain"  name="form1">
			<div align="center">
			  <table>
			    <tr>
			      <td>搜索货架id:</td>
				    <td><input type="text" width="50px" name="fanwei1"/><b>-</b></td>
				    <td><input type="text"  width="50px"  name="fanwei2"/></td>
				  </tr>
				  <tr>
				  	<td></td>
				  	<td><input type="button" value="搜索"  onClick="fun()"/></td>
					<td><a href="table1.jsp" target="storemain">搜索全部</a></td>
				  </tr>
			    </table>
		  </div>
		</form>
</body>
</html>
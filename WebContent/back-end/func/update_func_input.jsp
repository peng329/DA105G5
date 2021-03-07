<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.func.model.*"%>

<%
  FuncVO funcVO = (FuncVO) request.getAttribute("funcVO"); //FuncServlet.java (Concroller) 存入req的funcVO物件 (包括幫忙取出的funcVO, 也包括輸入資料錯誤時的funcVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>權限資料修改 - update_func_input.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>權限資料修改 - update_func_input.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="func.do" name="form1">
<table>
	<tr>
		<td>權限編號:<font color=red><b>*</b></font></td>
		<td><%=funcVO.getFc_no()%></td>
	</tr>
	
	<tr>
		<td>權限名稱:</td>
		<td><input type="TEXT" name="fc_name" size="45"
			 value="<%=funcVO.getFc_name()%>" /></td>
	</tr>
	


</table>


<br>
<input type="hidden" name="action" value="update">
<!-- 一些不能改的用hidden傳回 -->
<input type="hidden" name="fc_no" value="<%=funcVO.getFc_no()%>">


<input type="submit" value="送出修改">
</FORM>
</body>


</html>
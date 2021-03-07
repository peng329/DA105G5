<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.eqpic.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  EqpicVO eqpicVO = (EqpicVO) request.getAttribute("eqpicVO"); 
%>

<html>
<head>
<title>單張圖片資料- listOneEqpic.jsp</title>

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
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>單張圖片資料- listOneEqpic.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>圖片流水號</th>
		<th>裝備流水號</th>
		<th>圖片</th>
	</tr>
	<tr>
		<td><%=eqpicVO.getEpic_seq()%></td>
		<td><%=eqpicVO.getEp_seq()%></td>
		<td><img src="/IBM_Equip_0201MVC_Single-Table_css/PrintPic?epic_seq=${eqpicVO.epic_seq}" width="100px" height="100px"></td>
	</tr>
</table>

</body>
</html>
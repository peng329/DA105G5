<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.locale.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	LocVO locVO = (LocVO) request.getAttribute("locVO"); //LocServlet.java(Concroller), 存入req的locVO物件
%>

<html>
<head>
<title>地區資料 - listOneLoc.jsp</title>

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
		<tr>
			<td>
				<h3>單一地區資料 - ListOneLoc.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath() %>/dp_home.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>地區編號</th>
			<th>國家代號</th>
			<th>潛點名稱</th>
			<th>天氣(JSON)</th>
		</tr>
		<tr>
			<td><%=locVO.getLoc_no()%></td>
			<td><%=locVO.getCtry()%></td>
			<td><%=locVO.getSub_region()%></td>
			<td><%=locVO.getWeather()%></td>
		</tr>
	</table>

</body>
</html>
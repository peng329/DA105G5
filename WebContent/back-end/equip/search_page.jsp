<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.equip.model.*"%>
<%@ page import="com.equipc.model.*"%>

<% 
	EquipCService equipcSvc = new EquipCService();
	List<EquipCVO>list = equipcSvc.getAll();
	
	EquipService equipSvc = new EquipService();
	List<EquipVO>list2 = equipSvc.getAll();
	
	
	Set<String> set = new LinkedHashSet<String>();
	for (EquipVO equipVO : list2) {
		set.add(equipVO.getDs_name());
	}

	pageContext.setAttribute("set", set);

%>

<jsp:useBean id="Equipment_ByCompositeQuery" scope="request" type = "java.util.List<EquipVO>"/>
<jsp:useBean id ="equipCSvc" scope ="page" class="com.equipc.model.EquipCService"/>

<html>
<head>
<title>IBM Equip : Home</title>

<style>
table {
	width: 1200px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
	border-collapse:collapse;
	border: 1px solid #CCCCFF;
}
  
#table-1{
  background-color:#81C0C0;
  border:0px;
  }
  

table, th, td {
	border: 1px #cccccc solid;
	
}

th, td {
	cellpadding:0px;
	border-collapse:collapse;
	padding:5px;
	padding-top:0px;
	padding-bottom:0px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td style="border:0px;"><h1>${param.ds_name}</h1></td></tr>
   <tr><td style="border:0px;"><a href="select_page.jsp" style="font-size:25px;">回首頁</a></td></tr>
</table>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
<tr>
<th>潛店名稱</th>
<th>裝備分類名稱</th>
<th>裝備名稱</th>
<th>裝備租金</th>
</tr>

<c:forEach var="equipVO" items="${Equipment_ByCompositeQuery}">
	<tr align='center' valign='middle'>
<c:forEach var="ds_name" items="${set}">
<c:if test="${equipVO.ds_name==ds_name}">

		<td>${ds_name}</td>
</c:if>
</c:forEach>

		<td>
		<c:forEach var="equipCVO" items="${equipCSvc.all}">
			<c:if test ="${equipVO.epc_no == equipCVO.epc_no}">
				${equipCVO.epc_name}
			</c:if>
			</c:forEach>
		</td>		
		<td>${equipVO.ep_name}</td>
		<td>${equipVO.ep_rp}</td>
</tr>
</c:forEach>
</table>

</body>
</html>
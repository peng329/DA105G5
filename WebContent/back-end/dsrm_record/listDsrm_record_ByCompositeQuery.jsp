<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dsrm_record.model.*" %>

<jsp:useBean id="listDsrm_record_ByCompositeQuery" scope="request" type="java.util.List<Dsrm_recordVO>" />

<!DOCTYPE html>
<html>
<head>
<title>複合查詢 - listDsrm_record_ByCompositeQuery.jsp</title>
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
	width: 800px;
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
<body>

<table>
		<tr><td>
			<h3>所有檢舉資料 - list All Dsrm_record.jsp</h3>
			<h4><a href="<%=request.getContextPath()%>/dsrm_record_select_page.jsp">回首頁</a></h4>
		</td></tr>
	</table>
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
		<th>檢舉編號</th>
		<th>潛店編號</th>
		<th>會員編號</th>
		<th>檢舉內容</th>
		<th>檢舉狀態</th>
	</tr>
	<c:forEach var="dsrm_recordVO" items="${listDsrm_record_ByCompositeQuery }">
		<tr align="center" valign="middle">
			<td>${dsrm_recordVO.rdsr_no}</td>
			<td>${dsrm_recordVO.ds_no}</td>
			<td>${dsrm_recordVO.mem_no}</td>
			<td>${dsrm_recordVO.rep_content}</td>
			<td>${dsrm_recordVO.rep_state}</td>
		</tr>	
	</c:forEach>

</body>
</html>
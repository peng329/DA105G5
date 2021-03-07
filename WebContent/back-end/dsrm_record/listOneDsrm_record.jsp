<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dsrm_record.model.*" %>
<%
	Dsrm_recordVO dsrm_recordVO = (Dsrm_recordVO) request.getAttribute("dsrm_recordVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>檢舉紀錄 - listOneDsrm_record.jsp</title>

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
  img{
  	width:150px;
  	height:150px;
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
<table id="table-1">
	<tr><td>
		<h3>檢舉紀錄 - listOneDsrm_record.jsp</h3>
		<h4><a href="<%= request.getContextPath()%>/dsrm_record_select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>檢舉編號</th>
		<th>潛店編號</th>
		<th>會員編號</th>
		<th>檢舉內容</th>
		<th>檢舉狀態</th>
	</tr>
	<tr>
			<td>${dsrm_recordVO.rdsr_no}</td>
			<td>${dsrm_recordVO.ds_no}</td>
			<td>${dsrm_recordVO.mem_no}</td>
			<td>${dsrm_recordVO.rep_content}</td>
			<td>${dsrm_recordVO.rep_state}</td>
	</tr>
</table>
</body>
</html>
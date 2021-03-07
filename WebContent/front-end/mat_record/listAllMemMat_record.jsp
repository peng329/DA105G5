<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mat_record.model.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    
    List<Mat_recordVO> list = (List<Mat_recordVO>)request.getAttribute("list");
    pageContext.setAttribute("list",list);    
%>


<html>
<head>
<title>所有文章追蹤資料 - listAllMat_record.jsp</title>

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
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>文章追蹤資料 - listAllMat_record.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
		<th>會員編號</th>
		<th>文章編號</th>
		<th>追蹤時間</th>

	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="mat_recordVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${mat_recordVO.mem_no}</td>
			<td>${mat_recordVO.mpo_no}</td>
			<td>${mat_recordVO.trac_time}</td>

			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/mat_record/mat_record.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="mem_no"  value="${mat_recordVO.mem_no}">
			      <input type="hidden" name="mpo_no"  value="${mat_recordVO.mpo_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
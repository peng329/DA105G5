<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.lesson.model.*" %>

<jsp:useBean id="listLesson_ByCompositeQuery" scope="request" type="java.util.List<LessonVO>" />

<!DOCTYPE html>
<html>
<head>
<title>複合查詢 - listLesson_ByCompositeQuery.jsp</title>
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
			<h3>所有課程資料 - listAllDiveshop Lesson.jsp</h3>
			<h4><a href="<%=request.getContextPath()%>/lesson_select_page.jsp">回首頁</a></h4>
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
		<th>課程編號</th>
		<th>潛店編號</th>
		<th>潛店名稱</th>
		<th>課程名稱</th>
		<th>課程資訊</th>
		<th>教練名稱</th>
		<th>課程費用</th>
		<th>課程天數</th>
		<th>報名開始日期</th>
		<th>報名結束日期</th>
		<th>課程狀態</th>
		<th>課程人數上限</th>
		<th>課程人數下限</th>
		<th>課程開始日期</th>
		<th>課程結束日期</th>
		<th>課程陳列狀態</th>
	</tr>
	<c:forEach var="lessonVO" items="${listLesson_ByCompositeQuery }">
		<tr align="center" valign="middle">
			<td>${lessonVO.les_no }</td>
			<td>${lessonVO.ds_no }</td>
			<td>${lessonVO.ds_name }</td>
			<td>${lessonVO.les_name }</td>
			<td>${lessonVO.les_info }</td>
			<td>${lessonVO.coach }</td>
			<td>${lessonVO.cost }</td>
			<td>${lessonVO.days }</td>
			<td>${lessonVO.signup_startdate}</td>
			<td>${lessonVO.signup_enddate}</td>
			<td>${lessonVO.les_state}</td>
			<td>${lessonVO.les_max}</td>
			<td>${lessonVO.les_limit}</td>
			<td>${lessonVO.les_startdate}</td>
			<td>${lessonVO.les_enddate}</td>
			<td>${lessonVO.less_state}</td>
		</tr>	
	</c:forEach>

</body>
</html>
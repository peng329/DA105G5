<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.lessonorder.model.*"%>
<% LessonOrderVO lessonOrderVO = (LessonOrderVO) request.getAttribute("lessonOrderVO"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>潛店資料 - listOneDiveshop.jsp</title>

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
		<h3>潛店課程資料 - list One Lesson Order.jsp</h3>
		<h4><a href="<%= request.getContextPath()%>/lessonorder_select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>課程訂單編號</th>
		<th>會員編號</th>
		<th>課程編號</th>
		<th>會員名稱</th>
		<th>課程名稱</th>
		<th>課程費用</th>
		<th>教練</th>
		<th>訂單狀態</th>
	</tr>
	<tr>
			<td>${lessonOrderVO.les_o_no}</td>
			<td>${lessonOrderVO.mem_no}</td>
			<td>${lessonOrderVO.les_no}</td>
			<td>${lessonOrderVO.mem_name}</td>
			<td>${lessonOrderVO.les_name}</td>
			<td>${lessonOrderVO.cost}</td>
			<td>${lessonOrderVO.coach}</td>
			<td>${lessonOrderVO.lo_state}</td>
	</tr>
</table>
</body>
</html>
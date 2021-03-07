<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.friend.model.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    FriendService friendSvc = new FriendService();
	//DeptService ds = new DeptService();

    List<FriendVO> list = friendSvc.getAll();
    //List<FriendVO> list = ds.friends.getAll();
    pageContext.setAttribute("list",list);
    
%>


<html>
<head>
<title>所有員工資料 - listAllFriend.jsp</title>

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
		 <h3>所有會員好友資料 - listAllFriend.jsp</h3>
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
		<th>會員A編號</th>
		<th>會員B編號</th>
		<th>狀態</th>

	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="friendVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${friendVO.mem_no_a}</td>
			<td>${friendVO.mem_no_b}</td>
			<td>${friendVO.fri_state}</td>

		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
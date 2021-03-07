<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.friend.model.*"%>
<%@ page import="com.mem.model.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<% 
	List<FriendVO> list = (List<FriendVO>)request.getAttribute("list");
	
	MemService memSvc = new MemService();
	
	pageContext.setAttribute("memSvc", memSvc);
	pageContext.setAttribute("list", list);
	
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
		 <h3>我的好友 - listAllFriend.jsp</h3>
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
		<th>好友編號</th>
		<th>好友帳號</th>
		<th>好友暱稱</th>
		<th>狀態</th>

	</tr>
	<%@ include file="page1.file" %>
	<c:forEach var="friendVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
		<tr>

			
			<td>${memSvc.getOneMem(friendVO.mem_no_a).mem_no}</td>
			<td>${memSvc.getOneMem(friendVO.mem_no_a).mem_id}</td>
			<td>${memSvc.getOneMem(friendVO.mem_no_a).mem_name}</td>

		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/friend/friend.do" style="margin-bottom: 0px;">
			<td><select size="1" name="fri_state">			
			<option value="待通過" ${(friendVO.fri_state eq '待通過')?'selected':''}>待通過
			<option value="通過" ${(friendVO.fri_state eq '通過')?'selected':''}>通過
			<option value="不通過" ${(friendVO.fri_state eq '不通過')?'selected':''}>不通過
			</select></td>			
			
			
			<td>		  
		     <input type="submit" value="修改">
		     <input type="hidden" name="mem_no"  value="${friendVO.mem_no_b}">
		     <input type="hidden" name="mem_no_a"  value="${friendVO.mem_no_a}">
		     <input type="hidden" name="mem_no_b"  value="${friendVO.mem_no_b}">
		     <input type="hidden" name="action"	value="update"></FORM>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/friend/friend.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="mem_no_a"  value="${friendVO.mem_no_a}">
			     <input type="hidden" name="mem_no_b"  value="${friendVO.mem_no_b}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
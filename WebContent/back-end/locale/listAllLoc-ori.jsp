<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locale.model.*"%>

<jsp:useBean id="locSvc" scope="page" class="com.locale.model.LocService" />
<%

	List<LocVO> list = locSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<html>
<head><title>所有地區 - listAllLoc.jsp</title>

<style>
  table#table-1 {
	background-color: orange;
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有地區 - ListAllLoc.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/dp_home.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
		<th>地區編號</th>
		<th>國家代號</th>
		<th>地區名稱</th>
		<th>天氣</th>
		<th>修改</th>
		<th>刪除<font color=red>(地區核爆-小心)</font></th>
		<th>查詢地區潛點</th>
	</tr>
			<%@ include file="page1.file"%>
	<c:forEach var="locVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${locVO.loc_no}</td>
			<td>${locVO.ctry}</td>
			<td>${locVO.sub_region}</td>
			<td>${locVO.weather}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/locale/loc.do" style="margin-bottom: 0px;">
			    <input type="submit" value="修改"  > 
			    <input type="hidden" name="loc_no" value="${locVO.loc_no}">
			    <input type="hidden" name="action" value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/locale/loc.do" style="margin-bottom: 0px;">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="loc_no" value="${locVO.loc_no}">
			    <input type="hidden" name="action" value="delete_Loc"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/locale/loc.do" style="margin-bottom: 0px;">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="loc_no" value="${locVO.loc_no}">
			    <input type="hidden" name="action" value="listDpsByLocno_B"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
	<%@ include file="page2.file"%>
<%if (request.getAttribute("listDpsByLocno")!=null){%>
       <jsp:include page="listDpsByLocno.jsp" />
<%} %>

</body>
</html>
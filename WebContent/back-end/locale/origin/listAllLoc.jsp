<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locale.model.*"%>

<jsp:useBean id="locSvc" scope="page" class="com.locale.model.LocService" />

<html>
<head>
<title>所有部門 - listAllLoc.jsp</title>

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
		<tr>
			<td>
				<h3>所有部門 - ListAllLoc.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/select_page_locale.jsp"><img
						src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>地區編號</th>
			<th>國家名稱</th>
			<th>子地區/海域</th>
			<th>天氣狀況</th>
			<th>修改</th>
			<th>刪除<font color=red>(關聯測試與交易-小心)</font></th>
			<th>查詢潛點</th>
		</tr>

		<c:forEach var="locVO" items="${locSvc.all}">
			<tr>
				<td>${locVO.loc_no}</td>
				<td>${locVO.ctry}</td>
				<td>${locVO.sub_region}</td>
				<td>${locVO.weather}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/locale/locale.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改" disabled="disabled"> <input
							type="hidden" name="deptno" value="${locVO.loc_no}"> <input
							type="hidden" name="action" value="getOne_For_Update_Loc">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dept/dept.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="deptno" value="${locVO.deptno}"> <input
							type="hidden" name="action" value="delete_Dept">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dept/dept.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="送出查詢"> <input type="hidden"
							name="deptno" value="${locVO.deptno}"> <input
							type="hidden" name="action" value="listEmps_ByDeptno_B">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>

	<%
		if (request.getAttribute("listEmps_ByDeptno") != null) {
	%>
	<jsp:include page="listDps_ByLocno.jsp" />
	<%
		}
	%>

</body>
</html>
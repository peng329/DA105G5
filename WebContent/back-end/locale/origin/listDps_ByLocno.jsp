<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dive_point.model.*"%>

<jsp:useBean id="listDps_ByLocno" scope="request"
	type="java.util.Set<DpVO>" />
<!-- 於EL此行可省略 -->
<jsp:useBean id="locSvc" scope="page" class="com.locale.model.LocService" />


<html>
<head>
<title>地區潛點 - listDps_ByLocno.jsp</title>

<style>
table#table-2 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-2 h4 {
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
	<table id="table-2">
		<tr>
			<td>
				<h3>部門員工 - listDps_ByLocno.jsp</h3>
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
			<th>潛點編號</th>
			<th>地區編號</th>
			<th>潛點名稱</th>
			<th>緯度</th>
			<th>經度</th>
			<th>潛點圖片1</th>
			<th>潛點圖片2</th>
			<th>潛點圖片3</th>
			<th>潛點圖片4</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>

		<c:forEach var="empVO" items="${listDps_ByDeptno}">
			<tr>
				<td>${empVO.empno}</td>
				<td>${empVO.ename}</td>
				<td>${empVO.job}</td>
				<td>${empVO.hiredate}</td>
				<td>${empVO.sal}</td>
				<td>${empVO.comm}</td>
				<td><c:forEach var="deptVO" items="${deptSvc.all}">
						<c:if test="${empVO.deptno==deptVO.deptno}">
	                    ${deptVO.deptno}【<font color=orange>${deptVO.dname}</font> - ${deptVO.loc}】
                    </c:if>
					</c:forEach></td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/emp/emp.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="empno" value="${empVO.empno}"> <input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<!-- 目前尚未用到  -->
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/emp/emp.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="empno" value="${empVO.empno}"> <input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>

	<br>本網頁的路徑:
	<br>
	<b> <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
		<font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%>
	</b>

</body>
</html>
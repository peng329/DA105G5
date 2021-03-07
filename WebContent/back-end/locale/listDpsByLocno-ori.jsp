<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dive_point.model.*"%>

<jsp:useBean id="listDpsByLocno" scope="request"
	type="java.util.Set<DpVO>" />
<!-- 於EL此行可省略 -->
<jsp:useBean id="locSvc" scope="page"
	class="com.locale.model.LocService" />


<html>
<head>
<title>地區潛點 - listDpsByLocno.jsp</title>

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
				<h3>地區潛點 - listDpsByLocno.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/dp_home.jsp"><img
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
			<th>座標緯度</th>
			<th>座標經度</th>
			<th>潛點資訊</th>
			<th>潛點照片1</th>
			<th>潛點照片2</th>
			<th>潛點照片3</th>
			<th>潛點照片4</th>
			<th>修改</th>
			<th>刪除</th>
		</tr>
<!-- 		從controller回來的物件，是Set<dpVO> -->
		<c:forEach var="dpVO" items="${listDpsByLocno}">
			<tr>
				<td>${dpVO.dp_no}</td>
				<td><c:forEach var="locVO" items="${locSvc.all}">
				<c:if test="${locVO.loc_no eq dpVO.loc_no}">
				${locVO.sub_region}</c:if></c:forEach>
				</td>
				<td>${dpVO.dp_name}</td>
				<td>${dpVO.dp_lat}</td>
				<td>${dpVO.dp_lng}</td>
				<td>${dpVO.dp_info}</td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/DBGifReader2.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1"></c:if></td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/DBGifReader2.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2"></c:if></td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/DBGifReader2.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3"></c:if></td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/DBGifReader2.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4"></c:if></td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="dp_no" value="${dpVO.dp_no}"> <input type="hidden"
							name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="dp_no" value="${dpVO.dp_no}"> <input type="hidden"
							name="action" value="delete">
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
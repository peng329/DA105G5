<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dive_point.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	DpService dpSvc = new DpService();
	List<DpVO> list = dpSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="locSvc" scope="page"
	class="com.locale.model.LocService" />
<html>
<head>
<title>所有員工資料 - listAllDp.jsp</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<!-- 	libraies for leaflet -->
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
	integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
	crossorigin="" />
<!-- Make sure you put this AFTER Leaflet's CSS -->
<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
	integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
	crossorigin=""></script>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
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
	<div class="row">
		<div class="col-16">
			<h4>此頁練習採用 EL 的寫法取值:</h4>
			<table id="table-1">
				<tr>
					<td>
						<h3>所有員工資料 - listAllDp.jsp</h3>
						<h4>
							<a href="<%=request.getContextPath()%>/dp_home.jsp"><img
								src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
						</h4>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-6">
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
	</div>
	<div class="row">
		<div class="col-6">
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
				<!-- 以下匯入page1.file，轉交資料 -->
				<%@ include file="page1.file"%>
				<c:forEach var="dpVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">

					<tr>
						<td>${dpVO.dp_no}</td>
						<td><c:forEach var="locVO" items="${locSvc.all}">
								<c:if test="${dpVO.loc_no == locVO.loc_no}">
							${locVO.sub_region}
						</c:if>
							</c:forEach></td>
						<td>${dpVO.dp_name}</td>
						<td>${dpVO.dp_lat}</td>
						<td>${dpVO.dp_lng}</td>
						<td>${dpVO.dp_info}</td>
						<td><c:if test="${dpVO.dp_pic1!= null}">
								<img width="128dp"
									src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1">
							</c:if></td>
						<td><c:if test="${dpVO.dp_pic2!= null}">
								<img width="128dp"
									src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2">
							</c:if></td>
						<td><c:if test="${dpVO.dp_pic3!= null}">
								<img width="128dp"
									src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3">
							</c:if></td>
						<td><c:if test="${dpVO.dp_pic4!= null}">
								<img width="128dp"
									src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4">
							</c:if></td>
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="修改"> <input type="hidden"
									name="dp_no" value="${dpVO.dp_no}"> <input
									type="hidden" name="action" value="getOne_For_Update">
							</FORM>
						</td>
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="刪除"> <input type="hidden"
									name="dp_no" value="${dpVO.dp_no}"> <input
									type="hidden" name="action" value="delete">
							</FORM>
						</td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="page2.file"%>
		</div>
<!-- 		<div class="col-6"> -->
<!-- 			<div id="map"></div> -->
<!-- 		</div> -->
	</div>

	
</body>
</html>
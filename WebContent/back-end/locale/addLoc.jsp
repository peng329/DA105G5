<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.locale.model.*"%>

<%
	LocVO locVO = (LocVO) request.getAttribute("locVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<title>潛點資料新增 - addLoc.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>潛點資料新增 - addLoc.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/dp_home.jsp"><img
						src="images/tomcat.png" width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="loc.do" name="form1"
		>
		<table>
	
			<tr>
				<td>所屬國家：</td>
				<td><input type="TEXT" name="ctry" type="text"
					value="<%=(locVO == null) ? "" : locVO.getCtry()%>" /></td>
			</tr>
			<tr>
				<td>地區名稱：</td>
				<td><input type="TEXT" name="sub_region" size="45"
					value="<%=(locVO == null) ? "請輸入地區名稱" : locVO.getSub_region()%>" /></td>
			</tr>
			<tr>
				<td>天氣資訊：</td>
				<td><input type="TEXT" name="weather" size="45"
					value="<%=(locVO == null) ? "暫無資料" : locVO.getWeather()%>" /></td>
			</tr>

		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
		<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$(input).next().next().attr('src', e.target.result);
					$(input).next().next().show();
				}
				reader.readAsDataURL(input.files[0]);
			} else {
				$(input).next().next().hide();
			}
		}

		$(":input").change(function() {
			readURL(this);
		});
		
		
	</script>
</body>

</html>
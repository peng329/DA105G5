<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dspic.model.*"%>
<%
	DspicVO dspicVO = (DspicVO) request.getAttribute("dspicVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>潛店照片新增 - addDspic.jsp</title>

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

<script>
	var loadFile = function(event) {
		var output = document.getElementById('output');
		output.src = URL.createObjectURL(event.target.files[0]);
	};
</script>

</head>
<body>

	<table id="table-1">
		<tr>
			<td>
				<h3>潛店照片新增 - add Dive Shop Picture.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/dspic_select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<jsp:useBean id="diveshopSvc" scope="page" class="com.diveshop.model.DiveshopService"/>
	<form method="post" enctype="multipart/form-data"
		action="<%=request.getContextPath()%>/dspic/dspic.do" name="form1">
		<table>
			<tr>
				<td>潛店編號:</td>
				
				<td><select size = "1" name="ds_no">
					<c:forEach var="diveshopVO" items="${diveshopSvc.all }">
						<option value="${diveshopVO.ds_no }"/>${diveshopVO.ds_name }
					</c:forEach>
				</select></td>
				
				
<!-- 				<td><input type="text" name="ds_no" size="45" -->
<%-- 					value="<%=(dspicVO == null) ? "DS0001" : dspicVO.getDs_no()%>"></td> --%>
			</tr>
			<tr>
				<td>潛店圖片:</td>
				<td><input type="file" name="dpic" size="45"
					onchange="loadFile(event)"></td>
				<td><input type="submit" value="上傳"></td>
				<td><img id="output" width="150px" height="150px" border="none"></td>
			</tr>
			
		</table>
		<input type="hidden" name="action" value="insert"> <input
			type="submit" value="send">
	</form>
</body>
</html>
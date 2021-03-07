<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dspic.model.*"%>

<jsp:useBean id="dspicSvc" scope="page"
	class="com.dspic.model.DspicService" />

<%
	DspicVO dspicVO = (DspicVO) request.getAttribute("dspicVO");
	//session.setAttribute("wrongImg",dspicVO.getDpic());
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>潛店圖片資料修改 - update Dive Shop Picture.jsp</title>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
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

img {
	width: 150px;
	height: 150px;
}
</style>


</head>
<body>

	<table id="table-1">
		<tr>
			<td>
				<h3>潛店圖片資料修改 - update Dive Shop Picture.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/dspic_select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<form method="post" action="dspic.do" name="form1"
		enctype="multipart/form-data">


		<table>
			<tr>
				<td>照片流水號:<font color=red><b>*</b></font></td>
				<td><input type="hidden" name="dpic_seq" size="45"
					value="<%=dspicVO.getDpic_seq()%>" /><%=dspicVO.getDpic_seq()%></td>
			</tr>
			<tr>
				<td>潛店編號:<font color=red><b>*</b></font></td>
				<td><input type="hidden" name="ds_no" size="45"
					value="<%=dspicVO.getDs_no()%>" /><%=dspicVO.getDs_no()%></td>
			</tr>
			<tr>
				<td>潛店圖片:<font color=red><b>*</b></font></td>
				<td><img
					src="<%= request.getContextPath() %>/dspic/DBGifReader4.do?dpic_seq=${dspicVO.dpic_seq}" /></td>
				<td><input class="ifImgChange" type="file" accept="image/*"
					name="dpic" onchange="loadFile(event)"></td>
				<td><img id="output"
					src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?wrongImg"
					width="150px" height="150px" border="none"></td>

			</tr>
		</table>
		<script>
			var loadFile = function(event) {
				var output = document.getElementById('output');
				output.src = URL.createObjectURL(event.target.files[0]);
			};
		</script>

		<input type="hidden" name="action" value="update"> <input
			type="hidden" name="dpic_seq" value="<%=dspicVO.getDpic_seq()%>">
		<input type="submit" value="send">
	</form>
</body>
</html>
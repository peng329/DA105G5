<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dive_point.model.*"%>

<%
	DpVO dpVO = (DpVO) request.getAttribute("dpVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
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
<title>潛點資料新增 - addDp.jsp</title>

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
<jsp:>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>潛點資料新增 - addDp.jsp</h3>
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

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dive_point/dp.do" name="form1"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>潛點名稱：</td>
				<td><input type="TEXT" name="dp_name" size="45"
					value="<%=(dpVO == null) ? "" : dpVO.getDp_name()%>" /></td>
			</tr>
			<jsp:useBean id="locSvc" scope="page" class="com.locale.model.LocService" />
			<tr>
				<td>地區:<font color=red><b>*</b></font></td>
				<td><select size="1" name="loc_no">
						<c:forEach var="locVO" items="${locSvc.all}">
							<option value="${locVO.loc_no}"
								${(dpVO.loc_no==locVO.loc_no)? 'selected':'' }>${locVO.sub_region}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>經度：</td>
				<td><input type="TEXT" name="dp_lat" type="text"
					value="<%=(dpVO == null) ? "" : dpVO.getDp_lat()%>" /></td>
			</tr>
			<tr>
				<td>緯度：</td>
				<td><input type="TEXT" name="dp_lng" size="45"
					value="<%=(dpVO == null) ? "10000" : dpVO.getDp_lng()%>" /></td>
			</tr>
			<tr>
				<td>潛點資訊：</td>
				<td><input type="TEXT" name="dp_info" size="45"
					value="<%=(dpVO == null) ? "100" : dpVO.getDp_info()%>" /></td>
			</tr>
			<tr>
				<td>潛點圖片1：</td>
				<td><input type="file" name="dp_pic1" size="5242880"
					accept="image/*"><br>
				<img style="display: none" visible="hide" src="" width="230px" /></td>
			</tr>
			<tr>
				<td>潛點圖片2：</td>
				<td><input type="file" name="dp_pic2" size="5242880"
					accept="image/*"><br>
				<img style="display: none" visible="hide" src="" width="230px" /></td>
			</tr>
			<tr>
				<td>潛點圖片3：</td>
				<td><input type="file" name="dp_pic3" size="5242880"
					accept="image/*"><br>
				<img style="display: none" visible="hide" src="" width="230px" /></td>
			</tr>
			<tr>
				<td>潛點圖片4：</td>
				<td><input type="file" name="dp_pic4" size="5242880"
					accept="image/*"><br>
				<img style="display: none" visible="hide" src="" width="230px" /></td>
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
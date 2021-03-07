<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.locale.model.*"%>
<!-- action = "update"檢查過了0208 -->
<!-- 由首頁，listAll進入列表，接著由右側的修改進入controller來這裡，RequestDispatcher的req中有ctrller存入的vo -->
<%
	LocVO locVO = (LocVO) request.getAttribute("locVO"); //EmpServlet.java (Concroller) 存入req的locVO物件 (包括幫忙取出的locVO, 也包括輸入資料錯誤時的locVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>潛點資料修改 - update_Loc_input.jsp</title>

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
				<h3>潛點資料修改 - update_loc_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/select_page_locale.jsp"><img
						src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
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

	<FORM METHOD="post" ACTION="dp.do" name="form1"
	enctype="multipart/form-data">
		<table>
			<tr>
				<td>地區編號：<font color=red><b>*</b></font></td>
				<td><%=locVO.getLoc_no()%></td>
			</tr>
			<tr>
				<td>國家代碼：</td>
				<td><input type="TEXT" name="loc_no" size="45"
					value="<%=locVO.getCtry()%>" /></td>
			</tr>
			<tr>
				<td>子區域/海域：</td>
				<td><input type="TEXT" name="dp_name" size="45"
					value="<%=locVO.getSub_region()%>" /></td>
			</tr>

			<tr>
				<td>天氣資訊</td>
				<td><input type="TEXT" name="dp_info" size="45"
					value="<%=locVO.getWeather()%>" /></td>
			</tr>
		

<%-- 			<jsp:useBean id="dpSvc" scope="page" --%>
<%-- 				class="dive_point.model.DpService" /> --%>
<!-- 			<tr> -->
<!-- 				<td>地區：<font color=red><b>*</b></font></td> -->
<!-- 				<td><select size="1" name="loc_no"> -->
<%-- 						<c:forEach var="locVO" items="${dpSvc.all}"> --%>
<%-- 							<option value="${locVO.loc_no}" --%>
<%-- 								${(locVO.loc_no==locVO.loc_no)?'selected':'' }>${locVO.dp_name} --%>
<%-- 						</c:forEach> --%>
<!-- 				</select></td> -->
<!-- 			</tr> -->

		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="dp_no" value="<%=locVO.getLoc_no()%>"> <input
			type="submit" value="送出修改">
	</FORM>
</body>




</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dsrm_record.model.*"%>
<%
	Dsrm_recordVO dsrm_recordVO = (Dsrm_recordVO) request.getAttribute("dsrm_recordVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
<title>檢舉紀錄新增 - addDsrm_record.jsp</title>

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
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body >
	<table id="table-1">
		<tr>
			<td>
				<h3>檢舉紀錄新增 - addDsrm_record.jsp</h3>
			</td>
			<td>
				<h4><a href="<%=request.getContextPath()%>/dsrm_record_select_page.jsp">回首頁</a></h4>
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

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/dsrm_record/dsrm_record.do" name="form1">

		<table>
			<tr>
				<td>潛店編號:</td>
				<td><input type="TEXT" name="ds_no" size="45" id="ds_no"
					value="<%=(dsrm_recordVO == null) ? "DS0001" : dsrm_recordVO.getDs_no()%>" /></td>
			</tr>
			<tr>
				<td>被會員編號:</td>
				<td><input type="TEXT" name="mem_no" size="45"
					value="<%=(dsrm_recordVO == null) ? "M000001" : dsrm_recordVO.getMem_no()%>" /></td>
			</tr>
			<tr>
				<td>檢舉內容:</td>
				<td><input type="TEXT" name="rep_content" size="45"
					value="<%=(dsrm_recordVO == null) ? "檢舉內容" : dsrm_recordVO.getRep_content()%>" /></td>
			</tr>
			<tr>
				<td>狀態名稱:</td>
				<td><input type="radio" size="1" name="rep_state" value="未審核" id="Unreviewed" checked/>
					<label for="Unreviewed">未審核</label>
					<input type="radio" size="1" name="rep_state" value="審核通過" id="reviewed" disabled="disabled"/>
					<label for="reviewed">審核通過</label>
				</td></tr>
			
		</table>
		
		<br> <input type="hidden" name="action" value="insert"> 
			 <input	type="submit" value="send">
			 
	</FORM>


</body>
</html>
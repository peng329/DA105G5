<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dsrm_record.model.*"%>
<%@ page import="java.util.*"%>
<%
	Dsrm_recordVO dsrm_recordVO = (Dsrm_recordVO) request.getAttribute("dsrm_recordVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>檢舉內容修改 - update_dsrm_record_input.jsp</title>
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
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
		<tr><td>
			<h3>檢舉內容修改 - update_dsrm_record_input.jsp</h3>
				<h4><a href="<%=request.getContextPath()%>/dsrm_record_select_page.jsp">回首頁</a></h4>
		</td></tr>
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
	<jsp:useBean id="dsrm_recordSvc" scope="page"
		class="com.dsrm_record.model.Dsrm_recordService"></jsp:useBean>
	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/dsrm_record/dsrm_record.do"
		name="form1" >
		<table>
			<tr>
				<td>檢舉編號:</td>
				<td><input type="hidden" name="rdsr_no" size="45" id="rdsr_no"
					value="<%=dsrm_recordVO.getRdsr_no()%>" /><%=dsrm_recordVO.getRdsr_no()%></td>
			</tr>
			<tr>
				<td>潛店編號:</td>
				<td><input type="TEXT" name="ds_no" size="45" id="ds_no"
					value="<%=dsrm_recordVO.getDs_no()%>" /></td>
			</tr>
			<tr>
				<td>被會員編號:</td>
				<td><input type="TEXT" name="mem_no" size="45"
					value="<%=dsrm_recordVO.getMem_no()%>" /></td>
			</tr>
			<tr>
				<td>檢舉內容:</td>
				<td><input type="TEXT" name="rep_content" size="45"
					value="<%=dsrm_recordVO.getRep_content()%>" /></td>
			</tr>
			<tr>
				<td>狀態名稱:</td>
				<td><input type="radio" size="1" name="rep_state" value="未審核" id="Unreviewed"
					${(dsrm_recordVO.rep_state=="未審核")? "checked":"" }/>
					<label for="Unreviewed">未審核</label>
					<input type="radio" size="1" name="rep_state" value="審核通過" id="reviewed"
					${(dsrm_recordVO.rep_state=="審核通過")? "checked":"" }/>
					<label for="reviewed">審核通過</label>
				</td></tr>
			
		</table>
		
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="rdsr_no" value="<%=dsrm_recordVO.getRdsr_no()%>">
		<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
		<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  
		<input type="submit" value="送出修改">
	</FORM>
	
	<br>送出修改的來源網頁路徑:<br><b>
   <font color=blue>request.getParameter("requestURL"):</font> <%=request.getParameter("requestURL")%><br>
   <font color=blue>request.getParameter("whichPage"): </font> <%=request.getParameter("whichPage")%> (此範例目前只用於:istAllEmp.jsp))</b>

</body>
</html>
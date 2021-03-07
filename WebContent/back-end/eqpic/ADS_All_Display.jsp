<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.eqpic.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	String ds_no = request.getParameter("ds_no");
	EqpicService eqpicSvc = new EqpicService();
	List<EqpicVO> list = eqpicSvc.findByDsAll(ds_no);
	
	Set<String> set = new LinkedHashSet<String>();
	for (EqpicVO eqpicVO : list) {
		set.add(eqpicVO.getEp_seq());
	}
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("set", set);
%>
<html>
<head>
<title>所有圖片資料 - listADSAllEqpic.jsp</title>

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
	width: 300px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
	border-collapse:collapse;
	border: 1px solid #CCCCFF;
}

table, th, td {
	border: 1px solid #CCCCFF;
	
}

th, td {
	cellpadding:0px;
	border-collapse:collapse;
	padding:5px;
	padding-top:0px;
	padding-bottom:0px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有圖片資料 - listAllEqpic.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
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
			<th>裝備流水號</th>
			<th>圖片</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="ep_seq" items="${set}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${ep_seq}</td>
				<td>
					<table style="border-color: black; border-width: 2px">
						<c:forEach var="eqpicVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
							<c:if test="${ep_seq==eqpicVO.ep_seq}">
								<tr>
									<td>
									<img src="<%=request.getContextPath()%>/PrintPic?epic_seq=${eqpicVO.epic_seq}" width="50px" height="50px">
									 </td>
								 
								 	<td>
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" style="margin-bottom: 0px;">
									     <input type="submit" value="修改">
									     <input type="hidden" name="epic_seq"  value="${eqpicVO.epic_seq}">
									      <input type="hidden" name="action"	value="getOne_For_Update">
									</FORM>
									 </td>
								
									<td>
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" style="margin-bottom: 0px;">
									
									<input type="submit" value="刪除">
									<input type="hidden" name="epic_seq"  value="${eqpicVO.epic_seq}">
									<input type="hidden" name="action"	value="delete">
									<input type="hidden" name="action"  value="${param.ep_seq}">
									
									</FORM>
									 </td>
									
								</tr>
								
							</c:if>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

<script>
</script>
</body>
</html>
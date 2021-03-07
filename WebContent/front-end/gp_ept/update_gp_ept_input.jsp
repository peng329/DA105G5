<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.gp_ept.model.*"%>

<%
	Gp_Ept_VO gp_ept_vo = (Gp_Ept_VO) request.getAttribute("gp_ept_vo"); //Gp_EptServlet.java (Concroller) 存入req的gp_ept_vo物件 (包括幫忙取出的gp_ept_vo, 也包括輸入資料錯誤時的gp_ept_vo物件)
%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->
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
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>員工資料修改 - update_gp_ept_vo_input.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
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

	<FORM METHOD="post" ACTION="gp_ept.do" name="form1"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>揪團裝備明細編號:<font color=red><b>*</b></font></td>
				<td><%=gp_ept_vo.getGp_ept_no()%></td>
			</tr>

			<tr>
				<td>揪團編號:<font color=red><b>*</b></font></td>
				<td><%=gp_ept_vo.getAct_list_no()%></td>
			</tr>
			<tr>
				<td>會員編號:</td>
				<td><%=gp_ept_vo.getMem_no()%></td>
			</tr>
			<tr>
				<td>裝備分類編號:</td>
				<td><%=gp_ept_vo.getEpc_no()%></td>
			</tr>
			<tr>
				<td>數量:</td>
				<td><input type="TEXT" name="gp_t_num" size="45"
					value="<%=gp_ept_vo.getGp_t_num()%>" /></td>
			</tr>
			<tr>
				<td>SIZE:</td>
				<td><input type="TEXT" name="gp_t_size" size="45"
					value="<%=gp_ept_vo.getGp_t_size()%>" /></td>
			</tr>



		</table>
		<br> 
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="gp_ept_no" value="<%=gp_ept_vo.getGp_ept_no()%>"> 
		<input type="hidden" name="act_list_no"	value="<%=gp_ept_vo.getAct_list_no()%>"> 
		<input type="hidden" name="mem_no"	value="<%=gp_ept_vo.getMem_no()%>"> 
		<input type="hidden" name="epc_no"	value="<%=gp_ept_vo.getEpc_no()%>"> 
		<input type="submit" class="btn btn-info " value="送出修改">
	</FORM>
</body>

</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dsrm_record.model.*" %>

<%
	Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
	List<Dsrm_recordVO> list = dsrm_recordSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有潛店檢舉會員紀錄 - list All Dsrm Record.jsp</title>

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
  img{
  width:150px;
  height:150px;
  }
</style>

</head>
<body>


	<table>
		<tr><td>
			<h3>所有潛店檢舉會員紀錄 - list All Dsrm Record.jsp</h3>
			<h4><a href="<%=request.getContextPath()%>/dsrm_record_select_page.jsp">回首頁</a></h4>
		</td></tr>
	</table>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>檢舉編號</th>
		<th>潛店編號</th>
		<th>會員編號</th>
		<th>檢舉內容</th>
		<th>檢舉狀態</th>
	</tr>
	<%@ include file="pages/page1.file" %>
	<c:forEach var="dsrm_recordVO" items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr ${(dsrm_recordVO.rdsr_no==param.rdsr_no) ? 'bgcolor=#CCCCFF':'' }>
			<td>${dsrm_recordVO.rdsr_no}</td>
			<td>${dsrm_recordVO.ds_no}</td>
			<td>${dsrm_recordVO.mem_no}</td>
			<td>${dsrm_recordVO.rep_content}</td>
			<td>${dsrm_recordVO.rep_state}</td>
			
			
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/dsrm_record/dsrm_record.do" style="margin-bottom: 0px;">
					<input type="submit" value="修改">
					<input type="hidden" name="rdsr_no" 		value="${dsrm_recordVO.rdsr_no}">
					<input type="hidden" name="requestURL"  	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller  -->
					<input type="hidden" name="whichPage" 		value="<%=whichPage %>">			  <!-- 送出當前是第幾頁給Controlle -->
					<input type="hidden" name="action" 	  		value="getOne_For_Update">
				</form>
			</td>
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/dsrm_record/dsrm_record.do" style="margin-bottom: 0px;">
					<input type="submit" value="刪除">
					<input type="hidden" name="rdsr_no" 		value="${dsrm_recordVO.rdsr_no}">
					<input type="hidden" name="requestURL"  	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller  -->
					<input type="hidden" name="whichPage" 		value="<%=whichPage%>">			  <!-- 送出當前是第幾頁給Controlle -->
					<input type="hidden" name="action" 			value="delete"></form>
			</td>
		</tr>
	</c:forEach>
	
</table>
<%@ include file="pages/page2.file" %>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> <br>
   <font color=blue>request.getWhichPage(): </font> <%=whichPage%> </b>
</body>
</html>
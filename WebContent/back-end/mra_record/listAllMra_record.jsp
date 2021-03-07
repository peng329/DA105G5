<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mra_record.model.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    Mra_recordService mra_recordSvc = new Mra_recordService();
	//DeptService ds = new DeptService();

    List<Mra_recordVO> list = mra_recordSvc.getAll();
    //List<Mra_recordVO> list = ds.mra_records.getAll();
    pageContext.setAttribute("list",list);   
%>


<html>
<head>
<title>所有檢舉文章資料 - listAllMra_record.jsp</title>

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
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有檢舉文章資料 - listAllMra_record.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
		<th>會員編號</th>
		<th>文章編號</th>
		<th>檢舉時間</th>
		<th>檢舉內容</th>
		<th>處理狀態</th>

	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="mra_recordVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${mra_recordVO.rar_no}</td>
			<td>${mra_recordVO.mem_no}</td>
			<td>${mra_recordVO.mpo_no}</td>
			<td>${mra_recordVO.rep_time}</td>
			<td>${mra_recordVO.rep_content}</td>
			
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/mra_record/mra_record.do" style="margin-bottom: 0px;">			
			<td><select size="1" name="rep_state">			
			<option value="待驗證" ${(mra_recordVO.rep_state eq '待驗證')?'selected':''}>待驗證
			<option value="通過" ${(mra_recordVO.rep_state eq '通過')?'selected':''}>通過
			<option value="不通過" ${(mra_recordVO.rep_state eq '不通過')?'selected':''}>不通過
			</select></td>
			
			<td>
			  
			     <input type="submit" value="修改">
			     <input type="hidden" name="rar_no"  value="${mra_recordVO.rar_no}">
			     <input type="hidden" name="mem_no"  value="${mra_recordVO.mem_no}">
			     <input type="hidden" name="mpo_no"  value="${mra_recordVO.mpo_no}">
			     <input type="hidden" name="rep_time"  value="${mra_recordVO.rep_time}">
			     <input type="hidden" name="rep_content"  value="${mra_recordVO.rep_content}">
			     <input type="hidden" name="action"	value="update"></FORM>
			</td>

		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
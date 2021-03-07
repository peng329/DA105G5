<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.diveshop.model.*" %>
<%@ page import="com.dspic.model.*" %>

<jsp:useBean id="diveshopSvc" scope="page" class="com.diveshop.model.DiveshopService"></jsp:useBean>

<%
	List<DiveshopVO> list = diveshopSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="dspicSvc" scope="page" class="com.dspic.model.DspicService"></jsp:useBean>


<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>所有潛店資料 - listAllDiveshop.jsp</title>

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
			<h3>所有潛店資料 - listAllDiveshop.jsp</h3>
			<h4><a href="<%=request.getContextPath()%>/diveshop_select_page.jsp">回首頁</a></h4>
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
		<th>潛店編號</th>
		<th>潛店名稱</th>
		<th>營業登記證</th>
		<th>潛店電話</th>
		<th>潛店地址</th>
		<th>潛店帳號</th>
		<th>潛店密碼</th>
		<th>潛店信箱</th>
		<th>潛店介紹</th>
		<th>緯經度</th>
		<th>狀態</th>
		<th>潛店圖片</th>
		<th>潛店評分</th>
		<th>潛店檢舉次數</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %>
	<c:forEach var="diveshopVO" items="${list}"  begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr ${(diveshopVO.ds_no==param.ds_no) ? 'bgcolor=#CCCCFF':'' }>
			<td>${diveshopVO.ds_no}</td>
			<td>${diveshopVO.ds_name}</td>
			<td>${diveshopVO.brcid}</td>
			<td>${diveshopVO.tel}</td>
			<td>${diveshopVO.address}</td>
			<td>${diveshopVO.dsaccount}</td>
			<td>${diveshopVO.dspaw}</td>
			<td>${diveshopVO.dsmail}</td>
			<td>${diveshopVO.dsinfo}</td>
			<td>${diveshopVO.ds_latlng}</td>
			<td>${diveshopVO.ds_state}</td>
			<td>				  
				<c:forEach var="dpic_seq" items="${diveshopSvc.getDpic_seqByDs_no(diveshopVO.ds_no)}" >
				
					<img class="dpic" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?dpic_seq=${dpic_seq}"/>				
				</c:forEach>
				
			</td>
			<td>${diveshopVO.ds_ascore}</td>
			<td>${diveshopVO.ds_rep_no}</td>
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/diveshop/diveshop.do" style="margin-bottom: 0px;">
					<input type="submit" value="修改">
					<input type="hidden" name="ds_no" 		value="${diveshopVO.ds_no}">
					<input type="hidden" name="requestURL"  value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller  -->
					<input type="hidden" name="whichPage" 	value="<%=whichPage %>">			  <!-- 送出當前是第幾頁給Controlle -->
					<input type="hidden" name="action" 	  	value="getOne_For_Update">
				</form>
			</td>
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/diveshop/diveshop.do" style="margin-bottom: 0px;">
					<input type="submit" value="刪除">
					<input type="hidden" name="ds_no"		value="${diveshopVO.ds_no}">
					<input type="hidden" name="requestURL"  value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller  -->
					<input type="hidden" name="whichPage" 	value="<%=whichPage%>">			  <!-- 送出當前是第幾頁給Controlle -->
					<input type="hidden" name="action" 		value="delete"></form>
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
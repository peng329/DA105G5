<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.dspic.model.*" %>

<%
	DspicService dspicSvc = new DspicService();
	List<DspicVO> list = dspicSvc.getAll();
	pageContext.setAttribute("list", list);
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有潛店圖片 - listAllDiveshopPic</title>

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
			<h3>所有潛店圖片 - listAllDspic</h3>
			<h4><a href="<%=request.getContextPath()%>/dspic_select_page.jsp">Home</a></h4>
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
		<th>圖片流水號</th>
		<th>潛店編號</th>
		<th>潛店名稱</th>
		<th>圖片</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<jsp:useBean id="diveshopSvc" scope="page" class="com.diveshop.model.DiveshopService"></jsp:useBean>
	<%@ include file="pages/page1.file" %>
	<c:forEach var="dspicVO" items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${dspicVO.dpic_seq }</td>
			<td>${dspicVO.ds_no }</td>
			
			<td><c:forEach var="diveshopVO" items="${diveshopSvc.all }">
					<c:if test="${dspicVO.ds_no == diveshopVO.ds_no }">
						${diveshopVO.ds_no}【 ${diveshopVO.ds_name }】
					</c:if>
				</c:forEach>
			</td>
			
			<td><img src="<%= request.getContextPath() %>/dspic/DBGifReader4.do?dpic_seq=${dspicVO.dpic_seq }"/></td>
			
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/dspic/dspic.do"style="margin-bottom: 0px;">
					<input type="submit" value="修改">
					<input type="hidden" name="dpic_seq" value="${dspicVO.dpic_seq}">
					<input type="hidden" name="action" value="getOne_For_Update">
				</form>
			</td>
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/dspic/dspic.do" style="margin-bottom: 0px;">
					<input type="submit" value="刪除">
					<input type="hidden" name="dpic_seq" value="${dspicVO.dpic_seq}">
					<input type="hidden" name="action" value="delete"></form>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>
</body>
</html>
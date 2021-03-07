<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lesson.model.*" %>
<%@ page import="com.lespic.model.*" %>

<%
	LessonService lessonSvc = new LessonService();
	List<LessonVO> list = lessonSvc.getAll();
	pageContext.setAttribute("list", list);
	
%>
<jsp:useBean id="lespicSvc" scope="page" class="com.lespic.model.LespicService"></jsp:useBean>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有潛店課程 - list All Diveshop Lesson.jsp</title>

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
			<h3>所有課程資料 - listAllDiveshop Lesson.jsp</h3>
			<h4><a href="<%=request.getContextPath()%>/lesson_select_page.jsp">回首頁</a></h4>
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
		<th>課程編號</th>
		<th>潛店編號</th>
		<th>潛店名稱</th>
		<th>課程名稱</th>
		<th>課程資訊</th>
		<th>教練名稱</th>
		<th>課程費用</th>
		<th>課程天數</th>
		<th>報名開始日期</th>
		<th>報名結束日期</th>
		<th>課程狀態</th>
		<th>課程人數上限</th>
		<th>課程人數下限</th>
		<th>課程開始日期</th>
		<th>課程結束日期</th>
		<th>課程陳列狀態</th>
		<th>課程照片</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %>
	<c:forEach var="lessonVO" items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr ${(lessonVO.les_no==param.les_no) ? 'bgcolor=#CCCCFF':'' }>
			<td>${lessonVO.les_no}</td>
			<td>${lessonVO.ds_no}</td>
			<td>${lessonVO.ds_name}</td>
			<td>${lessonVO.les_name}</td>
			<td>${lessonVO.les_info}</td>
			<td>${lessonVO.coach}</td>
			<td>${lessonVO.cost}</td>
			<td>${lessonVO.days}</td>
			<td>${lessonVO.signup_startdate}</td>
			<td>${lessonVO.signup_enddate}</td>
			<td>${lessonVO.les_state}</td>
			<td>${lessonVO.les_max}</td>
			<td>${lessonVO.les_limit}</td>
			<td>${lessonVO.les_startdate}</td>
			<td>${lessonVO.les_enddate}</td>
			<td>${lessonVO.less_state}</td>
			<td>				  
				<c:forEach var="index" begin="0" end="${lespicSvc.getLespicByLes_no(lessonVO.les_no).size()}" >
					<img style="width=150px;height=150px;" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?les_no=${lessonVO.les_no}&index=${index}"/>				
				</c:forEach>
			</td>
			
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/lesson/lesson.do" style="margin-bottom: 0px;">
					<input type="submit" value="修改">
					<input type="hidden" name="les_no" 		value="${lessonVO.les_no}">
					<input type="hidden" name="requestURL"  value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller  -->
					<input type="hidden" name="whichPage" 	value="<%=whichPage %>">			  <!-- 送出當前是第幾頁給Controlle -->
					<input type="hidden" name="action" 	  	value="getOne_For_Update">
				</form>
			</td>
			<td>
				<form METHOD="post" action="<%=request.getContextPath()%>/lesson/lesson.do" style="margin-bottom: 0px;">
					<input type="submit" value="刪除">
					<input type="hidden" name="les_no" 		value="${lessonVO.les_no}">
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
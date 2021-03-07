<%@page import="java.io.PrintWriter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>

<%
	DiveshopVO diveshopVO = (DiveshopVO) request.getAttribute("diveshopVO");
	List<LessonVO> list = (ArrayList<LessonVO>) request.getAttribute("list");
	LessonVO lessonVO = (LessonVO) request.getAttribute("lessonVO");
%>

<jsp:useBean id="dspicSvc" scope="page"
	class="com.dspic.model.DspicService"></jsp:useBean>
<jsp:useBean id="lespicSvc" scope="page"
	class="com.lespic.model.LespicService"></jsp:useBean>
<jsp:useBean id="diveshopSvc" scope="page"
	class="com.diveshop.model.DiveshopService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link
	href="<%=request.getContextPath()%>/front-end/diveshop/listOneDiveshop.css"
	rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>${ds_name}潛店課程</title>



<style>
#diveshop-banner img{
    width: 100%;
}
#dsbg img{
width: 102%;
}
.card {
	width:45%;
}
.card-body button{
	margin:0px;
}
p{
font-family: 'Noto Sans TC', sans-serif;
}
</style>
 
</head>
<body> 
<%@include file="/HeaderFooter/header.jsp"%>  
  <div class="container" style="margin-top:1%;">
   <div class="row"> 
     <%@include file="/front-end/diveshop/dvheader.jsp"%>  
   </div>
   
   <div class="row"> 
    <div class="col-3" style="padding-left:0%;">
     <%@include file="/front-end/diveshop/left-pannel.file"%>
    </div>
    
    <div class="col-9" style="padding-right:0%;">

					<div class="row">
					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>


					<c:forEach var="lessonVO" items="${list}">
						<c:if test="${(lessonVO.less_state) == '上架'}">
						<div class="card mr-4 mt-3">
							<img
								src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?les_no=${lessonVO.les_no}&index=0"
								class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title">${lessonVO.les_name}</h5>
								<p class="card-text">${lessonVO.les_info}</p>
							</div>
							<ul class="list-group list-group-flush">
								<li class="list-group-item"><P>費用:${lessonVO.cost}</P></li>
								<li class="list-group-item"><P>日期:<br>${lessonVO.les_startdate}~${lessonVO.les_enddate}</P></li>
								<li class="list-group-item"><p>${lessonVO.les_state}</p></li>
							</ul>
							<div class="card-body">
								<div class="row">
								<div class="col">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lesson/lesson.do">
									<input type="hidden"  name="les_no" value="${lessonVO.les_no}">
									<input type="hidden"  name="requestURL"     value="<%=request.getServletPath()%>">
									<input type="hidden"  name="ds_no" value="${lessonVO.ds_no}">
									<input type="hidden"  name="action" value="getOne_For_Display">
									<button type="submit" class="btn btn-outline-primary btn-sm">more</button>
								</FORM>
								</div>
								</div>
							</div>
						</div>
					  </c:if>
					</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container" id='fot'>
			<p>
				copyright 2020 BUBBLE UP<br> Take a deep breath<br>
				Contact us : <br>
			</p>
		</div>
	</footer>



</body>
</html>
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
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script type="text/javascript"></script>
<link
	href="<%=request.getContextPath()%>/front-end/diveshop/listOneDiveshop.css"
	rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>潛店課程 - listLessonByDsname.jsp</title>



<style>
.card {
	width:30%;
}
</style>

</head>
<body>
	<header>

		<div class="container" id="con1">
			<nav class="navbar navbar-expand-lg navbar-light bg-white">
				<a class="navbar-brand" href="#"> <img
					src="<%=request.getContextPath()%>/front-end/Logo.jpg">
				</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>


				<div class="collapse navbar-collapse inline"
					id="navbarSupportedContent">
					<ul class="navbar-nav mr-auto inline">
						<li class="nav-item"><a class="nav-link" href="#">潛點資訊</a></li>
						<li class="nav-item"><a class="nav-link" href="#">環境保育</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/front-end/showAllDiveshop.jsp">潛店散策</a>
						</li>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> 潛水社群 </a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="#">Action</a> <a
									class="dropdown-item" href="#">Another action</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">Something else here</a>
							</div></li>
						<li class="nav-item"><a class="nav-link" href="#">揪團潛水</a></li>
						<li class="nav-item"><a class="nav-link" href="#">購物車</a></li>
						<li class="nav-item"><a class="nav-link" href="#">登入</a></li>
					</ul>
				</div>

				<form class="form-inline my-2 my-lg-0">
					<input class="form-control mr-sm-2" type="search"
						placeholder="Search" aria-label="Search" id="Search">
					<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
				</form>
			</nav>
		</div>

	</header>

	<section id="shop-banner">
		<div class="container">
			<div class="row" id="dsbg">
				<div id="bg">
					<img
						src="<%=request.getContextPath()%>/front-end/diveshop/dsbg.jpg"
						alt="">
				</div>
				<div id="logo">
					<img
						src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?ds_no=${ds_no}&index=0"
						alt="...">
				</div>
				<div id="dsname">
					<p>${ds_name}</p>
				</div>
			</div>

		</div>
	</section>

	<section >
		<div class="container">
			<div class="row">
				<div class="col-md-3" id="shop-content">

					<button type="button" class=" btn btn-outline-info btn-lg" id="bt0">潛店介紹</button>

					<button type="button" class=" btn btn-outline-info btn-lg">裝備租賃</button>

					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/lesson/lesson.do">
						<input type="hidden" name="ds_name" value="${ds_name}"> <input
							type="hidden" name="ds_no" value="${ds_no}"> <input
							type="hidden" name="action" value="findByDsname">
						<button type="submit" class=" btn btn-outline-info btn-lg">課程介紹</button>
					</FORM>

					<button type="button" class=" btn btn-outline-info btn-lg ">潛店評級</button>


					<button type="button" class=" btn btn-outline-info btn-lg "
						id="bt6">聯絡潛店</button>
				</div>


				<div class="col-md-9" id="epcontent">
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
						<div class="card mr-4 mt-3">
							<img
								src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?les_no=${lessonVO.les_no}&index=0"
								class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title">${lessonVO.les_name}</h5>
								<p class="card-text">${lessonVO.les_info}</p>
							</div>
							<ul class="list-group list-group-flush">
								<li class="list-group-item">費用:${lessonVO.cost}</li>
								<li class="list-group-item">日期:<br>${lessonVO.les_startdate}~${lessonVO.les_enddate}</li>
								<li class="list-group-item">${lessonVO.les_state}</li>
							</ul>
							<div class="card-body">
								<div class="row">
								<div class="col pr-0"> 
								<form METHOD="post" action="<%=request.getContextPath()%>/lesson/lesson.do">
									<button type="submit" class="btn btn-outline-primary btn-sm">修改</button>
									<input type="hidden"  name="les_no" 		value="${lessonVO.les_no}"> 
									<input type="hidden"  name="requestURL"     value="<%=request.getServletPath()%>"> 
									<input type="hidden"  name="action" 		value="getOne_For_Update">
								</form>
								</div>
								<div class="col pr-0">
								<form METHOD="post" class="mb-2" action="<%=request.getContextPath()%>/lesson/lesson.do"
									style="margin-bottom: 0px;">
									<button type="submit" class="btn btn-outline-primary btn-sm">刪除</button>
									<input type="hidden"  name="les_no"     value="${lessonVO.les_no}"> 
									<input type="hidden"  name="ds_no"     value="${lessonVO.ds_no}">
									<input type="hidden"  name="ds_name"     value="${lessonVO.ds_name}">  
									<input type="hidden"  name="requestURL" value="<%=request.getServletPath()%>"> 
									<input type="hidden"  name="action"     value="delete">
								</form>
								</div>
								<div class="col pr-0">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lesson/lesson.do">
									<input type="hidden"  name="les_no" value="${lessonVO.les_no}">
									<input type="hidden"  name="requestURL"     value="<%=request.getServletPath()%>">
									<input type="hidden"  name="action" value="getOne_For_Display">
									<button type="submit" class="btn btn-outline-primary btn-sm">more</button>
								</FORM>
								</div>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
					<br>本網頁的路徑:<br> <b> <font color=blue>request.getServletPath():</font>
						<%=request.getServletPath()%><br> <font color=blue>request.getRequestURI():
					</font> <%=request.getRequestURI()%><br></b>
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
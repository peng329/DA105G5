<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>
<%@ page import="com.lessonorder.model.*"%>
<%
	LessonOrderVO lessonOrderVO = (LessonOrderVO) request.getAttribute("lessonOrderVO");
%>

<jsp:useBean id="dspicSvc" scope="page"
	class="com.dspic.model.DspicService"></jsp:useBean>
<jsp:useBean id="lespicSvc" scope="page"
	class="com.lespic.model.LespicService"></jsp:useBean>
<jsp:useBean id="diveshopSvc" scope="page"
	class="com.diveshop.model.DiveshopService"></jsp:useBean>
<jsp:useBean id="lessonSvc" scope="page"
	class="com.lesson.model.LessonService"></jsp:useBean>
<jsp:useBean id="lessonorderSvc" scope="page"
	class="com.lessonorder.model.LessonOrderService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<link
	href="<%=request.getContextPath()%>/front-end/diveshop/listOneDiveshop.css"
	rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>潛店課程訂單新增 - addLessonOrder.jsp</title>
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

	<section id="shop-content">
		<div class="container">
			<div class="row">
				<div class="col-md-3" id="btn-row">

					<button type="button" class=" btn btn-outline-info btn-lg" id="bt0">潛店介紹</button>

					<button type="button" class=" btn btn-outline-info btn-lg">裝備租賃</button>

					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/lesson/lesson.do">
						<input type="hidden" name="ds_name" value="${ds_name}"> <input
							type="hidden" name="ds_no" value="<%=lessonOrderVO.getDs_no()%>"> <input
							type="hidden" name="action" value="findByDsname">
						<button type="submit" class=" btn btn-outline-info btn-lg">課程介紹</button>
					</FORM>

					<button type="button" class=" btn btn-outline-info btn-lg ">潛店評級</button>


					<button type="button" class=" btn btn-outline-info btn-lg "
						id="bt6">聯絡潛店</button>
				</div>
				<div class="col-md-9" id="epcontent">

					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
					<form class="form" autocomplete="off" METHOD="post"
						ACTION="<%=request.getContextPath()%>/lessonorder/lessonorder.do">
						<div class="row">
							<div class="col pt-5">
								<h3>請確認報名資訊:</h3>
								<table class="table table-hover">
									<tr>
										<td>潛店編號:</td>
										<td><input type="hidden" name="ds_no"
											value="<%=lessonOrderVO.getDs_no()%>" /><%=lessonOrderVO.getDs_no()%></td>
									</tr>
									<tr>
										<td>會員編號:</td>
										<td><input type="hidden" name="mem_no"
											value="<%=lessonOrderVO.getMem_no()%>" /><%=lessonOrderVO.getMem_no()%></td>
									</tr>
									<tr>
										<td>課程編號:</td>
										<td><input type="hidden" name="les_no"
											value="<%=lessonOrderVO.getLes_no()%>" /><%=lessonOrderVO.getLes_no()%></td>
									</tr>
									<tr>
										<td>會員名稱:</td>
										<td><input type="hidden" name="mem_name"
											value="<%=lessonOrderVO.getMem_name()%>" /><%=lessonOrderVO.getMem_name()%></td>
									</tr>
									<tr>
										<td>課程名稱:</td>
										<td><input type="hidden" name="les_name"
											value="<%=lessonOrderVO.getLes_name()%>" /><%=lessonOrderVO.getLes_name()%></td>
									</tr>
									<tr>
										<td>教練名稱:</td>
										<td><input type="hidden" name="coach"
											value="<%=lessonOrderVO.getCoach()%>" /><%=lessonOrderVO.getCoach()%></td>
									</tr>
									<tr>
										<td>上課費用:</td>
										<td><input type="hidden" name="cost"
											value="<%=lessonOrderVO.getCost()%>" /><%=lessonOrderVO.getCost()%></td>
									</tr>
									<tr>
										<td>訂單狀態:</td>
										<td><input type="hidden" name="lo_state" value="已付款" /><%=lessonOrderVO.getLo_state()%></td>
									</tr>
								</table>

								<input type="hidden"  name="ds_no"   value="${ds_no}" /> 
								<input type="hidden"  name="ds_name" value="${ds_name}" /> <br> 
								<input	type="hidden" name="action"  value="xxxx">
							</div>
							<div class="col">
								<%@ include file="/front-end/checkout/card.jsp"%>
							</div>
						</div>
					</FORM>
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
<script type="text/javascript">
function checkout(){
// 	$(".btn").function(){
// }
	swal("恭喜!","付款成功!!", "success", {button: "完成"});
}
</script>


</body>
</html>




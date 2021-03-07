<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>


<jsp:useBean id="dspicSvc" scope="page"
	class="com.dspic.model.DspicService"></jsp:useBean>
<jsp:useBean id="lespicSvc" scope="page"
	class="com.lespic.model.LespicService"></jsp:useBean>
<jsp:useBean id="diveshopSvc" scope="page"
	class="com.diveshop.model.DiveshopService"></jsp:useBean>
<jsp:useBean id="lessonSvc" scope="page"
	class="com.lesson.model.LessonService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/checkout/css/reset.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/checkout/css/style.css" />
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.3.1/css/all.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/checkout/css/hover.css">
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
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link
	href="<%=request.getContextPath()%>/front-end/diveshop/listOneDiveshop.css"
	rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>潛店課程 - listLessonByDsname.jsp</title>
<style type="text/css">
	@import url('https://fonts.googleapis.com/css?family=Courgette|Noto+Sans+TC:500|Noto+Serif+TC:900|Patua+One&display=swap');
	h2,p{
font-family: 'Noto Sans TC', sans-serif;
}
</style>
</head>
  
<body>
	<%@include file="/HeaderFooter/header.jsp"%>  
	<section id="shop-banner">
		<div class="container">
			<div class="row" id="dsbg">
				<div id="bg">
					<img
						src="<%=request.getContextPath()%>/front-end/diveshop/dsbg.jpg"
						alt="">
				</div>
			</div>

		</div>
	</section>

	<section id="shop-content">
		<div class="container">
			<div class="row">
				<div class="col-md-3" id="btn-row">				
					<%@include file="/front-end/diveshop/left-pannel.file"%>
				</div>

				<div class="col-md-9" id="epcontent">

					<div class="wrap">
						<div
							class="successPage flex flex-row flex-justify-content-center m-3">
							<div
								class="pos-rel w-10 flex flex-justify-content-center flex-align-items-center">
								<div class="successImg w-10 h-10 pos-abs"></div>
								<div class="mx-3 pos-abs w-4">
									<div class="flex flex-column flex-align-items-center ">
										<h2 class="color-darkGreen f-36 my-2">付款成功</h2>
										<div class="w-10">
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/lesson/lesson.do">
												<input type="hidden" name="ds_name" value="${ds_name}">
												<input type="hidden" name="ds_no" value="${ds_no}">
												<input type="hidden" name="requestURL"  value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="findByDsname">
												<button class=" bg-lY-color-dG w-10 p-2 f-24 hvr-ripple-out">繼續逛逛</button>
											</FORM>

										</div>
									</div>
								</div>
							</div>
						</div>
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
<script type="text/javascript">
	$(document).ready(function(){
		swal(
				  '付款成功',
				  '訂單已送交潛店！'
				)
	});
</script>
</html>
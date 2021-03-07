<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>
<%@ page import="com.lessonorder.model.*" %>
<%@ page import="java.lang.*" %>

<jsp:useBean id="dspicSvc"       scope="page" 	class="com.dspic.model.DspicService"></jsp:useBean>
<jsp:useBean id="lespicSvc"      scope="page"	class="com.lespic.model.LespicService"></jsp:useBean>
<jsp:useBean id="diveshopSvc"    scope="page"	class="com.diveshop.model.DiveshopService"></jsp:useBean>
<jsp:useBean id="lessonSvc"      scope="page"	class="com.lesson.model.LessonService"></jsp:useBean>
<jsp:useBean id="lessonorderSvc" scope="page" 	class="com.lessonorder.model.LessonOrderService"></jsp:useBean>
<%
	LessonVO lessonVO =(LessonVO) request.getAttribute("lessonVO");

	if(lessonVO==null){
		 lessonVO=(LessonVO)session.getAttribute("lessonVO");}
	pageContext.setAttribute("lessonVO", lessonVO);
	session.setAttribute("lessonVO", lessonVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dive shop</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/css/BubbleUpStyle.css" rel="stylesheet" type="text/css">
	
<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>

<script src="<%=request.getContextPath()%>/front-end/lesson/datetimepicker/datepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/lesson/datetimepicker/datepicker.zh.js"></script>

<link   href="<%=request.getContextPath()%>/front-end/lesson/datetimepicker/datepicker.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/BubbleUpStyle.css" rel="stylesheet" type="text/css">
<link	href="<%=request.getContextPath()%>/front-end/diveshop/listOneDiveshop.css"	rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>潛店課程 - listLessonByDsname.jsp</title>
<style>
.datepicker--days.datepicker--body.active{
pointer-events:none;
}
.nav-item {
    padding-right: 2.5em;
    padding-left: 2.5em;
	}
	p{
font-family: 'Noto Sans TC', sans-serif;
}
</style>
</head>

<body>
<header>
	<!--js-->

	<!--CSS-->
	<style>
	.nav-item {
    padding-right: 2.5em;
    padding-left: 2.5em;
	}
	
	.user-profile{
	    max-height: 3em;
    	max-width: 3em;
	}
	</style>
	
	<!------------------------------------>
	
	<div class="container-fluid" id="con1">
	 <div class="row justify-content-center">
	<nav class="navbar navbar-expand-lg navbar-light bg-white navbar-scroll ">
		<a class="navbar-brand" href="<%=request.getContextPath()%>/front-end/index.jsp"> 
			<img src="<%=request.getContextPath()%>/HeaderFooter/Logo.jpg"	height=100px width=100px>
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"	data-target="#navbarSupportedContent"	aria-controls="navbarSupportedContent" aria-expanded="false"	aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse inline"	id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto inline">
				<li class="nav-item">
					<a class="nav-link" href="<%=request.getContextPath()%>/front-end/locale/locale.jsp">潛點資訊</a>	
				</li>
				<li class="nav-item">
					 <a class="nav-link" href="<%=request.getContextPath()%>/front-end/diveshop/showAllDiveshop.jsp">潛店散策</a>
				</li>
				<li class="nav-item dropdown">
        			<a class="nav-link dropdown-toggle" href="<%=request.getContextPath()%>/front-end/locale/locale.jsp" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		         	潛水社群
		        	</a>
		        		<div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          			<a class="dropdown-item" href="#">Action</a>
		          			<a class="dropdown-item" href="#">Another action</a>
		          		<div class="dropdown-divider"></div>
		          			<a class="dropdown-item" href="#">Something else here</a>
		        		</div>
		      	</li>
				<li class="nav-item">
					<a class="nav-link"	href="<%=request.getContextPath()%>/front-end/act_list/listAllAct_List1.jsp">揪團趣</a>
				</li>
				
				<%--------------判斷會員是否登入，載入不同的右上角-----------------------------------------%>
				<%--------------未登入-----------------------------------------%>
				<c:if test="${memVO == null}">
			     <li class="nav-item dropdown">
      				<div id="buycar">
    				  <a href="<%=request.getContextPath()%>/front-end/rentcart/Cart.jsp">購物車/</a>
      				  <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
     				      登入
        			  </a>
        				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         					 <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem/memLogin.jsp">會員登入</a>
         				 <div class="dropdown-divider"></div>
         					 <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem/dsLogin.jsp">潛店登入</a>
        				</div>
     				 </div>
    			 </li>
    			 </c:if>
    			 
    			 <%--------------已登入-----------------------------------------%>
    			 <c:if test="${memVO != null}">
    			 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/memcenter.css">
					<link href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css" rel="stylesheet" type="text/css">
					
					 <li class="nav-item dropdown">
      				<div id="buycar">
    				  <a href="<%=request.getContextPath()%>/front-end/rentcart/Cart.jsp">購物車</a>
			      <li class="nav-item avatar dropdown">
			      <a href="#" id="navbarDropdownMenuLink-55" role="button" data-toggle="dropdown" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
			      <span class="navdropdown-title"><div class="user-avatar-wrapper img-circle">
			      <span class=" lazy-load-image-background blur lazy-load-image-loaded" style="background-image:;background-size:;color:transparent;display:inline-block">
			      <img class="border user-profile rounded-circle" src="<%=request.getContextPath()%>/DBGifReader2?mem_no=${memVO.mem_no}" >
			      </span></div>${memVO.mem_name}</span></a>
			        <div class="dropdown-menu dropdown-menu-lg-right dropdown-secondary"
			          aria-labelledby="navbarDropdownMenuLink-55">
			          <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/memCenter/memCenter.jsp">會員頁面</a>
			          <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=memLogout">登出</a>
			        </div>
			      </li>
    			 
    			 </c:if>
			</ul>
			</div>
	</nav>		
	</div>
	</div>
	
</header>

	
<div class="container" style="margin-top:1%;">
   <section id="shop-banner">
		<div class="container">
			<div class="row" id="dsbg">
				<div id="bg">
					<img src="<%=request.getContextPath()%>/front-end/diveshop/dsbg.jpg" alt="">
				</div>
			</div>
		</div>
	</section>
   
   <div class="row"> 
    <div class="col-3" style="padding-left:0%;">
     <%@include file="/front-end/diveshop/left-pannel.file"%>
    </div>
    
    <div class="col-9" style="padding-right:0%;">
    	<div class="row">
					<div class="jumbotron jumbotron-fluid col-md-6"	style="background-color: white;">
						<div class="container">
							<h1 class="display-4">${lessonVO.les_name}</h1>
							<p class="lead">${lessonVO.les_info}</p>
						</div>
					</div>
					<div class="col-md-6">
						
						<div id="carouselExampleSlidesOnly" class="carousel slide"	data-ride="carousel">
						
						<div class="carousel-inner">
							<div class="carousel-item active">
								<c:forEach var="lespic_seq"	items="${lessonSvc.getlespic_seqByLess_no(lessonVO.les_no)}" begin="0" end="0">
									<img style="width: 100%;"
										src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?lpic_seq=${lespic_seq}" />
								</c:forEach>
							</div>

							<c:forEach var="lespic_seq"
								items="${lessonSvc.getlespic_seqByLess_no(lessonVO.les_no)}"
								begin="1">
								<div class="carousel-item">
									<img style="width: 100%;"
										src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?lpic_seq=${lespic_seq}" />
								</div>
							</c:forEach>
						</div>
					</div>
					</div>
				</div>	
					<div class="row">
					<div class="col">
						<div id="datepicker-here" class="datepicker-here" data-language='zh' data-range="true" ></div>
						<div id="custom-cells-events">
							<strong></strong>
							<p></p></div>
						</div>
					<div class="col">
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
					<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/lessonorder/lessonorder.do">
						<h4><p>詳情介紹:</p></h4>
						<p>
							<b>報名日期:</b>${lessonVO.signup_startdate}~${lessonVO.signup_enddate}</p>
						<p>
							<b>開課日期:</b>${lessonVO.les_startdate}~${lessonVO.les_enddate}</p>
						<p>
							<b>上課費用:</b>${lessonVO.cost}</p>
						<p>
							<b>教練:</b>${lessonVO.coach}</p>
						<p>
							<%
								List<LessonOrderVO> signuplist = lessonorderSvc.getLessonOrderByLesno(lessonVO.getLes_no());
								long signupNo = signuplist.stream()
									.filter(p -> p.getLo_state().equals("已付款")).count();
								
							%>
							<b>報名人數:</b><%=signupNo%>/${lessonVO.les_max}</p>
							
							
							<input 
								type="hidden" name="signup_qty" value="${lessonorderSvc.getLessonOrderByLesno(lessonVO.les_no).size()}"/>
							<input 
								type="hidden" name="mem_no" value="${memVO.mem_no}" /> 
							<input
								type="hidden" name="mem_name" value="${memVO.mem_name}" /> 
							<input
								type="hidden" name="ds_no" value="${lessonVO.ds_no}" /> 
							<input
								type="hidden" name="ds_name" value="${lessonVO.ds_name}" /> 
							<input
								type="hidden" name="les_no" value="${lessonVO.les_no}" /> 
							<input
								type="hidden" name="les_name" value="${lessonVO.les_name}" /> 
							<input
								type="hidden" name="coach" value="${lessonVO.coach}" /> 
							<input
								type="hidden" name="cost" value="${lessonVO.cost}" /> 
								
							<input type="hidden" name="requestURL" value="<%=request.getRequestURI()%>"/>
							
							<input
								type="hidden" name="lo_state" value="未付款" /> 
							<input
								type="hidden" name="action" value="getLessonOrder" />
								
							<c:if test="${(lessonVO.les_state) == '結束報名'}">
							<button type="submit" id="signup" class=" btn btn-secondary btn-sm" disabled>報名截止</button>
							</c:if>
							
							<c:if test="${lessonorderSvc.getLessonOrderByLesno(lessonVO.les_no).size()==(lessonVO.les_max)}">
							<button id="signup" class=" btn btn-secondary btn-sm" disabled>人數已滿</button>
							</c:if>
							
<%-- 							<c:if test="${lessonorderSvc.getMem_noByLes_no(lessonVO.les_no).contains(memVO.mem_no)}"> --%>
<!-- 									<button type="submit" id="signup" class=" btn btn-outline-info btn-sm" disabled>已報名</button> -->
<%-- 							</c:if> --%>
							
							<c:if test="${(lessonVO.les_state) == '開放報名' and lessonorderSvc.getLessonOrderByLesno(lessonVO.les_no).size() < lessonVO.les_max}">
								<button type="submit" id="signup" class=" btn btn-outline-info btn-sm">我要報名</button>
							</c:if>
							
						</form>
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
<script>
var signup_start = "${lessonVO.signup_startdate}";
var signup_end = "${lessonVO.signup_enddate}";
var signup_start_month = parseInt(signup_start.substr(5,2))
var signup_start_date = parseInt(signup_start.substr(8));
console.log(signup_start);
var eventDates = [signup_start_date],
$picker = $('#datepicker-here'),
$content = $('#custom-cells-events'),
sentences = [
	'教練:'+'${lessonVO.coach}'+'<br>',
	'費用:'+'${lessonVO.cost}'+'<br>',
	'報名人數:'+'${lessonorderSvc.getLessonOrderByLesno(lessonVO.les_no).size()}'+'/'+'${lessonVO.les_max}'+'<br>',
]
$picker.datepicker({
language: 'en',
onRenderCell: function (date, cellType) {
var currentDate = date.getDate();
if (cellType == 'day' && eventDates.indexOf(currentDate) != -1) {
    return {
        html: currentDate + '<span class="dp-note"></span>'
    }
}
},
})
var currentDate = new Date();
$picker.data('datepicker').selectDate(new Date(signup_start));
$picker.data('datepicker').selectDate(new Date(signup_end));
</script>

</html>
<%@page import="java.util.List"%>
<%@page import="com.lesson.model.LessonVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mdst_record.model.*"%>
<jsp:useBean id="lessonSvc" scope="page" class="com.lesson.model.LessonService"></jsp:useBean>


<%
	DiveshopVO diveshopVO = (DiveshopVO) request.getAttribute("diveshopVO");
  	HttpSession session2 =request.getSession();
  	session2.setAttribute("diveshopVO", diveshopVO);
  	
  	MemService memSrc = new MemService();
  	MemVO memVO = (MemVO)session.getAttribute("memVO"); //
  	
  	Mdst_recordService mdst_recordSvc = new Mdst_recordService();
  	pageContext.setAttribute("mdst_recordSvc",mdst_recordSvc);
%>
<jsp:useBean id="dspicSvc" scope="page"	class="com.dspic.model.DspicService"></jsp:useBean>
<jsp:useBean id="diveshopSvc" scope="page"	class="com.diveshop.model.DiveshopService"></jsp:useBean>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link href="<%=request.getContextPath()%>/front-end/diveshop/listOneDiveshop.css"	rel="stylesheet" type="text/css"/>

<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>


<style type="text/css">
.dpic{
	width:100%;
}
</style>
<title>${diveshopVO.ds_name}</title>
</head>
<%@include file="/HeaderFooter/header.jsp"%>  
<body>
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
						src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?ds_no=${diveshopVO.ds_no}&index=0"
						alt="...">
				</div>
				<div id="dsname">
					<p>${diveshopVO.ds_name}</p>
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
					
					<div class="jumbotron jumbotron-fluid"
						style="background-color: white;">
						<div class="container">
						<div class="display-4">
						<div class="row">
						<div class="col-4">
							<h1 >${diveshopVO.ds_name}</h1>
						</div>
						<div class="col-2">
							<c:if test='${memVO != null}'>
							<c:if test='${ mdst_recordSvc.getOneMdst_record(memVO.mem_no, diveshopVO.ds_no) == null}'>
										<button id="fllow" type="submit" class="btn top_add_fav btn-outline-none"  value="insert" >
										<i class="fa fa-heart-o" aria-hidden="true" style="font-size: 28px; color: red;" id="heart"></i>
										</button>
									</c:if>
									<c:if test='${ mdst_recordSvc.getOneMdst_record(memVO.mem_no, diveshopVO.ds_no) != null}'>
										<button id="fllow" type="submit" class="btn top_add_fav btn-outline-none"  value="delete" >
										<i class="fa fa-heart" aria-hidden="true" style="font-size: 28px; color: red;" id="heart"></i>
										</button>
									</c:if>
							</c:if>		
					<input type="hidden" name="mem_no"  value= ${mem_VO.mem_no} >
			        <input type="hidden" name="ds_no"  value= ${diveshopVO.ds_no} >
			        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
			        	<script type="text/javascript">
			        	
		$(document).ready(function() {
		    $("#fllow").click(function(){
		    	
		    	if($("#fllow").val()==="insert") {
// 		    		$("#fllow").htmlText("");
// 		    		$("#fllow").htmlText("<i class='fa fa-heart' style='font-size: 28px; color: red;'></i>");
					//$(".fa fa-heart-o")
		    		
		    		$("#heart").attr('class','fa fa-heart');
		    		$("#fllow").val("delete");
		    		
		    		var addOrDelete = "ajaxInsert";
		    	}else{
		    		//$("#fllow").htmlText("");
				    //$("#fllow").htmlText("<i class='fa fa-heart-o' style='font-size: 28px; color: red'></i>");
		    		
		    		$("#heart").attr('class','fa fa-heart-o');
		    		$("#fllow").val("insert");
		    		var addOrDelete = "ajaxDelete";
		    	} 
		    	
// 		    	if(this.checked) {
// 		    		var addOrDelete = "insert";		    		
// 		    	}else{
// 		    		var addOrDelete = "delete";		    		
// 		    	}        
		        $.ajax({
					url:'<%=request.getContextPath()%>/front-end/mdst_record/mdst_record.do',
					data:{mem_no:"${memVO.mem_no}", ds_no:"${diveshopVO.ds_no}", action:addOrDelete},
					type: "POST",
					dataType: "json",
					success: function(data){	
					}
				});
			});	   
		    });
		</script>
		</div>					
		</div>					
		</div>					
							
							<p class="lead">${diveshopVO.dsinfo}</p>
						</div>
					</div>
					
					<div id="carouselExampleSlidesOnly" class="carousel slide"
						data-ride="carousel">
						<div class="carousel-inner">
							<div class="carousel-item active">
								<c:forEach var="dpic_seq"
									items="${diveshopSvc.getDpic_seqByDs_no(diveshopVO.ds_no)}"
									begin="0" end="0">
									<img style="width: 100%;"
										src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?dpic_seq=${dpic_seq}" />
								</c:forEach>
							</div>

							<c:forEach var="dpic_seq"
								items="${diveshopSvc.getDpic_seqByDs_no(diveshopVO.ds_no)}"
								begin="1">
								<div class="carousel-item">
									<img style="width: 100%;"
										src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?dpic_seq=${dpic_seq}" />
								</div>
							</c:forEach>
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
</html>

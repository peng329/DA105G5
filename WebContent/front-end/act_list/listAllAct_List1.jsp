<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act_list.model.*"%>


<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	Act_List_JDBCDAO dao = new Act_List_JDBCDAO();
    List<Act_List_VO> list = dao.getAll();
    pageContext.setAttribute("list",list);
    
   
%>


<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->


<style>


	.butrow {
		margin-top: 30px;
	}
	

</style>
<body bgcolor='white'>
<!-- -------------------------------------------------------- -->
	<div class="container-fluid">
		<div class="row clearfix">
			<div class="col-md-12 column">

				<!---------------------------------------------------------------------------------------->

				<div id="myCarousel" class="carousel slide" data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>
					<div class="carousel-inner">
						<div class="carousel-item active">
							<img class="bd-placeholder-img" width="100%" height="380px"
								src="images/fish_underwater.jpg" >
							<div class="container">
								<div class="carousel-caption " style="background:rgba(0,0,0,0.3);border-radius:10px;">
									<h1>海洋生態保育</h1>
									<p>珍貴海洋正面臨前所未有的危機：破壞性捕撈、污染及氣候變遷，對獨特海洋生態構成嚴重威脅。 加入守護海洋，推動成立海洋保護區，讓這片蔚藍生生不息。</p>
									<p><a class="btn btn-info btn-lg btn-primary" href="#" role="button">更多</a></p>
								</div>
							</div>
						</div>
						<div class="carousel-item">
							<img class="bd-placeholder-img" width="100%" height="380px"
								src="images/trash04 (3).jpg">
							<div class="container">
								<div class="carousel-caption" style="background:rgba(0,0,0,0.3);border-radius:10px;">
									<h1>海洋生物的迫害</h1>
									<p>報告指出，在過去，未被妥善運作的流網（drift nets）是禍首，遺失或丟棄的漁具在海中繼續纏捕魚類或其他海洋生物稱之為「幽靈撈捕」，是對海洋生態極大的傷害。
									</p>
									<p><a class="btn btn-info btn-lg btn-primary" href="#" role="button">了解更多</a></p>
								</div>
							</div>
						</div>
						<div class="carousel-item">
							<img class="bd-placeholder-img" width="100%" height="380px"
								src="images/trash05 (3).jpg">
							<div class="container">
								<div class="carousel-caption " style="background:rgba(0,0,0,0.3);border-radius:10px;">
									<h1>海洋垃圾</h1>
									<p>海洋生物很容易被人類的垃圾纏住或困住，美麗的珊瑚與海綿也會被摧毀。更甚者，塑膠袋會被海龜或海豚視為牠們最喜愛的食物-水母或魷魚而吞食，造成牠們窒息或阻塞消化系統。
									</p>
									<p><a class="btn btn-info btn-lg btn-primary" href="#" role="button">詳情</a></p>
								</div>
							</div>
						</div>
					</div>
					<a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>

			</div>
		</div>
		<!---------------------------------------------------------------------------------------->

		<div class="row clearfix">
			<div class="col-lg-2 column">
			</div>
			<!--------------------------------揪團按鈕列---------------------------------------------->
			<%@ include file="grouplistbtn.file" %>

			<!---------------------------------------------------------------------------------------->

			<div class="col-lg-6 column">
				<div class="page-header">
					<h1>
						揪團區
					</h1>
					<hr>
				</div>
			<%@ include file="page1.file" %> 
				<div class="row">
	<c:forEach var="act_list_vo" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<div class="col-md-4">
						<div class="row" style=" margin: 2px; padding: 15px;border:2px #ccc solid;border-radius:10px;">
							<div class="col-md-12">
							<img alt="300x200" style="border:2px #ccc solid;border-radius:10px;" width="100%" height="200"
								src="<%=request.getContextPath()%>/front-end/act_list/DBGifReader?act_list_no=${act_list_vo.act_list_no}">
							</div>
							<div class="caption">
							<div class="row">
							
							<div class="col-md-12">
								<h3>
									團名:${act_list_vo.act_list_name}
								</h3>
							</div>
							</div>
							
								<hr>
								<!-- 會員 -->
								<div class="row">
								<div class="col-md-6">
								<i class="fa fa-user-circle" style="color:#17a2b8"></i>
								<a>團主編號:</a>
								</div>
								<div class="col-md-6">
								<a>${act_list_vo.mem_no}</a>
							
								</div>
								</div>
								<!-- 潛點 -->
								<div class="row">
								<div class="col-md-6">
								<i class="fa fa-swimmer" style="color:#17a2b8"></i>
								<a>潛點編號:</a>
							
								</div>
								<div class="col-md-6">
								<a>${act_list_vo.dp_no}</a>
							
								</div>
								</div>
								<!-- 報名日 -->
								<div class="row">
								
								<div class="col-md-6">
							
								<i class="fa fa-clock" style="color:#17a2b8"></i>
								<a>報名日:</a>
								</div>
								<div class="col-md-6">
								<a>${act_list_vo.start_date} ~ ${act_list_vo.dual_date}</a>
							
								</div>
								</div>
								<!--人數  -->
								<div class="row">
								
								<div class="col-md-6">
								<i class="fa fa-walking" style="color:#17a2b8"></i>
								<a>人數:</a>
							
								</div>
								<div class="col-md-6">
								<a>${act_list_vo.act_min} ~ ${act_list_vo.act_max} 人</a>
							
								</div>
								</div>
								<!-- 揪團介紹 -->
<!-- 								<div class="row"> -->
								
<!-- 								<div class="col-md-6"> -->
<!-- 								<a> -->
<!-- 									揪團介紹: -->
<!-- 								</a> -->
<!-- 								</div> -->
<!-- 								<div class="col-md-6"> -->
<%-- 								<a>${act_list_vo.gp_info}</a> --%>
								
<!-- 								</div> -->
<!-- 								</div> -->
								<!---------------------------->
								<div class="col-md-12">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/act_list/act_list.do" name="form1" >
<!-- 								<p> -->
<!-- 									<a class="btn btn-info float-right" href="#">查看更多</a> -->
									
<!-- 								</p> -->
				
									<button class="btn btn-info btn-block float-right" type="submit" data-toggle="modal"
									data-target="#cart">
									<i class="fa fa-eye"></i> 查看更多</button>
									<input type="hidden" name="action" value="getOne_For_Display">
			     					<input type="hidden" name="act_list_no"  value="${act_list_vo.act_list_no}">

								</FORM>	
								</div>
							</div>
						</div>
					</div>
	</c:forEach>
					
				</div>
				<hr>
			<%@ include file="page2.file" %>
			
			</div>
			<div class="col-lg-2 column"></div>
		</div>
		<div class="row" style="height:10%;background:rgba(0,0,0,0);">
	</div>
	</div>
</body>
	<%@ include file="/HeaderFooter/footer.jsp" %>
</html>
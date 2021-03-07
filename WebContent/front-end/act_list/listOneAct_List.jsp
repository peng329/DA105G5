<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.act_list.model.*"%>
<%@ page import="com.dive_point.model.*"%>


<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Act_List_VO act_list_vo = (Act_List_VO) request.getAttribute("act_list_vo"); //Act_List_Servlet.java(Concroller), 存入req的act_list_vo物件

  DpVO dpVO ; 
  DpService dpSvc = new DpService();
  dpVO = dpSvc.getOneDp(act_list_vo.getDp_no());
  pageContext.setAttribute("dpVO", dpVO);
  
%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCurHnzXYx0tnNK_VhMBgFWTSk8-eH1g2w&callback=initMap"	async defer  ></script>

<style>
.bg{
background-image: url("images/water2.png");
background-size: cover;
background-attachment: fixed;

}
.row{
background-color:white;
/* background:rgba(0,0,0,0.1); */
}


</style>
<script type="text/javascript">
		// 以下google map 區域，相關script請寫在這
		function initMap() {
			var dplat = ${dpVO.dp_lat};
			var dplng = ${dpVO.dp_lng};
			var gLatLng = {
				lat : dplat,
				lng : dplng
			};

			var map = new google.maps.Map(document.getElementById('map'), {
				center : gLatLng,
				zoom : 12
			});
			var marker = new google.maps.Marker({
				position : gLatLng,
				map : map,
				title : '${dpVO.dp_name}'
			});

		}
		// 			以下leaflet區域，相關script請寫在這
		//			下方"L."類似於leafmap 套件的代號，使用這種方式叫出他的套件功能
		//step1:建立地圖，
	</script>
<body>
	<div class="bg">
	<div class="container">
	<div class="row" style="height:7%;background:rgba(0,0,0,0);">
	</div>
		<div class="row">
			<!-- 左邊 -->
			<!-- 團名 -->
			<div class="col-lg-8">
				<!------------------------------------------------------------------------------------------>

				<div class="row align-items-center">
					<div class="col-lg-12">
						<!-- Title -->
						<h1 class="mt-4"><a>${act_list_vo.act_list_name}</a></h1>

						<!-- Author -->
						<p class="lead">
							by
							團主:<a href="#"><td>${act_list_vo.mem_no}</td></a>
						</p>

						<hr>

					</div>

				</div>
				<!------------------------------------------------------------------------------------------>


				<!-- 某團照片 -->
				<img src="<%=request.getContextPath()%>/front-end/act_list/DBGifReader?act_list_no=${act_list_vo.act_list_no}" style="max-heigh:100%;max-width:100%;">


				<hr>

				<!-- 內文 -->

				<blockquote class="blockquote">
				<p>${act_list_vo.gp_info}</p>

					<footer class="blockquote-footer">揪團編號團
						<cite title="Source Title">${act_list_vo.act_list_no}</cite>
					</footer>
				</blockquote>

				<hr>
				<div>
				<h5>揪團地點</h5>
				<a>${act_list_vo.locale}</a>
				</div>
				<hr>
				<h5>潛點地圖</h5>
				<hr>					
					
					
					<div class="row ">					
						<div class="col-lg-8" style="padding:20px;">
						<div id="map" style="width:600px;height:600px;"></div>
						</div>
					<div class="col-lg-12" style="height:100;background:white;">
					</div>
					</div>
			</div>

			<!-- 右邊 -->
			<div class="col-lg-4">
				<!-- 人數 -->
				<div class="card my-3">
					<h5 class="card-header">人數</h5>
					<div class="card-body">
						<div class="row">
							<div class="col-6">
								<ul class="list-unstyled mb-0">
<!-- 									<li> -->
<!-- 										<a>目前人數:</a> -->
<!-- 									</li> -->
									<li>
										<a>上限人數:</a>
									</li>
									<li>
										<a>下限人數</a>
									</li>
								</ul>
							</div>
							<div class="col-6">
								<ul class="list-unstyled mb-0">
<!-- 									<li> -->
<!-- 										<a>0</a> -->
<!-- 									</li> -->
									<li>
										<a>${act_list_vo.act_max}</a>
									</li>
									<li>
										<a>${act_list_vo.act_min}</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!-- 日期 -->
				<div class="card my-4">
					<h5 class="card-header">日期</h5>
					<div class="card-body">
						<div class="row">
							<div class="col-6">
								<ul class="list-unstyled mb-0">
									<li>
										<a>報名起始日:</a>
									</li>
									<li>
										<a>報名截止日:</a>
									</li>
									<li>
										<a>出團日:</a>
									</li>
								</ul>
							</div>
							<div class="col-6">
								<ul class="list-unstyled mb-0">
									<li>
										<a>${act_list_vo.start_date}</a>
									</li>
									<li>
										<a>${act_list_vo.dual_date}</a>
									</li>
									<li>
										<a>${act_list_vo.action_date}</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 入團-->
				<div class="my-4">
				
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/agp_list/agp_list.do" name="form1" >
				
					<button class="btn btn-info btn-block float-right" type="submit" data-toggle="modal"
						data-target="#cart">
						<i class="fa fa-hand-point-right"></i>			入團			<i class="fa fa-hand-point-left"></i></button>
					<input type="hidden" name="action" value="nextgroup">
			     <input type="hidden" name="act_list_no"  value="${act_list_vo.act_list_no}">

				</FORM>		
				</div>
				<div class="my-4"  style="height:100;background:white;"></div>
				</div>

		</div>


	</div>

	<div class="row" style="height:7%;background:rgba(0,0,0,0);">
	</div>
	</div>

</body>



	
</html>
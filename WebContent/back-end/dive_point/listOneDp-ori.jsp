<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.dive_point.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	DpVO dpVO = (DpVO) request.getAttribute("dpVO"); //DpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>潛點資料 - listOneDp.jsp</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
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
 	width: 600px; 
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
	#map{
	width:960;
height:480;
	}
</style>

</head>
<body bgcolor='white'>
<div class="row"> 
<div class="col">
	<table id="table-1">
		<tr>
			<td>
				<h3>潛點資料 - ListOneDp.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath() %>/dp_home.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>
	</div></div>
	<div class= "row">
	<div class= "col">
	<table>
		<tr>
			<th>潛點編號</th>
			<th>地區編號</th>
			<th>潛點名稱</th>
			<th>經度</th>
			<th>緯度</th>
			<th>潛點資訊</th>
			<th>圖片1</th>
			<th>圖片2</th>
			<th>圖片3</th>
			<th>圖片4</th>
			<th>修改</th>
			<th>刪除</th>

		</tr>
		<tr>
			<td><%=dpVO.getDp_no()%></td>
			<td><%=dpVO.getLoc_no()%></td>
			<td><%=dpVO.getDp_name()%></td>
			<td><%=dpVO.getDp_lat()%></td>
			<td><%=dpVO.getDp_lng()%></td>
			<td><%=dpVO.getDp_info()%></td>
			<td><c:if test="${dpVO.dp_pic1!= null}"><img width="230px" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1"/></c:if></td>
			<td><c:if test="${dpVO.dp_pic2!= null}"><img width="230px" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2"/></c:if></td>
			<td><c:if test="${dpVO.dp_pic3!= null}"><img width="230px" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3"/></c:if></td>
			<td><c:if test="${dpVO.dp_pic4!= null}"><img width="230px" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4"/></c:if></td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="dp_no" value="${dpVO.dp_no}"> <input type="hidden"
							name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="dp_no" value="${dpVO.dp_no}"> <input type="hidden"
							name="action" value="delete">
					</FORM>
				</td>
		</tr>
	</table>
	</div>
		</div>
		<div class="row">
						<div class="col-1">
							<div class="col-10">
								<div id="map"></div>
							</div>
						</div>
			</div>
			<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCurHnzXYx0tnNK_VhMBgFWTSk8-eH1g2w&callback=initMap"
		async defer></script>
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
			console.log(dplat, dplng, "myLatLng", gLatLng.lat, gLatLng.lng);//測試地點用	
			var marker = new google.maps.Marker({
				position : gLatLng,
				map : map,
				title : '${dpVO.dp_name}'
			});

		}</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>

<jsp:useBean id="diveshopSvc" scope="page"	class="com.diveshop.model.DiveshopService"></jsp:useBean>

<%
	List<DiveshopVO> list = diveshopSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="dspicSvc" scope="page"	class="com.dspic.model.DspicService"></jsp:useBean>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>潛店散策</title>

<!-- Bootstrap core CSS -->
<!-- Custom styles for this template -->


</head>
<%@include file="/HeaderFooter/header.jsp"%>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">	

<body onload="initMap()">



	<main role="main">

	<section class="">
		<div class="row">
			<div class="container">
				<div class="col" id="map"></div>
				<div class="col"></div>
			</div>
		</div>
	</section>

	<div class="album py-5 bg-light">
		<div class="container">
			<%@ include file="pages/page1.file"%>
			<div class="row">
				<c:forEach var="diveshopVO" items="${list}" begin="<%=pageIndex %>"
					end="<%=pageIndex+rowsPerPage-1%>">
					<div class="card col-md-4 pt-2 mt-3">
						<img
							src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?ds_no=${diveshopVO.ds_no}&index=0"
							class="card-img-top" alt="...">
						<div class="card-body">
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/diveshop/diveshop.do">
								<h5 class="card-title">${diveshopVO.ds_name}</h5>
								<p class="card-text">${diveshopVO.address}</p>
								<input type="hidden" name="ds_no" value="${diveshopVO.ds_no}">
								<input type="hidden" name="action" value="getOne_For_Display">
								<input type="submit" class="btn btn-primary" value="查看潛店資訊">
							</FORM>
						</div>
					</div>
				</c:forEach>


				
			</div>
			<%@ include file="pages/page2.file"%>
		</div>
	</div>

	</main>

	<footer class="navbar-fixed-bottom text-center">
		<div class="container" id='fot'>
			<p>
				copyright 2020 BUBBLE UP<br> Take a deep breath<br>
				Contact us : <br>
			</p>
		</div>
	</footer>
	<script>window.jQuery || document.write('<script src="/docs/4.2/assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
<script>
var map;
var markers = [];
var position = [];
var positionArray = [];
var infowindows = [];
<c:forEach var="diveshopVO" items="${list}">
	var title = "${diveshopVO.ds_name}"
	var latlng = "${diveshopVO.ds_latlng}";
		var x = latlng.indexOf(",");
		var y = latlng.indexOf(")");
		var lat = parseFloat(latlng.substring(1,x));
		var lng = parseFloat(latlng.substring(x+1,y));
		position = {title,lat,lng};
	positionArray.push(position);
	infowindows.push(title);
</c:forEach>

console.log(infowindows);
function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 7,
		center : {
			lat : 24.967841,
			lng : 121.191830
		}
	});
	
	for(var i = 0; i < positionArray.length; i++){
		addMarker(i);
	}
	
	
	
	function addMarker(e){
		markers[e] = new google.maps.Marker({
			position:{
				lat:positionArray[e].lat,
				lng:positionArray[e].lng,
			},
			map:map,
			title:positionArray[e].title
		})
		var a = -1;
		var infowindow = new google.maps.InfoWindow({
			content:infowindows[e]
		})
		markers[e].addListener('click',function(){
			a = a * -1;
			if(a > 0){
				infowindow.open(map, markers[e]);
			}else{
				infowindow.close();
			}
		});
	}

}
</script>

</body>
<script	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCvS_7XU7xlsw0cWtSRBGBe6dPblebLkHM&callback=initMap"	async defer></script>
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.navbar-brand {
	width: 15%;
}

#logo {
	width: 100%;
}

#map {
	width: 100%;
	height: 50vh;
	margin: 0;
	padding: 0;
}
.nav-item{
	width: max-content;
}
#buycar{
	width: max-content;
}
*{
	font-family: 'Noto Serif TC', serif;
}
</style>
</html>
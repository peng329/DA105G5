<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.locale.model.*"%>
<%@page import="com.dive_point.model.*" %>
<%@page import="java.util.*" %>

<%
DpService dpSvc = new DpService();
List<DpVO> dplist = dpSvc.getAll();
pageContext.setAttribute("dplist", dplist);
%>>




<!-- saved from url=(0233)file:///C:/Users/User/Desktop/%E5%B0%88%E6%A1%88/%E7%88%AC%E8%9F%B2%E8%B7%9F%E5%85%B6%E4%BB%96%E5%9F%8E%E5%B8%82/%E8%87%BA%E7%81%A3%E8%BF%91%E6%B5%B7%20_%20%E4%BA%A4%E9%80%9A%E9%83%A8%E4%B8%AD%E5%A4%AE%E6%B0%A3%E8%B1%A1%E5%B1%80.html -->
<html>
<!--<![endif]-->
 <%--------------判斷會員是否登入，載入不同導覽頁-----------------------------------------%>
<head>
 <c:if test="${memVO != null}">
 <% System.out.println("--------------1");%>
 <%@ include file="/HeaderFooter/memHeader.jsp"%>
 
 </c:if>
 
 <c:if test="${memVO == null}">
 <% System.out.println("--------------2");%>
 <%@ include file="/HeaderFooter/header.jsp"%>
 </c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>近海天氣</title>
<!-- Meta -->

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!--Facebook_meta_begin-->
<!-- Facebook -->
<!--Facebook_meta_end-->
<!--tw.gov_meta_begin-->
<!--tw.gov_meta_end-->
<!-- =============== CSS Global Compulsory =============== -->
<!--common_css_begin-->
<meta name="application-name" content="中央氣象局">

<!-- <link rel="stylesheet" href="./locale_files/bootstrap.min.css"> -->
<link rel="stylesheet" href="./locale_files/font-awesome.min.css">
<%-- <script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script> --%>
<link rel="stylesheet" href="./locale_files/main.css">
<!--common_css_end-->


<!-- <link rel="stylesheet" href="./locale_files/main-map.css"> -->
<!-- Latest compiled and minified CSS -->
<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css"> -->
<!-- Latest compiled and minified JavaScript -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script> -->
<!-- <!-- (Optional) Latest compiled and minified JavaScript translation files --> 
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-*.min.js"></script> -->




<style type="text/css">

@import
	url('https://fonts.googleapis.com/css?family=Courgette|Noto+Sans+TC|Noto+Serif+TC|Patua+One&display=swap')
	;

h1 {
	font-family: 'Patua One', cursive, 'Noto Sans TC', sans-serif;
	color: #00dddd;
}

h3,#sub_region {
	font-family: 'Courgette', cursive, 'Noto Sans TC', sans-serif;
	color: #65c4e0;
}

p, desc {
	font-family: 'Courgette', cursive, 'Noto Serif TC', serif;
}
</style>
<style>
.hidden-xs.hidden-sm .stag li:nth-child(even) span:nth-child(3), .stag li:nth-child(even) span:nth-child(4)
	{
	background: #effcfe;
}

.hidden-xs.hidden-sm .stag li span {
	padding: 0.5em;
	width: 31%;
	word-break: break-all;
}


#gmap {
	height: 840px;
}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>


		<script type="text/javascript"> 
		//地圖初始化設定
		let infoWindow;
// 		let gmap;
		let gLatLng;
		let dfcenter
		$(document).ready(navigator.geolocation.watchPosition(
				(curposition) => {
			        console.log(curposition.coords);
			        curlat = curposition.coords.latitude;
			        curlng = curposition.coords.longitude;
				        //地理中心碑
					gLatLng = {	
						lat : curlat,
						lng : curlng
									}
				}));

				var deflat = 23.97384;
				var deflng = 120.9775;
		function initMap() {
// 			if (gmap != null) {
		// 			} else {
		
			 gmap = new google.maps.Map(document.getElementById('gmap'), {
				center :{lat:deflat,lng:deflng},
				zoom : 8,
				styles:[
				    { 	"elementType": "geometry", "stylers": [{ "color": "#1d2c4d" }] },
				    { 	"elementType": "labels.text.fill", "stylers": [{ "color": "#8ec3b9" }] },
				    { 	"elementType": "labels.text.stroke","stylers": [{ "color": "#1a3646" }] },
				    { 	"featureType": "administrative.country",
				    	"elementType": "geometry.stroke",
				    	"stylers": [{ "color": "#4b6878" }]},
				    { 	"featureType": "administrative.land_parcel",
				    	"stylers": [{ "visibility": "off" }]},				    
				    { 	"featureType": "administrative.land_parcel",
				    	"elementType": "labels.text.fill",
				        "stylers": [{ "color": "#64779e" }]},
				    {	"featureType": "administrative.neighborhood",
				        "stylers": [{ "visibility": "off" }]},
				    {	"featureType": "administrative.province",
				        "elementType": "geometry.stroke",
				        "stylers": [{ "color": "#4b6878" }]},
				    {	"featureType": "landscape.man_made",
				        "elementType": "geometry.stroke",
				        "stylers": [{ "color": "#334e87" }]},
				    {	"featureType": "landscape.natural",
				        "elementType": "geometry",
				        "stylers": [{ "color": "#023e58" }]},
				    {	"featureType": "poi",
				        "elementType": "geometry",
				        "stylers": [{ "color": "#283d6a" }]},
				    {	"featureType": "poi",
				        "elementType": "labels.text",
				        "stylers": [{ "visibility": "off" }]},
				    {	"featureType": "poi",
				        "elementType": "labels.text.fill",
				        "stylers": [{ "color": "#6f9ba5" }]},
				    {   "featureType": "poi",
				        "elementType": "labels.text.stroke",
				        "stylers": [{ "color": "#1d2c4d" }]},
				    { 	"featureType": "poi.business", 
				        "stylers": [{ "visibility": "off" }]},
				    {  	"featureType": "poi.park","elementType": "geometry.fill",
				        "stylers": [{ "color": "#023e58" }]},
				    {	"featureType": "poi.park","elementType": "labels.text.fill",
				        "stylers": [{ "color": "#3C7680" }]},
				    {	"featureType": "road","elementType": "geometry",
				        "stylers": [{ "color": "#304a7d" }]},
				    {	"featureType": "road","elementType": "labels",
				        "stylers": [{ "visibility": "off" }]},
				    {	"featureType": "road","elementType": "labels.icon",
				        "stylers": [{ "visibility": "off" }]},
				    {	"featureType": "road","elementType": "labels.text.fill",
				        "stylers": [{ "color": "#98a5be" }]},
				    {	"featureType": "road","elementType": "labels.text.stroke",
				        "stylers": [{ "color": "#1d2c4d" }]},
				    { 	"featureType": "road.arterial", "stylers": [{ "visibility": "off" }] },
				    {	"featureType": "road.highway","elementType": "geometry",
				        "stylers": [{ "color": "#2c6675" }]},
				    {	"featureType": "road.highway","elementType": "geometry.stroke",
				        "stylers": [{ "color": "#255763" }]},
				    {	"featureType": "road.highway","elementType": "labels",
				        "stylers": [{ "visibility": "off" }]},
				    {	"featureType": "road.highway","elementType": "labels.text.fill",
				        "stylers": [{ "color": "#b0d5ce" }]},
				    {	"featureType": "road.highway","elementType": "labels.text.stroke",
				        "stylers": [{ "color": "#023e58" }]},
				    { 	"featureType": "road.local", "stylers": [{ "visibility": "off" }] },
				    { 	"featureType": "transit", "stylers": [{ "visibility": "off" }] },
				    {	"featureType": "transit","elementType": "labels.text.fill",
				        "stylers": [{ "color": "#98a5be" }]},
				    {	"featureType": "transit","elementType": "labels.text.stroke",
				        "stylers": [{ "color": "#1d2c4d" }]},
				    {	"featureType": "transit.line","elementType": "geometry.fill",
				        "stylers": [{ "color": "#283d6a" }]},
				    {	"featureType": "transit.station","elementType": "geometry",
				        "stylers": [{ "color": "#3a4762" }]},
				    {	"featureType": "water","elementType": "geometry",
				        "stylers": [{ "color": "#0e1626" }]},
				    {	"featureType": "water","elementType": "labels.text",
				        "stylers": [{ "visibility": "off" }]},
				    {	"featureType": "water",
				        "elementType": "labels.text.fill",
				        "stylers": [{ "color": "#4e6d70" }] }
				]
			});
// 			defcenter = new google.maps.Marker({
// 				position : gLatLng,
// 				map : gmap,
// 				title : '嗨我在這'
// 			});	
// 	  		});
// 			}
			
		}
	</script>	
</head>

<body>

	<!--=== Header ===-->

	<!--=== End Header ===-->
	<!--=== Breadcrumbs ===-->
	<!--=== End Breadcrumbs ===-->

	<!--=== Content Part ===-->
		<main class="main-content container">
		<div class="row">
		<div class="col-5">
		<div class="row">
			<div class="col">
			<!-- 地圖選單在這喔 -->

					<fieldset>
						<div class="row">
<!-- 							<div class="col-md-1 col-md-offset-3"> -->
							<div class="input-group mb-3">
							<div class="col-3 input-group-prepend">
								<label class="input-group-text" for="inputGroupSelect01"> 請選擇地區</label>
							</div>
								<select class="custom-select" id="inputGroupSelect01" onchange="javascript:getData(value);">
										<option value="LOC000001">彭佳嶼基隆海面</option>
										<option value="LOC000002">釣魚台海面</option>
										<option value="LOC000003">新竹鹿港沿海</option>
										<option value="LOC000004">鹿港東石沿海</option>
										<option value="LOC000005">東石安平沿海</option>
										<option value="LOC000006">安平高雄沿海</option>
										<option value="LOC000007">高雄枋寮沿海</option>
										<option value="LOC000008">枋寮恆春沿海</option>
										<option value="LOC000009">鵝鑾鼻沿海</option>
										<option value="LOC000010">成功臺東沿海</option>
										<option value="LOC000011">臺東大武沿海</option>
										<option value="LOC000012">綠島蘭嶼海面</option>
										<option value="LOC000013">花蓮沿海</option>
										<option value="LOC000014">宜蘭蘇澳沿海</option>
										<option value="LOC000015">澎湖海面</option>
										<option value="LOC000016">馬祖海面</option>
										<option value="LOC000017">金門海面</option>
								</select> <i aria-hidden="true"></i>
							</div>
						</div>
					</fieldset>
			</div>
			<!--Striped Rows 樣式三 資料表格-->
			<div class="row">
		<div class="col">
				<div class="lt-blue-aco">
					<div class="panel-group" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel">
							<div class="panel-heading" role="tab">
								<h4 class="panel-title">
									<a class="collapsed" id="sub_region">彭佳嶼基隆海面</a>
								</h4>
							</div>
						</div>
						<div class="table-scroll">
							<table class="table-v6">
								<caption class="sr-only">臺灣近海預報</caption>
								<tbody>
									<tr>
										<th id="type">日期</th>
										<td id="day1">03/09</td>
										<td id="day2">03/10</td>
										<td id="day3">03/11</td>
									</tr>
									<tr>
										<th id="Weather" headers="type">天氣</th>
										<td headers="Weather day1"><img
											src="./locale_files/33.svg" alt="多雲局部陣雨或雷雨" title="多雲局部陣雨或雷雨"></td>
										<td headers="Weather day2"><img
											src="./locale_files/34.svg" alt="陰時多雲局部陣雨或雷雨"
											title="陰時多雲局部陣雨或雷雨"></td>
										<td headers="Weather day3"><img
											src="./locale_files/29.svg" alt="多雲局部陣雨" title="多雲局部陣雨"></td>
									</tr>
									<tr id="Wind_Dir" headers="type">
										<th>風向</th>
										<td headers="Wind_Dir day1">偏西風</td>
										<td headers="Wind_Dir day2">偏西轉偏北風</td>
										<td headers="Wind_Dir day3">偏北轉偏東風</td>
									</tr>
									<tr id="Wave" headers="type">
										<th>風力（級）</th>
										<td headers="Wave_Height day1">小浪轉中浪</td>
										<td headers="Wave_Height day2">中浪轉大浪</td>
										<td headers="Wave_Height day3">大浪轉中浪</td>
									</tr>
									<tr id="Wave_Height" headers="type">
										<th>浪高</th>
										<td headers="Wind_Force day1">1轉2公尺</td>
										<td headers="Wind_Force day2">2轉3公尺</td>
										<td headers="Wind_Force day3">3轉2公尺</td>
									</tr>
									<tr id="Wind_Force" headers="type">
										<th>海浪</th>
										<td headers="Wave day1">4至5陣風7級晚轉5至6雷雨區陣風9級</td>
										<td headers="Wave day2">5至6雷雨區陣風9級晨轉6至7雷雨區陣風10級</td>
										<td headers="Wave day3">6至7陣風9級晨轉5至6陣風8級</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			
			
			</div></div></div>
			<!--End Striped Rows 樣式三 地圖放這 -->
			<div class="col-md-7">
				<div id="gmap"></div>
			</div>
		
		</div>

		</main>
	<!--/container-->
	<!-- End Content Part -->
	<!--=== Footer ===-->
  <footer id="footer-v3" class="footer-v3">
  <div id="gcse-put-div"></div><div class="copyright">
  <div class="container">
    <div class="row">
      <!-- link Info -->
      <div class="col-md-12">
        <p class="link">
         </div>
      <!-- End link Info-->
      <!-- Address -->
      <!-- End Address -->
      <div id="footer-view-type">
      </div>
    </div>
  </div>
</div>
</footer>
		
		<!--=== End Footer ===-->
		<!--/wrapper-->
		<!-- JS Weather Data -->
		
		<!-- JS Global Compulsory -->
		<!--common_js_begin-->
		<!-- Global site tag (gtag.js) - Google Analytics -->


			<script type="text/javascript">
						let day = [];
			let wxori;
			let jw;
			let months = "一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月".split(",");
			let weekdays = "星期日,星期一,星期二,星期三,星期四,星期五,星期六".split(",");
			let allDp;
			let allLoc;
			let gmap;
			let markers=[];
			let locMarkers=[];
			var centerlng;
			var centerlat;
				//以下非地圖區域，相關script寫在這
				//初期取得allDp、allLoc方便後面利用
				var getAllDp = {
					action : 'getAllDp'
				}
				
				$.ajax({
					url : '<%=request.getContextPath()%>/ajaxDp.do',
					type : 'GET',
					data : getAllDp,
					dataType : 'json',
					error : function(xhr) {
						console.log(xhr);
					},
					success : function(res) {
						allDp=res;
						console.log(res);
						plotAll();
					}
				});
				console.log(2);
		
				var getAllLoc = {
					action : 'getAllLoc'
				};
				
				$.ajax({
					url : '<%=request.getContextPath()%>/ajaxLoc.do',
					type : 'GET',
					data : getAllLoc,
					dataType : 'json',
					error : function(xhr) {
						console.log(xhr);
					},
					success : function(res) {
						allLoc=res;
						console.log(res);
					}
				});
				console.log(2);
				
				$('#test').on(
						'click',
						function() {
							var data = {
								action : 'getOne',
								dp_no : $('#dp_no').val()
							}
							$.ajax({
								url : '/dive_point/dp.do',
								type : 'GET',
								data : data,
								dataType : 'json',
								error : function(xhr) {
									alert('AJAX Error');
									// 			      console.log(xhr.responseText);
								},
								success : function(res) {
									if (res.dp_no) {
										$('#preview').empty();
										for (var i = 1; i <= 4; i++) {
											$('#preview').append(
													'<img class="pic" src="http://localhost:8081/DA105G5/LocPic4.do?dp_no='
															+ res.dp_no
															+ '&dp_pic=dp_pic' + i
															+ '">');
										}
									}
								}
							});
						});
			//..............
			//是天氣資料剖析用
			function predict(){
				day[0]=111;
				console.log(day[0]);
				day[0] = new Array(new Date(jw[0].Wx[0].startTime),
						"<img src=./locale_files/"+jw[0].Wx[0].parameter.parameterValue+".svg alt="+jw[0].Wx[0].parameter.parameterName+" title="+jw[0].Wx[0].parameter.parameterName+">",
						jw[1].WindDir[0].parameter.parameterName,
						jw[2].WindSpeed[0].parameter.parameterName,
						jw[3].WaveHeight[0].parameter.parameterName,
						jw[4].WaveType[0].parameter.parameterName);
				day[1] = new Array(new Date(jw[0].Wx[1].startTime),
						"<img src=./locale_files/"+jw[0].Wx[1].parameter.parameterValue+".svg alt="+jw[0].Wx[1].parameter.parameterName+" title="+jw[0].Wx[1].parameter.parameterName+">",
						jw[1].WindDir[1].parameter.parameterName,
						jw[2].WindSpeed[1].parameter.parameterName,
						jw[3].WaveHeight[1].parameter.parameterName,
						jw[4].WaveType[1].parameter.parameterName);
				day[2] = new Array(new Date(jw[0].Wx[2].startTime),
						"<img src=./locale_files/"+jw[0].Wx[2].parameter.parameterValue+".svg alt="+jw[0].Wx[2].parameter.parameterName+" title="+jw[0].Wx[2].parameter.parameterName+">",
						jw[1].WindDir[2].parameter.parameterName,
						jw[2].WindSpeed[2].parameter.parameterName,
						jw[3].WaveHeight[2].parameter.parameterName,
						jw[4].WaveType[2].parameter.parameterName);
						<c:forEach begin="0" end="2" varStatus="i">
						$("#day${i.count}").text(months[day[${i.index}][0].getMonth()]+","+day[${i.index}][0].getDate()+","+weekdays[day[${i.index}][0].getDay()]);
						$("td[headers*='Weather day${i.count}']").html(day[${i.index}][1]);
						$("td[headers*='Wind_Dir day${i.count}']").text(day[${i.index}][2]);
						$("td[headers*='Wave_Height day${i.count}']").text(day[${i.index}][3]);
						$("td[headers*='Wind_Force day${i.count}']").text(day[${i.index}][4]);
						$("td[headers*='Wave day${i.count}']").text(day[${i.index}][5]);
						</c:forEach>
					}
			// 以下google map 區域，相關script請寫在這	
				function getData(e){//點擊地區時更新天氣
					for(let i = 0; i<allLoc.length;i++){
						if(allLoc[i].loc_no==e){
							wxori = allLoc[i].weather;
							$("#sub_region").text(allLoc[i].sub_region);
							jw = JSON.parse(wxori);
							predict();
						}
					}
					console.log(e);
					clearMarkers();
					getLocDps(e);//順便呼叫比對地區潛點方法
				}
			
			
// 				function getLocDps(loc_no){//比對地區方法，取出地區中的潛點，新陣列locMarkers[];
// 					locMarkers=[];
// 					var j=0;
// 					for(var i=0;i<allDp.length;i++){
// 						if(allDp[i].loc_no==loc_no){
// // 							locMarkers[j]=new google.maps.Marker({
// 							locMarkers.push({
// 								position:{lat:allDp[i].dp_lat,
// 								lng:allDp[i].dp_lng},
// 								map: gmap,
// 								title: allDp[i].dp_name,
// 								data:allDp[i].loc_no});
// 							console.log(locMarkers[j]);
// 							j=j+1;
// 							}
// 						}
// 					if(locMarkers.length!=0){
// 						for(var i=0;i<locMarkers.length;i++){
// 							plotLocMkrs(i);}//呼叫布置地區潛點
// 					}
// 					centerlat=centerlat/(locMarkers.length);
// 					centerlat = parseFloat(centerlat.toFixed(5));
// 					centerlng=centerlng/(locMarkers.length);
// 					centerlng = parseFloat(centerlng.toFixed(5));
					
// 					gmap.setCenter({
// 						lat:centerlat,
// 						lng:centerlng});
					
// 				}
				
// 				var currentInfoWindow = ''; //Global variable
// 				var templat=0;
// 				var tmeplng=0;
// 				var locInf='';
// 				function plotLocMkrs(e){//布置地區的潛點
// 					templat = Math.sqrt((locMarkers[e].lat)*(locMarkers[e].lat));
// 					centerlat =	centerlat+templat;
// 					console.log(centerlat);
// 					templng = Math.sqrt((locMarkers[e].lng)*(locMarkers[e].lng));
// 					centerlng =	centerlng+templng;
// 					console.log(centerlat);
// 				var locInf= new google.maps.InfoWindow({
// 								content:locMarkers[e].data.loc_no
// 								});
// 						locMarkers[e].addListener('click', callback(e));//呼叫callback傳出(i)，這是為了對應在迴圈中註冊事件監聽
// 						};
						
// 					function callback(i){
// 						return function(){
// 							console.log(locInf);
// 							 if(currentInfoWindow != '') {
// 							        currentInfoWindow.close();   
// 							        currentInfoWindow = '';   
// 							  }  
// 							 locInf.open(gmap, locMarkers[i]);
// 							currentInfoWindow=locInf;
// 						};
// 					}
// 				};
			
			
							//以下這段可以直接解開
				function getLocDps(loc_no){//地區比對方法，取出地區中的潛點
					locMarkers=[];
					var j=0;
					for(var i=0;i<allDp.length;i++){
						if(allDp[i].loc_no==loc_no){
// 							locMarkers[j]=new google.maps.Marker({
							locMarkers.push({//locMarkers即是符合條件的所有潛點
								dp_no:allDp[i].dp_no,	
								title:allDp[i].dp_name,
									lat:allDp[i].dp_lat,
									lng:allDp[i].dp_lng});
							console.log(locMarkers[j]);
							j=j+1;
							}
						}
					if(locMarkers.length!=0){
							plotLocMkrs();}//呼叫布置地區潛點
					}
				var currentInfoWindow = ''; //Global variable
				var loc_no='';
				var dp_no='';
				function plotLocMkrs(){//布置地區的潛點
					markers=[];
					console.log(locMarkers.length);
					var lt;
					var lg;
					centerlat=0;
					centerlng=0;	
					for (var i = 0; i < locMarkers.length; i++) {
						lt =locMarkers[i].lat;
						lg =locMarkers[i].lng;
						centerlat =	centerlat+Math.sqrt(lt*lt);
						centerlng =	centerlng+Math.sqrt(lg*lg);
							
						markers[i]=new google.maps.Marker({
								position: {lat:lt,lng:lg},
								map: gmap,
								title:locMarkers[i].title,
								data:locMarkers[i].loc_no
						});
							
							markers[i].addListener('click', callback(i));
						}
				
					function callback(i){
						return function(){
							console.log(locInf);
							var locInf= new google.maps.InfoWindow({
								content:getPic(locMarkers[i].dp_no)
							})
							 if(currentInfoWindow != '') {
							        currentInfoWindow.close();   
							        currentInfoWindow = '';   
							  }  
							 locInf.open(gmap, markers[i]);
							currentInfoWindow=locInf;
							console.log(locInf);

						};
					}
					centerlat=centerlat/(locMarkers.length);
					centerlng=centerlng/(locMarkers.length);
					gmap.setCenter({
						lat:parseFloat(centerlat.toFixed(5)),
						lng:parseFloat(centerlng.toFixed(5))})
				};
			//以上這段可以直接解開
		

		//得到ajax的結果之後展示全部的潛點
		function plotAll(){
			markers=[];
		for (var i = 0; i < allDp.length; i++) {
			addMarker(i);
		}}
		   
		function addMarker(e) {
			markers[e]=new google.maps.Marker({
				position: {
					lat: allDp[e].dp_lat,
					lng: allDp[e].dp_lng
				},
				map: gmap,
				title: allDp[e].dp_name,
				data:allDp[e].loc_no
			});
			var a = -1;
			var infoWindow = new google.maps.InfoWindow({
				content:allDp[e].dp_name
			});
			markers[e].addListener('click',function(){
				console.log(markers[e]);
				 if(currentInfoWindow != '') {
				        currentInfoWindow.close();   
				        currentInfoWindow = '';   
				  }   
				infoWindow.open(gmap,markers[e]);
				 console.log("infoWindow=--------------------------------------"+infoWindow.position);
				currentInfoWindow=infoWindow;
			});
			markers[e].setMap(gmap);
		}
		
// 		google.maps.event.addListener(markers[e], 'click', function()   
// 		   {   
// 		      if(currentInfoWindow != '')   
// 		      {    
// 		        currentInfoWindow.close();   
// 		        currentInfoWindow = '';   
// 		      }   
// 		      var infoWindow = new google.maps.InfoWindow({content: 'COntent'});   
// 		      infowindow.open(map, marker);   
// 		      currentInfoWindow = infowindow;   
// 		});  

// 			var rtnVal
// 		function getMarkerInfo(){//點擊地標時取的資訊
// 			for(var i=0;i<allDp.length;i++){
// 			if(marker.title==allDp[i].dp_name){
// 				rtnVal='<h>'+allDp[i].dp_name+'</h1>'+'<h3>Lat:'+allDp[i].dp_lat+','+allDp[i].dp_lng+'</h3>';
// 			return rtnVal;
// 				}
// 				}
// 				}
		
// 		 var infowindow = new google.maps.InfoWindow({
// 			    content: rtnVal
// 			  });

		//地標開關
// 				let openchk = 1;
// 			  marker.addListener('click',function(){
// 				  openchk = openchk * -1;
// 			    if(openchk > 0){//利用自乘變換開關狀態
// 			      infowindow.open(gmap, marker);
// 			    }else{
// 			      infowindow.close();
// 			    }
// 			  });
			  //以下展示部分區域座標
// 			  function go() {
// 				  clearMarkers();
// 				  for (var i = 0; i < position.length; i++) {
// 				    addMarker(i);
// 				  }
// 				}

			  //現有圖片列表陣列
			  var dpIndex=[
				  <c:forEach var="dpVO" items="${dplist}">
				  '${dpVO.dp_no}',
				  </c:forEach>
			  ]
			  
			  
			 function getPic(e){
				var ind =dpIndex.indexOf(e);
				return picCluster[ind];
			  }
			  let picCluster=[];
			<c:forEach var="dpVO" items="${dplist}">
			var ${dpVO.dp_no}=[
				<c:if test="${dpVO.dp_pic1!= null}">
					'<img width="320dp"	src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1">',
				</c:if>
				<c:if test="${dpVO.dp_pic2!= null}">
					'<img width="320dp" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2">',
					</c:if>
				<c:if test="${dpVO.dp_pic3!= null}">
					'<img width="320dp" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3">',
				</c:if>
				<c:if test="${dpVO.dp_pic4!= null}">
					'<img width="320dp" src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4">',
				</c:if>
							]
			
			picCluster.push(${dpVO.dp_no}.join());				  
			</c:forEach>
				
				
				
			  
			  
			  //點擊得到經緯度
			  var clickOnMap;
		var geolocation = 'https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyCurHnzXYx0tnNK_VhMBgFWTSk8-eH1g2w';
		(function() {
		    xhr = new XMLHttpRequest();
		    xhr.open('POST', geolocation);
		    xhr.onload = function () {
		        var response = JSON.parse(this.responseText);
		        console.log(response);
		        clickOnMap=response;
		    }
		    xhr.send();
		})();
			  
				function closeAllInfo(){
					for(var i =0;i<=markers.length;i++){
						markers[i].infowindow.close();
					}
				}
			  
			  
				//清地標
				function clearMarkers() {
				  for (var i = 0; i < markers.length; i++) {
				    if(markers[i]){
				      markers[i].setMap(null);
				    }
				  }
				  markers = [];
				}
		
				
	</script>
<script async defer	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCurHnzXYx0tnNK_VhMBgFWTSk8-eH1g2w&callback=initMap"></script>
		<script type="text/javascript">
		//圖片按鈕用
		
		</script>
		<!--common_js_end-->
	
<!-- 	//----	Info_Fishery Data	END	-- -->


		<!--[if lt IE 9]>
    <script src="/V8/assets/plugins/respond.js"></script>
    <script src="/V8/assets/plugins/html5shiv.js"></script>
    <script src="/V8/assets/plugins/placeholder-IE-fixes.js"></script>
    <![endif]-->
	<%@ include file="/HeaderFooter/footer.jsp"%>


			
</body>
</html>
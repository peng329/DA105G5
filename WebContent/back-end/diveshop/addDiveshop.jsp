<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>
<%
	DiveshopVO diveshopVO = (DiveshopVO) request.getAttribute("diveshopVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
<title>潛店資料新增 - addDiveshop.jsp</title>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCvS_7XU7xlsw0cWtSRBGBe6dPblebLkHM&callback=initMap"
	async defer>
</script>

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

img {
	width: 200px;
	hieght: 200px;
}
</style>

<style>
#map {
	height: 400px;
	width: 400px;
	margin: 0;
	padding: 0;
}

table {
	width: 800px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>



</head>
<body onload="initMap()">
	<table id="table-1">
		<tr>
			<td>
				<h3>潛店資料新增 - addDiveshop.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/diveshop_select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/diveshop/diveshop.do"
		enctype="multipart/form-data" name="form1">

		<table>
			<tr>
				<td>潛店名稱:</td>
				<td><input type="TEXT" name="ds_name" size="45" id="ds_name"
					value="<%=(diveshopVO == null) ? "XXXX潛水中心" : diveshopVO.getDs_name()%>" /></td>
			</tr>
			<tr>
				<td>營業登記證:</td>
				<td><input type="TEXT" name="brcid" size="45"
					value="<%=(diveshopVO == null) ? "123456" : diveshopVO.getBrcid()%>" /></td>
			</tr>
			<tr>
				<td>電話:</td>
				<td><input type="TEXT" name="tel" size="45"
					value="<%=(diveshopVO == null) ? "123456" : diveshopVO.getTel()%>" /></td>
			</tr>
			<tr>
				<td>地址:</td>
				<td><input name="address" id="address"
					value="<%=(diveshopVO == null) ? "桃園市中壢區中央路300號" : diveshopVO.getAddress()%>"
					class="twaddress" /><br>
					<input type="button" id="submit" value="取得緯經度"></td>
				<td>
				<div id="map"></div>
				</td>
			</tr>
			<tr>
				<td>潛店緯經度:</td>
				<td><input type="TEXT" name="ds_latlng" size="45" id="ds_latlng"
					value="<%=(diveshopVO == null) ? "32.21" : diveshopVO.getDs_latlng()%>" /></td>
			</tr>
			<tr>
				<td>帳號:</td>
				<td><input type="TEXT" name="dsaccount" size="45"
					value="<%=(diveshopVO == null) ? "請輸入帳號" : diveshopVO.getDsaccount()%>" /></td>
			</tr>
			<tr>
				<td>密碼:</td>
				<td><input type="TEXT" name="dspaw" size="45"
					value="<%=(diveshopVO == null) ? "請輸入密碼" : diveshopVO.getDspaw()%>" /></td>
			</tr>
			<tr>
				<td>信箱:</td>
				<td><input type="TEXT" name="dsmail" size="45"
					value="<%=(diveshopVO == null) ? "請輸入信箱" : diveshopVO.getDsmail()%>" /></td>
			</tr>
			<tr>
				<td>潛店介紹:</td>
				<td><input type="TEXT" name="dsinfo" size="45" id="dsinfo"
					value="<%=(diveshopVO == null) ? "請輸入介紹" : diveshopVO.getDsinfo()%>" /></td>
			</tr>
			
			<tr>
				<td><input type="hidden" name="ds_state" size="45"
					value="<%=(diveshopVO == null) ? "未審核" : diveshopVO.getDs_state()%>" /></td>
			</tr>

			<tr>
				<td>潛店圖片:</td>
				<td class="imgBox">
				<input class="uploadBox" type="file" name="addPics" multiple></td>
			</tr>

			<tr>
				<td>
				<input type="hidden" name="ds_ascore" size="45"
					value="<%=(diveshopVO == null) ? "00" : diveshopVO.getDs_ascore()%>" />
				<input type="hidden" name="ds_rep_no" size="45"
					value="<%=(diveshopVO == null) ? "00" : diveshopVO.getDs_rep_no()%>" />
				</td>
			</tr>
		</table>

		<script>
			
		<%@ include file="pages/address.file" %>
			
		$(".uploadBox").change(function(e) {
				var url = null;
				var img = null;
				var length = e.target.files.length; //上傳幾個檔案
				for (let i = 0; i < length; i++) {
					url = URL.createObjectURL(e.target.files[i]);
					img = $("<img>").attr("src", url);
					$(".imgBox").append(img);
				}
			});
		</script>

		<script>
		
			function initMap() {
				var map = new google.maps.Map(document.getElementById('map'), {
					zoom : 15,
					center : {
						lat : 24.967841,
						lng : 121.191830
					}
				});
				var geocoder = new google.maps.Geocoder();
				
				document.getElementById('submit').addEventListener('click',
						function() {
							geocodeAddress(geocoder, map);
						});
			}
			
			function geocodeAddress(geocoder, resultsMap) {	
				var ds_latlng = document.getElementById('ds_latlng');
				var ds_name = document.getElementById('ds_name').value;
		        var address = document.getElementById('address').value;
		        var marker = null;
		        var infowindow = new google.maps.InfoWindow({
		            content: '<h5>' + ds_name + '</h5>'
		          });
		        geocoder.geocode({'address': address}, function(results, status) {
		          if (status === 'OK') {
		            resultsMap.setCenter(results[0].geometry.location);
		            marker = new google.maps.Marker({
		              map: resultsMap,
		              position: results[0].geometry.location,
		              title:ds_name
		            });
		            ds_latlng.value =results[0].geometry.location;
		            
		            var a = -1;
		            marker.addListener('click',function(){
		            	a = a * -1;
		                if(a > 0){
		                  infowindow.open(map, marker);
		                }else{
		                  infowindow.close();
		                }
		              });
		            
		            marker.addListener('click',function(){
		                if(marker.getAnimation()==null){
		                  marker.setAnimation(google.maps.Animation.BOUNCE);
		                }else{
		                  marker.setAnimation(null);
		                }
		              });
		          } else {
		            alert('Geocode was not successful for the following reason: ' + status);
		          }
		        });
		      }
			
		</script>





		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="send">
	</FORM>


</body>
</html>
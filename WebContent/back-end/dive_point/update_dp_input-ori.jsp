<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.dive_point.model.*"%>
<!-- action = "update"檢查過了0208 -->
<!-- 由首頁，listAll進入列表，接著由右側的修改進入controller來這裡，RequestDispatcher的req中有ctrller存入的vo -->
<%
	DpVO dpVO = (DpVO) request.getAttribute("dpVO"); //EmpServlet.java (Concroller) 存入req的dpVO物件 (包括幫忙取出的dpVO, 也包括輸入資料錯誤時的dpVO物件)
%>
<html>
<head>
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
<!-- 	libraies for leaflet -->
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
	integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
	crossorigin="" />
<!-- Make sure you put this AFTER Leaflet's CSS -->
<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
	integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
	crossorigin=""></script>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<title>潛點資料修改</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h6 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h6 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 600dp;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}

#map {
	height: 480;
}

#leafmap {
	height: 480;
}
</style>

</head>

<body bgcolor='white'>
	<div class="container-fluid">
		<table id="table-1">
			<tr>
				<td>
					<h3>潛點資料修改</h3>
					<h6>
						<a href="<%=request.getContextPath()%>/dp_home.jsp"><img
							src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
					</h6>
				</td>
			</tr>
		</table>

		<div class="row">
			<div class="col">
				<h6>資料修改:</h6>
			</div>
		</div>

		<%-- 錯誤表列 --%>
		<div class="row">
			<div class="col-4">
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
		</div>

		<FORM METHOD="post" ACTION="dp.do" name="form1"
			enctype="multipart/form-data">

			<div class="row">
				<div class="col-1">
					<h6>潛點編號：</h6>
					<font color=red><b>*</b>
				</div>
				<div class="col-3">
					<h3><%=dpVO.getDp_no()%></h3>
					</font>
				</div>


				<!-- 				<td height="240dp"><div id="map"></div></td> -->

				<!-- 			<tr> -->
				<!-- 				<td width="95dp">地區編號：</td> -->
				<!-- 				<td><input type="TEXT" name="loc_no" size="35" -->
				<%-- 					value="<%=dpVO.getLoc_no()%>" /></td> --%>
				<!-- 				<td></td> -->
				<!-- 			</tr> -->
				<div class="col-1">
					<h6>地區：</h6>
					<font color=red><b>*</b></font>
				</div>
				<div class="col-3">
					<jsp:useBean id="locSvc" scope="page"
						class="com.locale.model.LocService" />
					<select size="1" name="loc_no">
						<c:forEach var="locVO" items="${locSvc.all}">
							<option value="${locVO.loc_no}"
								${(dpVO.loc_no==locVO.loc_no)?'selected':'' }>${locVO.sub_region}
						</c:forEach>
					</select>
				</div>
				<div class="col-1">
					<h6>潛點名稱：</h6>
				</div>
				<div class="col-3">
					<input type="TEXT" name="dp_name" value="<%=dpVO.getDp_name()%>" />
				</div>
			</div>
			<!-- ------data row 2------- -->
			<div class="row">
				<div class="col-1">
					<h6>緯度：</h6>
				</div>
				<div class="col-3">
					<input type="TEXT" name="dp_lat" value="<%=dpVO.getDp_lat()%>" />
				</div>

				<div class="col-1">
					<h6>經度：</h6>
				</div>
				<div class="col-3">
					<input type="TEXT" name="dp_lng" value="<%=dpVO.getDp_lng()%>" />
				</div>

				<div class="col-1">
					<h6>潛點資訊</h6>
				</div>
				<div class="col-3">
					<input type="TEXT" name="dp_info" value="<%=dpVO.getDp_info()%>" />
				</div>
			</div>
			<br>
			<!-- 		map here -->
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div id="map"></div>
				</div>

			</div>

			<!-- -------data row 3------- -->
			<div class="row">
				<div class="col-3">
					<h6>圖片1:</h6>
					<br>
					<input type="hidden" name="dp_pic1d" value="">
					<button type ="button" class="delbtn">刪除</button>
					<c:if test="${dpVO.dp_pic1!= null}">
						<img name="show_dp_pic1" height="256"
							src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1" />
						<br>
					</c:if>
					<input type="file" name="dp_pic1" id="pic1" accept="image/*"><br>
					<img style="display: none" visible="hide" src="" width="230px" />
				</div>
				
				<div class="col-3">
					<h6>圖片2:</h6>
					<br>
					<input type="hidden" name="dp_pic2d" value="">
					<button type ="button" class="delbtn">刪除</button>
					<c:if test="${dpVO.dp_pic2!= null}">
						<img name="show_dp_pic2" height="256"
							src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2" />
						<br>
					</c:if>
					
					<input type="file" name="dp_pic2" id="pic2" accept="image/*"><br>
					<img style="display: none" visible="hide" src="" width="230px" />
				</div>
				<div class="col-3">
					<h6>圖片3:</h6>
					<br>
					<input type="hidden" name="dp_pic3d" value="">
					<button type="button" class="delbtn">刪除</button>
					<c:if test="${dpVO.dp_pic3!= null}">
						<img name="show_dp_pic3" height="256" visible="hide"
							src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3" />
						<br>
					</c:if>
					
					<input type="file" name="dp_pic3" id="pic3" accept="image/*"><br>
					<img style="display: none" visible="hide" src="" width="230px" />
				</div>
				<div class="col-3">
					<h6>圖片4:</h6>
					<br>
					<input type="hidden" name="dp_pic4d" value="">
					<button type="button" class="delbtn">刪除</button>
					<c:if test="${dpVO.dp_pic4!= null}">
						<img name="show_dp_pic4" height="256" visible="hide"
							src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4" />
						<br>
					</c:if>
					
					<input type="file" name="dp_pic4" id="pic4" accept="image/*"><br>
					<img style="display: none" visible="hide" src="" width="230px" />
				</div>
			</div>
			<br> <input type="hidden" name="action" value="update">
			<input type="hidden" name="dp_no" value="<%=dpVO.getDp_no()%>">
			<input type="submit" value="送出修改">
		</FORM>
	</div>

	<script type="text/javascript">
		//以下非地圖區域，相關script寫在這
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$(input).next().next().attr('src', e.target.result);
					$(input).next().next().show();
					
				}
				reader.readAsDataURL(input.files[0]);
			} else {
				$(input).next().next().hide();
			}
		}
		
		<!--button 刪除-->
			$(".delbtn").click(function(){
				$(this).prev("input:hidden").val("delete");
				$(this).next("img").attr('src',"");
				$(this).next("img").attr('style',"display:none");
			});	
		$(":input").change(function() {
			readURL(this);
		});

		
		
		
	</script>
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
				title : '這是總統府'
			});

		}
		// 			以下leaflet區域，相關script請寫在這
		//			下方"L."類似於leafmap 套件的代號，使用這種方式叫出他的套件功能
		//step1:建立地圖，
	</script>


</body>
</html>
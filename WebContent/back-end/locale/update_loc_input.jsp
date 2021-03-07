<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.locale.model.*"%>
<!-- action = "update"檢查過了0208 -->
<!-- 由首頁，listAll進入列表，接著由右側的修改進入controller來這裡，RequestDispatcher的req中有ctrller存入的vo -->
<%
	LocVO locVO = (LocVO) request.getAttribute("locVO"); //EmpServlet.java (Concroller) 存入req的locVO物件 (包括幫忙取出的locVO, 也包括輸入資料錯誤時的locVO物件)
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
<title>地區資料修改 - update_Loc_input.jsp</title>

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
	width: 600loc;
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

#gmap {
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
					<h3>地區資料修改 - update_loc_input.jsp</h3>
					<h6>
						<a href="<%=request.getContextPath()%>/dp_home.jsp"><img
							src="<%=request.getContextPath()%>/back-end/locale/images/back1.gif"
							width="100" height="32" border="0">回首頁</a>
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

		<FORM METHOD="post" ACTION="loc.do" name="form1">

			<div class="row">
				<div class="col-2">
					<h6>地區編號：</h6>
					<font color=red><b>*</b>
				</div>
				<div class="col-4">
					<h3><%=locVO.getLoc_no()%></h3>
					</font>
				</div>
				<div class="col-2">
					<h6>國家代號：</h6>
				</div>
				<div class="col-4">
					<input type="TEXT" name="ctry" value="<%=locVO.getCtry()%>" />
				</div>

			</div>
			<!-- ------data row 2------- -->
			<div class="row">
				<div class="col-12 col-md-6 col-sm-12 col-lg-6 ">
					<table>
						<tr>
							<td size="25"><h6>地區：</h6> <font color=red><b>*</b></font></td>
							<td><jsp:useBean id="locSvc" scope="page"
									class="com.locale.model.LocService" /> <select size="1"
								name="sub_region">
									<c:forEach var="locVO2" items="${locSvc.all}">
										<option value="${locVO.loc_no}"
											${(locVO.loc_no==locVO2.loc_no)?'selected':'' }>${locVO2.sub_region}
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td>日期</td>
							<th id="d1"></th>
							<th id='d2'></th>
							<th id='d3'></th>
						</tr>
						<tr>
							<td>天氣</td>
							<td id="wx1">
							<td id="wx2"></td>
							<td id="wx3"></td>
						</tr>
						<tr>
							<td>風向</td>
							<td id="wd1"></td>
							<td id="wd2"></td>
							<td id="wd3"></td>
						</tr>
						<tr>
							<td>海浪</td>
							<td id="ws1"></td>
							<td id="ws2"></td>
							<td id="ws3"></td>
						</tr>
						<tr>
							<td>浪高</td>
							<td id="wh1"></td>
							<td id="wh2"></td>
							<td id="wh3"></td>
						</tr>
						<tr>
							<td>風力</td>
							<td id="wt1"></td>
							<td id="wt2"></td>
							<td id="wt3"></td>
						</tr>

					</table>
				</div>

				<div class="col-12 col-md-6 col-sm-12">
					<div id="gmap"></div>
				</div>

			</div>

			<!-- -------data row 3------- -->

			<br> <input type="hidden" name="action" value="update">
			<input type="hidden" name="loc_no" value="<%=locVO.getLoc_no()%>">
			<input type="submit" value="送出修改">
		</FORM>
	</div>

	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCurHnzXYx0tnNK_VhMBgFWTSk8-eH1g2w&callback=initMap"></script>
	<script type="text/javascript">
		//以下非地圖區域，相關script寫在這
		var wxori = '${locVO.weather}';
		var jw = JSON.parse(wxori);

		var day = new Array(3);
		day[0] = new Array(new Date(jw[0].Wx[0].startTime),
				jw[0].Wx[0].parameter.parameterName,
				jw[1].WindDir[0].parameter.parameterName,
				jw[2].WindSpeed[0].parameter.parameterName,
				jw[3].WaveHeight[0].parameter.parameterName,
				jw[4].WaveType[0].parameter.parameterName);
		day[1] = new Array(new Date(jw[0].Wx[1].startTime),
				jw[0].Wx[1].parameter.parameterName,
				jw[1].WindDir[1].parameter.parameterName,
				jw[2].WindSpeed[1].parameter.parameterName,
				jw[3].WaveHeight[1].parameter.parameterName,
				jw[4].WaveType[1].parameter.parameterName);
		day[2] = new Array(new Date(jw[0].Wx[2].startTime),
				jw[0].Wx[2].parameter.parameterName,
				jw[1].WindDir[2].parameter.parameterName,
				jw[2].WindSpeed[2].parameter.parameterName,
				jw[3].WaveHeight[2].parameter.parameterName,
				jw[4].WaveType[2].parameter.parameterName);
		var months = "一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月".split(",");
		var weekdays = "星期日,星期一,星期二,星期三,星期四,星期五,星期六".split(",");
		
		<c:forEach begin="0" end="2" varStatus="i">
		$("#d${i.count}").text(months[day[${i.index}][0].getMonth()]+","+day[${i.index}][0].getDate()+","+weekdays[day[${i.index}][0].getDay()]);
		$("#wx${i.count}").text(day[${i.index}][1]);
		$("#wd${i.count}").text(day[${i.index}][2]);
		$("#ws${i.count}").text(day[${i.index}][3]);
		$("#wh${i.count}").text(day[${i.index}][4]);
		$("#wt${i.count}").text(day[${i.index}][5]);
		</c:forEach>
	
		// 以下google map 區域，相關script請寫在這
		function initMap() {
			var loclat = 24.967742;
			var loclng = 121.1917;

			var gLatLng = {
				lat : loclat,
				lng : loclng
			};

			var gmap = new google.maps.Map(document.getElementById('gmap'), {
				center : gLatLng,
				zoom : 12
			});
			console.log(loclat, loclng, gLatLng.lat, gLatLng.lng);//測試地點用	
			var marker = new google.maps.Marker({
				position : gLatLng,
				map : gmap,
				title : '這是總統府'
			});

		};
	</script>


</body>




</html>
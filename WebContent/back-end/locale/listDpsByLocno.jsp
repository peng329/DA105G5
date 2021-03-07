<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locale.model.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>
<%@ page import="com.dive_point.model.*"%>
<jsp:useBean id="listDpsByLocno" scope="request"
	type="java.util.Set<DpVO>" />
<jsp:useBean id="locSvc" scope="page"
	class="com.locale.model.LocService" />


<%
	List<LocVO> loclist = locSvc.getAll();
	pageContext.setAttribute("loclist", loclist);
	
	WebmasterVO sessionWmVO = (WebmasterVO) session.getAttribute("webmasterVO");
	Authority_manageService amSvc = new Authority_manageService();
	List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
	pageContext.setAttribute("sessionWmVO", sessionWmVO);
	pageContext.setAttribute("fc_noList", fc_noList);

	WebmasterService webmasterSvc = new WebmasterService();

	pageContext.setAttribute("amSvc", amSvc);

	List<WebmasterVO> list = webmasterSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>


<script
	src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>
<link href="<%=request.getContextPath()%>/back-end/locale/css/admin.css"
	rel="stylesheet" type="text/css">

<link href="../../css/style.css" rel="stylesheet" type="text/css">


<style type="text/css">
.navbar {
	background: url("images/background.png") no-repeat center center;
	background-size: cover;
}

.side-bar-logo {
	background: url("images/sidebar_21.png") no-repeat center center;
	background-size: cover;
	opacity: .7;
}

nav {
	opacity: .8;
}
</style>

</head>

<body>
		<!--------------------------------------------- 上邊與側邊欄位 ------------------------------------------------------>

		<!-- --------------------------------右邊內容 -------------------------------------->
			<div class="table-responsive bg-light">

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>

				<div class="container search-table">
					<div class="search-list table-responsive-lg">
						<table class="table table-sm table-striped table-hover">
							<thead class="thead-dark">
								<tr>
									<th>潛點編號</th>
			<th>地區</th>
			<th>潛點名稱</th>
			<th>座標緯度</th>
			<th>座標經度</th>
			<th>潛點資訊</th>
			<th>潛點照片1</th>
			<th>潛點照片2</th>
			<th>潛點照片3</th>
			<th>潛點照片4</th>
			<th>修改</th>
			<th>刪除</th>
								</tr>
							</thead>



							<tbody id="myTable">
								<c:forEach var="dpVO" items="${listDpsByLocno}">
									<tr>
										<td>${dpVO.dp_no}</td>
				<td><c:forEach var="locVO" items="${locSvc.all}">
				<c:if test="${locVO.loc_no eq dpVO.loc_no}">
				${locVO.sub_region}</c:if></c:forEach>
				</td>
				<td>${dpVO.dp_name}</td>
				<td>${dpVO.dp_lat}</td>
				<td>${dpVO.dp_lng}</td>
				<td>${dpVO.dp_info}</td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1"></c:if></td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2"></c:if></td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3"></c:if></td>
				<td><c:if test="${dpVO.dp_pic1!=null}"><img width="128dp"
					src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4"></c:if></td>
				<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
												style="margin-bottom: 0px;">
												<input type="submit" class="btn btn-primary" value="修改"> 
												<input type="hidden" name="dp_no" value="${dpVO.dp_no}">
												  <input type="hidden" name="wm_no"  value="${webmasterVO.wm_no}">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
										</td>
										<td rowsapn="6">
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/dive_point/dp.do"
												style="margin-bottom: 0px;">
												<input type="submit" class="btn btn-danger" value="刪除"> 
												<input type="hidden" name="dp_no" value="${dpVO.dp_no}">
												<input type="hidden" name="wm_no"  value="${webmasterVO.wm_no}">
												<input type="hidden" name="action" value="delete">
											</FORM>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
				</div>
			</div>




	<!-- Optional JavaScript -->

	<script type="text/javascript">
		jQuery(document).ready(function() {
			jQuery("#open-menu").click(function() {
				if (jQuery('#page-container').hasClass('show-menu')) {
					jQuery("#page-container").removeClass('show-menu');
				}

				else {
					jQuery("#page-container").addClass('show-menu');
				}
			});
		});
	</script>
	<script type="text/javascript">
	//以下天氣資料處理，相關script寫在這，var wxori包含全部區域的天氣資料
	var wxoriAll = '';
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
	</c:forEach></script>
</body>
</html>


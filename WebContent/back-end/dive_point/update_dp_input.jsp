<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locale.model.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>
<%@ page import="com.dive_point.model.*"%>
<jsp:useBean id="locSvc" scope="page"
	class="com.locale.model.LocService" />


<%
	DpVO dpVO = (DpVO) request.getAttribute("dpVO"); //EmpServlet.java (Concroller) 存入req的dpVO物件 (包括幫忙取出的dpVO, 也包括輸入資料錯誤時的dpVO物件)
	List<LocVO> loclist = locSvc.getAll();
	pageContext.setAttribute("loclist", loclist);
	pageContext.setAttribute("dpVO", dpVO);
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
<title>潛點資料修改</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>
<link
	href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css"
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
	<div id="page-container" class="main-admin show-menu">
		<!--------------------------------------------- 上邊與側邊欄位 ------------------------------------------------------>
		<%@ include file="/back-end/webmaster/adminSidebar.file"%>

		<!-- --------------------------------右邊內容 -------------------------------------->
		<div class="main-body-content w-100 ets-pt">
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
					<FORM METHOD="post" ACTION="dp.do" name="form1"
									enctype="multipart/form-data" style="margin-bottom: 0px;">
						<table class="table table-sm table-striped table-hover">
							<thead class="thead-dark">
								<tr>
									<th>潛點編號：</th>
									<th>地區：</th>
									<th>潛點名稱：</th>
									<th>緯度：</th>
									<th>經度：</th>
									<th>潛點資訊</th>

								</tr>
							</thead>
							<tbody id="myTable">
								
								<tr>
									<td>${dpVO.dp_no}</td>
									<td><select size="1" name="loc_no">
											<c:forEach var="locVO" items="${loclist}">
												<option value="${locVO.loc_no}"
													${(dpVO.loc_no==locVO.loc_no)?'selected':'' }>${locVO.sub_region}
											</c:forEach>
									</select></td>
									<td><input type="TEXT" name="dp_name"
										value="${dpVO.dp_name}"/></td>
									<td><input type="TEXT" name="dp_lat" 
										value="${dpVO.dp_lat}" /></td>
									<td><input type="TEXT" name="dp_lng"
										value="${dpVO.dp_lng}" /></td>
									<td><input type="TEXT" name="dp_info"
										value="${dpVO.dp_info}" /></td>
									<td></td>
								</tr>

								<tr>
									<th  colspan="3">圖片1:</th>
									<th  colspan="3">圖片2:</th></tr>
								
								<tr>
									<td colspan="3"><input type="hidden" name="dp_pic1d" value="">
										<button type="button" class="delbtn btn btn-danger">刪除</button><br> <c:if
											test="${dpVO.dp_pic1!= null}">
											<img name="show_dp_pic1" height="256"
												src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic1" />
											<br>
										</c:if> <input type="file" name="dp_pic1" id="pic1" class="form-control-file" accept="image/*"><br>
										<img style="display: none" visible="hide" src="" width="480px" />
									</td>
									<td colspan="3"><input type="hidden" name="dp_pic2d"
										value="">
										<button type="button" class="delbtn btn btn-danger">刪除</button><br> <c:if
											test="${dpVO.dp_pic2!= null}">
											<img name="show_dp_pic2" height="256"
												src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic2" />
											<br>
										</c:if> <input type="file" name="dp_pic2" id="pic2" class="form-control-file" accept="image/*"><br>
										<img style="display: none" visible="hide" src="" width="480px" /></td>
								</tr>
								<tr>
									<th colspan="3">圖片3:</th>
									<th colspan="3">圖片4:</th>
								</tr>
									<tr>
									<td colspan="3"><input type="hidden" name="dp_pic3d" value="">
										<button type="button" class="delbtn btn btn-danger">刪除</button><br> <c:if
											test="${dpVO.dp_pic3!= null}">
											<img name="show_dp_pic3" height="256" visible="hide"
												src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic3" />
											<br>
										</c:if> <input type="file" name="dp_pic3" id="pic3" class="form-control-file" accept="image/*"><br>
										<img style="display: none" visible="hide" src="" width="480px" /></td>
									<td colspan="3"><input type="hidden" name="dp_pic4d"
										value="">
										<button type="button" class="delbtn btn btn-danger">刪除</button><br> <c:if
											test="${dpVO.dp_pic4!= null}">
											<img name="show_dp_pic4" height="256" visible="hide"
												src="<%=request.getContextPath()%>/LocPic4.do?dp_no=${dpVO.dp_no}&dp_pic=dp_pic4" />
											<br>
										</c:if> <input type="file" name="dp_pic4" id="pic4" class="form-control-file" accept="image/*"><br>
										<img style="display: none" visible="hide" src="" width="480px" /></td>
									</tr>
								<tr><td colspan="3">
											<input type="submit" class="btn btn-primary" value="送出修改">
											<input type="hidden" name="dp_no" value="${dpVO.dp_no}">
											<input type="hidden" name="wm_no"
												value="${webmasterVO.wm_no}"> <input type="hidden"
												name="action" value="update">
									</td>
								</tr>

							</tbody>
						</table
						></FORM>
					</div>
				</div>
			</div>
		</div>
	</div>




	<!-- Optional JavaScript -->
<script type="text/javascript">
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
		$(this).next().next("img").attr('src',"");
		$(this).next().next("img").attr('style',"display:none");
	});	
$(":input").change(function() {
	readURL(this);
});



</script>
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


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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<title>潛點資料新增</title>
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
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dive_point/dp.do" name="form1"
									enctype="multipart/form-data" style="margin-bottom: 0px;">
						<table class="table table-sm table-striped table-hover">
							<thead class="thead-dark">
								<tr>
								<th>潛點名稱：<font color=red><b>*</b></font></th>
								<th>地區:<font color=red><b>*</b></font></th>
								<th>經度：<font color=red><b>*</b></font></th>
								<th>緯度：<font color=red><b>*</b></font></th>	</tr>
								
							</thead>
							<tbody id="myTable">
								
							<tr>
								<td><input type="TEXT" name="dp_name" 
									value="<%=(dpVO == null) ? "deep blue" : dpVO.getDp_name()%>" /></td>
								<td><select size="1" name="loc_no">
										<c:forEach var="locVO" items="${locSvc.all}">
											<option value="${locVO.loc_no}"
												${(dpVO.loc_no==locVO.loc_no)? 'selected':'' }>${locVO.sub_region}
										</c:forEach>
								</select></td>
								<td><input type="TEXT" name="dp_lat" size="10" 
									value="<%=(dpVO == null) ? "23.027307" : dpVO.getDp_lat()%>" /></td>
								<td><input type="TEXT" name="dp_lng" size="10" 
									value="<%=(dpVO == null) ? "120.42167" : dpVO.getDp_lng()%>" /></td>
								</tr>
								<tr><th colspan="4">潛點資訊：</th>
								</tr><tr>
								<td colspan="4"><input type="TEXT" name="dp_info" var="hello"
									value="<%=(dpVO == null) ? "100" : dpVO.getDp_info()%>" /></td>
								</tr>
								<tr>
									<th colspan="2">圖片1:</th>
									<th colspan="2">圖片2:</th>
									</tr>
								<tr>
									<td colspan="2"><input type="file" name="dp_pic1" size="5242880"
									accept="image/*"><br>
								<img style="display: none" visible="hide" src="" width="480px" /></td>
								<td colspan="2"><input type="file" name="dp_pic2" size="5242880"
									accept="image/*"><br>
								<img style="display: none" visible="hide" src="" width="480px" /></td>
								</tr>
								<tr><th colspan="2">圖片3:</th>
									<th colspan="2">圖片4:</th>
								</tr>
								<tr><td colspan="2"><input type="file" name="dp_pic3" size="5242880"
									accept="image/*"><br>
								<img style="display: none" visible="hide" src="" width="480px" /></td>
								<td colspan="2"><input type="file" name="dp_pic4" size="5242880"
									accept="image/*"><br>
								<img style="display: none" visible="hide" src="" width="480px" /></td>
									</tr><tr><td colspan="2">
											<input type="submit" class="btn btn-primary" value="送出新增">
											<input type="hidden" name="wm_no" value="${webmasterVO.wm_no}"> 
											<input type="hidden" name="action" value="insert">
									</td>
								</tr>
							</tbody>
						</table>
						</FORM>
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
		$(this).next("img").attr('src',"");
		$(this).next("img").attr('style',"display:none");
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

	$(":input").change(function() {
		readURL(this);
	});
	
	</script>
</body>
</html>


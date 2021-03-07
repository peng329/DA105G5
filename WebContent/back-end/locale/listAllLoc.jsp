<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.locale.model.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>
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
<link href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css"
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
					<div class="search-box">
						<div class="row">
							<div class="col-md-3">
								<h5>關鍵字搜尋</h5>
							</div>
							<div class="col-md-6">
								<input type="text" id="myInput" onkeyup="myFunction()"
									class="form-control" placeholder="輸入關鍵字">
								<script>
									$(document)
											.ready(
													function() {
														$("#myInput")
																.on(
																		"keyup",
																		function() {
																			var value = $(
																					this)
																					.val()
																					.toLowerCase();
																			$(
																					"#myTable tr")
																					.filter(
																							function() {

																								$(
																										this)
																										.toggle(
																												$(
																														this)
																														.text()
																														.toLowerCase()
																														.indexOf(
																																value) > -1)
																							});
																		});
													});
								</script>
							</div>
						</div>
					</div>
					<div class="search-list table-responsive-lg">
						<table class="table table-sm table-striped table-hover">
							<thead class="thead-dark">
								<tr>
									<th>地區編號/地區名稱</th>
									<th>潛點數量</th>
									<th>日期</th>
									<th colspan ="5">天氣</th>
									<th>查詢地區潛點</th>
								</tr>
							</thead>
							<tbody id="myTable">
							<c:set var="count" value="0" scope="page"/>
								<c:forEach var="locVO" items="${loclist}" >
								<c:set var="count" value="${count+1}"  scope="page"/>
										<tr>
										<td rowspan="3"><p>${locVO.loc_no}</p><p>${locVO.sub_region}</p></td>
										<td rowspan="3" id="count loc${count}"></td>
										<td id="d1 loc${count}"></td>
										<td id="wx1 loc${count}"></td>
										<td id="wd1 loc${count}"></td>
										<td id="ws1 loc${count}"></td>
										<td id="wh1 loc${count}"></td>
										<td id="wt1 loc${count}"></td>
										<td rowspan="3">
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/locale/loc.do"
												style="margin-bottom: 0px;">
												<input type="submit" class="btn btn-primary" value="送出查詢"> 
												<input type="hidden" name="loc_no" value="${locVO.loc_no}">
												<input type="hidden" name="wm_no"  value="${webmasterVO.wm_no}">
												<input type="hidden" name="action" value="listDpsByLocno_B">
											</FORM><br>
											  <a class="btn btn-warning"  href="<%=request.getContextPath()%>/back-end/dive_point/addDp.jsp" role="button" aria-expanded="false" aria-controls="collapseExample">
    										新增潛點
  											</a>
										</td>
										<c:forEach begin="2" end="3" varStatus="i">
										<tr>
											<td id="d${i.count+1} loc${count}"></td>
											<td id="wx${i.count+1} loc${count}"></td>
											<td id="wd${i.count+1} loc${count}"></td>
											<td id="ws${i.count+1} loc${count}"></td>
											<td id="wh${i.count+1} loc${count}"></td>
											<td id="wt${i.count+1} loc${count}"></td>
										</tr>
										</c:forEach>
								</c:forEach>
							</tbody>
						</table>
		<%if (request.getAttribute("listDpsByLocno")!=null){%>
       <jsp:include page="listDpsByLocno.jsp" />
		<%} %>
					</div>
				</div>
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
	var listSize=<%=loclist.size()%>;
	<%
	int i =1;
		out.print("var loc =[0];");
	for(LocVO locvo:loclist){
		out.print("loc.push('"+locvo.getWeather()+"');");
		i++;
	}
	%>
	let allDp=[];
	let allLoc=[];
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
				executeCountDps();
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
	
	var str='';
	var wxoriAll = '${strjson}';
	
	for (var i =1;i<=listSize;i++){
		getWx(i);//拆天氣的func
		}

	
	var wxori;
	function getWx(e){
	let months = "一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月".split(",");
	let weekdays = "星期日,星期一,星期二,星期三,星期四,星期五,星期六".split(",");
		wxori=loc[e];
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
	
	<c:forEach begin="0" end="2" varStatus="i">
	$("td[id*='d${i.count} loc"+e+"']").text(day[${i.index}][0].getDate());
	$("td[id*='wx${i.count} loc"+e+"']").text(day[${i.index}][1]);
	$("td[id*='wd${i.count} loc"+e+"']").text(day[${i.index}][2]);
	$("td[id*='ws${i.count} loc"+e+"']").text(day[${i.index}][3]);
	$("td[id*='wh${i.count} loc"+e+"']").text(day[${i.index}][4]);
	$("td[id*='wt${i.count} loc"+e+"']").text(day[${i.index}][5]);
	</c:forEach>
	}
	var timeoutID;
	function executeCountDps(){
		timeoutID=setTimeout(choseLoc,500);
		console.log("timeOutID +"+timeoutID);
	}
	function choseLoc(){
		window.clearTimeout(timeoutID);
		for(var i =0;i<listSize;i++){
		countDps(i,allDp.length);//算潛點數量
		}
		}
	var count;
	function countDps(e,f){
		window.clearTimeout(timeoutID);
		count = 0 ;
		for(var i=0;i<f;i++){
		if(allLoc[e].loc_no==allDp[i].loc_no){
			count = count+1;
			console.log(count);
		}
		}
		$("td[id*='count loc"+(e+1)+"']").text(count);
		console.log(count);
	}
	
	</script>
</body>
</html>


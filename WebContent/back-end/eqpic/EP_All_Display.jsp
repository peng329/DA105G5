<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.eqpic.model.*"%>
<%
	String ep_seq = request.getParameter("ep_seq");
	String ep_name = request.getParameter("ep_name");
	String ds_no=request.getParameter("ds_no");
	EqpicService eqpicSvc = new EqpicService();
	List<EqpicVO> list = eqpicSvc.findByEpAll(ep_seq);

	Set<String> set = new LinkedHashSet<String>();
	for (EqpicVO eqpicVO2 : list) {
		set.add(eqpicVO2.getEp_seq());
	}
	
	pageContext.setAttribute("ds_no", ds_no);
	pageContext.setAttribute("ep_seq", ep_seq);
	pageContext.setAttribute("ep_name", ep_name);

	pageContext.setAttribute("list", list);
	pageContext.setAttribute("set", set);
	
%>

<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>

<style>
	#right-panel{
	background-color:white;
	}
	.card{
	width:100%;
	font-size:0.875em;
	}
	.card .card-body {
	padding:0.875rem;
	}
	#bootstrap-data-table_length{
	text-align:left;
	}
	#bootstrap-data-table_info{
	text-align:left;
	}
</style>

</head>
<body bgcolor='white'>
	<div class="container">
			<div class="row">
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
		
			<div class="row" style="margin-bottom:3%;">
				<a class="btn btn-outline-info btn-lg" href="<%=request.getContextPath()%>/back-end/eqpic/addEpic.jsp?ds_no=${ds_no}&ep_seq=${ep_seq}">
					新增圖片
				</a>
			</div>
			<div class="row" style="margin-bottom:1.5%;">
				<%@ include file="page1.file"%>
			</div>
			
			<div class="row">
				<div class="col-md-2" style="margin:auto;margin-bottom:1%;font-size:22px;">裝備名稱</div>
				<div class="col-md-4" style="postition:relative;left:12%;margin:auto;margin-bottom:1%;font-size:22px;">圖片</div>
			</div>
				
			<div class="row">
				<div class="col-md-2" style="position:relative;margin:auto;top:50%;font-size:25px;">${ep_name}</div>
							<c:forEach var="ep_seq" items="${set}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<div class="col-md-4" style="margin:auto;margin-bottom:1%;">
					<c:forEach var="eqpicVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<div class="card text-center">
							<c:if test="${ep_seq==eqpicVO.ep_seq}">
								<img class="card-img-top" src="<%=request.getContextPath()%>/PrintPic?epic_seq=${eqpicVO.epic_seq}" alt="Card image" style="width:50%;text-align:center;">
							</c:if>
						</div>
						<div class="row" style="margin-top:0%;margin-bottom:3%;">
							<div class="col" style="margin:auto;">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" style="margin-bottom: 0px;">
									<input type="hidden"  name="epic_seq" value="${eqpicVO.epic_seq}">
									<input type="hidden" name="action" value="getOne_For_Update">
									<input type="submit" value="修改" class="btn btn-outline-info btn-lg"> 
								</FORM>
							</div>
							<div class="col" style="margin:auto;margin-left:35%;">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" style="margin-bottom: 0px;">
									<input type="hidden" name="epic_seq" value="${eqpicVO.epic_seq}"> 
									<input type="hidden" name="action" value="${ep_seq}">
									<input type="hidden" name="action" value="delete">
									<input type="submit" value="刪除" class="btn btn-outline-info btn-lg"> 
								</FORM>		
							</div>
						</div>
					</c:forEach>
				</div>				
						</c:forEach>	
			</div>
			<%@ include file="page2.file"%>
			<div style="text-align:center;margin:auto;margin-bottom:3%;">
				<a class="btn btn-outline-info btn-lg" href="<%=request.getContextPath()%>/back-end/equip/listAllDSEquipByDs_no.jsp?ds_no=${diveshopVO.ds_no}">回上頁</a>
			</div>
	</div>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</html>
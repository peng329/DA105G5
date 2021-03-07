<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.equip.model.*,com.rorder.model.*,com.eqpic.model.*"%>
<%@	page import="com.rodetail.model.*"%>
<%
String ro_no=request.getParameter("ro_no");
EquipService equipSvcm = new EquipService();
RoDetailService rodetailSvc= new RoDetailService();
List<RoDetailVO> listm = rodetailSvc.getgetSameRoRdAll(ro_no);
List<String> ep_seqlist=new ArrayList<>();
for(int i=0;i<listm.size();i++){
	ep_seqlist.add(listm.get(i).getEp_seq());
}

pageContext.setAttribute("equipSvcm",  equipSvcm);
pageContext.setAttribute("listm", listm);
pageContext.setAttribute("ep_seqlist", ep_seqlist);
%>
<%@include file="/HeaderFooter/header.jsp" %>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" >
		<script src="<%=request.getContextPath()%>/js/bootstrap.min.js" ></script>
	</head>

	<body>
		<div class="container">
	
				<div class="row" style="background-color:#65c4e0;text-align:center;color:white;font-size:15px;">
					<div class="col-md-4">裝備名稱</div>
					<div class="col-md-4">裝備尺寸</div>
					<div class="col-md-4">租賃金額</div>
				</div>
				
				<div class="row" style="text-align:center;font-size:15px;">
				<%
				for(int i=0;i<listm.size();i++){
					String ep_seq = ep_seqlist.get(i);
					equipSvcm.getOneEq(ep_seq);
					String ep_name=equipSvcm.getOneEq(ep_seq).getEp_name();
					String ep_size=equipSvcm.getOneEq(ep_seq).getEp_size();
					String ep_rp=equipSvcm.getOneEq(ep_seq).getEp_rp().toString();
					pageContext.setAttribute("ep_seq", ep_seq);
					pageContext.setAttribute("ep_name", ep_name);
					pageContext.setAttribute("ep_size", ep_size);
					pageContext.setAttribute("ep_rp", ep_rp);
				%>
								<div class="col-md-4" style="width:50%;margin:auto;">${ep_name}</div>
								<div class="col-md-4" style="width:50%;margin:auto;">${ep_size}</div>
								<div class="col-md-4" style="width:50%;margin:auto;">${ep_rp}</div>
				
				<% 
				}
				%>	
				</div>
		</div>
	</body>
	<%@include file="/HeaderFooter/footer.jsp"%>
</html>
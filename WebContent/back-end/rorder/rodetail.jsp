<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.equip.model.*,com.rorder.model.*,com.eqpic.model.*"%>
<%@	page import="com.rodetail.model.*"%>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<%
String ro_no=request.getParameter("ro_no");
EquipService equipSvc = new EquipService();
RoDetailService rodetailSvc= new RoDetailService();
List<RoDetailVO> list = rodetailSvc.getgetSameRoRdAll(ro_no);
List<String> ep_seqlist=new ArrayList<>();
for(int i=0;i<list.size();i++){
	ep_seqlist.add(list.get(i).getEp_seq());
}

pageContext.setAttribute("equipSvc",  equipSvc);
pageContext.setAttribute("list", list);
pageContext.setAttribute("ep_seqlist", ep_seqlist);
%>

		<style>
			#right-panel{
			background-color:white;
			}
			body{
			overflow-x: hidden;
			display:block;
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
	<body>
		<div class="container">
	
				<div class="row" style="background-color:#65c4e0;text-align:center;color:white;font-size:18px;">
					<div class="col-md-3">裝備編號</div>
					<div class="col-md-3">裝備名稱</div>
					<div class="col-md-3">裝備尺寸</div>
					<div class="col-md-3">租賃金額</div>
				</div>
				
				<div class="row" style="text-align:center;font-size:16px;">
				<%
				for(int i=0;i<list.size();i++){
					String ep_seq = ep_seqlist.get(i);
					equipSvc.getOneEq(ep_seq);
					String ep_no=equipSvc.getOneEq(ep_seq).getEp_no();
					String ep_name=equipSvc.getOneEq(ep_seq).getEp_name();
					String ep_size=equipSvc.getOneEq(ep_seq).getEp_size();
					String ep_rp=equipSvc.getOneEq(ep_seq).getEp_rp().toString();
					pageContext.setAttribute("ep_seq", ep_seq);
					pageContext.setAttribute("ep_no", ep_no);
					pageContext.setAttribute("ep_name", ep_name);
					pageContext.setAttribute("ep_size", ep_size);
					pageContext.setAttribute("ep_rp", ep_rp);
				%>
								<div class="col-md-3" style="width:50%;margin:auto;">${ep_no}</div>
								<div class="col-md-3" style="width:50%;margin:auto;">${ep_name}</div>
								<div class="col-md-3" style="width:50%;margin:auto;">${ep_size}</div>
								<div class="col-md-3" style="width:50%;margin:auto;">${ep_rp}</div>
				
				<% 
				}
				%>	
				</div>
				
				<div class="row justify-content-md-center" style="margin:1.5%;">
						<a href="<%=request.getContextPath()%>/back-end/rorder/listADSAllRO.jsp?ds_no=${diveshopVO.ds_no}" class="btn btn-outline-info btn-sm" >回上頁</a>
				</div>
				
		</div>
	</body>
	<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</html>
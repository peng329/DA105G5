<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.equip.model.*,com.rorder.model.*,com.rodetail.model.*,com.mem.model.*"%>
<%@ page import="com.eqpic.model.EqpicService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
	EquipService equipSvc =new EquipService();
	EqpicService eqpicSvc = new EqpicService();
%>

<%	
	String ro_no= (String)session.getAttribute("next_ro_no");
	ROrderVO rorderVO = (ROrderVO)session.getAttribute("rorderVO");
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	List<RoDetailVO> rdlist=(List<RoDetailVO>)session.getAttribute("rdlist");
%>
<% 
	pageContext.setAttribute("memVO", memVO);
	pageContext.setAttribute("ro_no", ro_no);
	pageContext.setAttribute("rdlist", rdlist);
	pageContext.setAttribute("eqpicSvc", eqpicSvc);
	pageContext.setAttribute("rorderVO", rorderVO);
%>

<%@include file="/HeaderFooter/header.jsp" %>
<style>
  table{
    text-align:center;
	background-color="#616130"
  }
  td,th{
 	font-color:black;
 	text-align:center;
  }

</style>

<body>

	<div class="container" style="margin-top:3%;">
<!-- 			<div class="row"  style="font-size:1.5em;"> -->
					<c:if test="${rorderVO.mem_no==memVO.mem_no}">
							<div class="row justify-content-md-center" style="font-size:1.5em;font-family:'Noto Sans TC';">
								${memVO.mem_name} 您好！
							</div>
							
							<div class="row justify-content-md-center" style="font-size:1.5em;font-family:'Noto Sans TC';">
								以下是您的訂單明細
							</div>
					</c:if>
<!-- 			</div> -->
			
			<div class="row" style="font-size:1.4em;margin-top:3%;text-align:center;
									background-color:#65c4e0;color:white;font-family:'Noto Serif TC', serif;">
				<div class="col-md-3">訂單編號</div>
				<div class="col-md-3">潛店名稱</div>
				<div class="col-md-3">裝備名稱</div>
				<div class="col-md-3">租賃金額</div>
			</div>
	
	<% 
	for(int i=0;i<rdlist.size();i++){
		String ds_no=equipSvc.getOneEq(rdlist.get(i).getEp_seq()).getDs_name();
		String ep_name=equipSvc.getOneEq(rdlist.get(i).getEp_seq()).getEp_name();
		Integer ep_crp=equipSvc.getOneEq(rdlist.get(i).getEp_seq()).getEp_rp();
		
		pageContext.setAttribute("ds_no", ds_no);
		pageContext.setAttribute("ep_name",ep_name);
		pageContext.setAttribute("ep_crp", ep_crp);	
	%>
	
			<div class="row" style="font-size:1.3em;margin-top:1.5%;text-align:center;">
				<div class="col-md-3">${ro_no}</div>
				<div class="col-md-3">${ds_no}</div>
				<div class="col-md-3">${ep_name}</div>
				<div class="col-md-3">${ep_crp}</div>
			</div>
	<%
	}
	%>		
			<div class="row" style="font-size:1.3em;margin-top:1.5%;">
				租賃總件數：${param.tepc}
			</div>
		
			<div class="row" style="font-size:1.3em;margin-top:1.5%;">
				租賃總金額：${param.tpriz}
			</div>
			
			<div class="row" style="font-size:1.3em;margin-top:1.5%;">
				付款狀態：請於二日內完成付款，否則將自動取消訂單！
			</div>
		
			<div class="row" style="margin-top:3%;margin-bottom:3%;">
		       <div class="col-md-6" style="text-align:center">
			      <a class="btn btn-outline-info btn-lg" href="<%=request.getContextPath()%>/front-end/equip/listAllDSEquipByDs_no.jsp?ds_no=${ds_no}">
			       		繼續購物
			      </a>
		       </div>
		     
		       <div class="col-md-6" style="text-align:center">
			       <FORM name="payment2" action="<%=request.getContextPath()%>/front-end/creditcard/credit_card.jsp" method="POST">
				       <input type="hidden" name="ro_no" value="${ro_no}">
				       <input type="hidden" name="mem_no" value="${memVO.mem_no}">
					       <button type="submit" class="btn btn-outline-info btn-lg">
								付款
					       </button>
			       </FORM >
		       </div>
	       </div>
	</div>
</body>
<%@include file="/HeaderFooter/footer.jsp"%>
</html>
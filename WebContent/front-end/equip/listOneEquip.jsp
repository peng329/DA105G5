<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.equip.model.*,com.eqpic.model.*"%>

<%
	String ep_seq = request.getParameter("ep_seq");
	EquipService equipSvc = new EquipService();
	EquipVO equipVO = equipSvc.getOneEq(ep_seq);
	EqpicService eqpicSvc = new EqpicService();
	List<EqpicVO> list = eqpicSvc.findByEpAll(ep_seq);
	pageContext.setAttribute("equipVO", equipVO);
	pageContext.setAttribute("eqpicSvc", eqpicSvc);
	pageContext.setAttribute("ep_seq", ep_seq);
	pageContext.setAttribute("list", list);

%>
<html lang="en">
<meta charset="UTF-8">
<head>
<%@include file="/HeaderFooter/header.jsp"%>
</head>
<style>
.btn {
  display: block;
/*   background: #bded7d; */
/*   color: #fff; */
  text-decoration: none;
  margin: 20px 0;
  width: 100%;
  padding: 15px 15px;
  border-radius: 5px;
  position: relative;
}
.btn::after {
  content: '';
  position: absolute;
  z-index: 1;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transition: all 0.2s ease-in-out;
  box-shadow: inset 0 3px 0 rgba(0, 0, 0, 0), 0 3px 3px rgba(0, 0, 0, .2);
  border-radius: 5px;
}
.btn:hover::after {
  background: rgba(0, 0, 0, 0.1);
  box-shadow: inset 0 3px 0 rgba(0, 0, 0, 0.2);
}
#diveshop-banner img{
    width: 100%;
}
#dsbg img{
width: 102%;
}
</style>

<body>
	        <div class="container">
	        	<div class="row">	
					<%@include file="/front-end/diveshop/dvheader.jsp"%>  
				</div>
				
	            <div class="row">
	            	<div class="col-3" style="padding-left:0%;">   
    					 <%@include file="/front-end/diveshop/left-pannel.file"%>
    				</div>
    				
					<div class="col-9" style="padding-right:0%;">
    					<div class="row" style="margin-top:0%;">
	    					
	    					<div class="col-md-6" style="margin:auto;text-align:center;">
		    						<c:set var = "epic_seq" value="${eqpicSvc.AEp_seq_All_Epic_seq(ep_seq).get(0)}"/>
										<c:if test="${eqpicSvc.AEp_seq_All_Epic_seq(ep_seq).size()!=0}">
											<img src="<%=request.getContextPath()%>/PrintPic?epic_seq=${epic_seq}" 
												 style="position:relative;width:77%;">
										</c:if>  
	    					</div>
	    					
	    					<div class="col-md-6" style="postition:relative;text-align:left;">
	    						<h3 style="postition:relative;text-align:left;margin-top:5%;">品名：<%=equipVO.getEp_name()%></h3>
							    	<div style ="postition:relative;text-align:left;margin-top:5%;">
							    	主要布料 : 90.0% 氯丁橡膠,10.0% 聚醯纖維<br>
							    	下擺 : 82.0% 聚醯纖維,18.0% 彈性纖維
							    	</div>
							    	<div style ="margin-top:5%;">裝備租金：<%=equipVO.getEp_rp()%></div>
							    	<div style ="margin-top:5%;">裝備尺寸：<%=equipVO.getEp_size()%></div>
							    	<div style="postition:relative;text-align:center;margin-top:10%;">
								    	<form name="RentForm" action="<%=request.getContextPath()%>/RentCart/rentcart.do" method="POST">
										<input type="hidden" name="ep_seq" value="${equipVO.ep_seq}">
										<input type="hidden" name="epc_no" value="${equipVO.epc_no}">
										<input type="hidden" name="ep_name" value="${equipVO.ep_name}">
										<input type="hidden" name="ep_rp" value="${equipVO.ep_rp}">
										<input type="hidden" name="ep_size" value="${equipVO.ep_size}">
										<input type="hidden" name="action" value="ADD">
									<c:if test="${equipVO.epr_state.contains('出租') ||equipVO.epr_state.contains('待取')}">
								    	<input type="submit" class="btn btn-outline-danger" value="請洽店家" disabled="disabled"style="color:red;">
									</c:if>
									<c:if test="${!equipVO.epr_state.contains('出租') &&!equipVO.epr_state.contains('待取')}">
								    	 <input type="submit" class="btn btn-outline-info" value="放入購物車" >
									</c:if>
										</form>
									</div>
	    					</div>
	    				</div>
    					
    					<div class="row" style="">
	    					<c:forEach var="eqpicVO" items="${list}">
	    							<img src="<%=request.getContextPath()%>/PrintPic?epic_seq=${eqpicVO.epic_seq}" 
	    								 style="postition:relative;margin:auto;padding-top:2%;width:80%;">
		    				</c:forEach>
	    				</div>
    				</div>
				</div>
		</div>	
</body>
<%@include file="/HeaderFooter/footer.jsp"%>
</html>
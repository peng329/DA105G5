<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.equip.model.*,com.eqpic.model.*,java.util.*,com.rorder.model.*,com.rodetail.model.*,com.diveshop.model.*"%>

<%
HttpSession session2 =request.getSession();
DiveshopVO diveshopVO=(DiveshopVO)session2.getAttribute("diveshopVO");
String ds_no=diveshopVO.getDs_no();
EquipVO equipVO = (EquipVO) request.getAttribute("equipVO");

%>

<%
	EquipService equipSvc = new EquipService();
	pageContext.setAttribute("equipSvc", equipSvc);
	List<EquipVO> list = equipSvc.getDSAllFORSHOP(ds_no);
	list = list.stream()                   //一個來源(Source)
		       .distinct()       //零或多個中介操作(Intermediate operation)
		 .collect(java.util.stream.Collectors.toList());    //一個終端操作(Terminal operation)

	pageContext.setAttribute("list", list);
		 
	List<String> list2 = equipSvc.getAllGroupBy(); 
	pageContext.setAttribute("list2", list2);
	
	ROrderService rorderSvc = new ROrderService();
	Set<String> set = rorderSvc.getRR_DATE_IS_NULL();
	
	RoDetailService rodetailSvc = new RoDetailService();
 
	List<String> list3 = new ArrayList<String>();
	list3.addAll(set);
	
	List<RoDetailVO> list4 = new ArrayList<RoDetailVO>();
	RoDetailVO rodetailVO = new RoDetailVO();

	for(int i = 0 ;i<list3.size();i++){
		list4.addAll(rodetailSvc.getgetSameRoRdAll(list3.get(i)));
	}
	
	List<String> list5 = new ArrayList<String>();
		for(int i=0;i<list4.size();i++){
			list5.add(list4.get(i).getEp_seq());
		}
	pageContext.setAttribute("list5", list5);	
	pageContext.setAttribute("ds_no", ds_no);	

%>


<%
EqpicService eqpicSvc = new EqpicService();
Set<String> set2 = 	new LinkedHashSet<>();
for(int i =0;i<list.size();i++){
	set2.add(list.get(i).getEp_seq());
}	

pageContext.setAttribute("set2", set2);
pageContext.setAttribute("eqpicSvc", eqpicSvc);

String ds_name =request.getParameter("ds_name");


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
h5{
font-family: 'Noto Serif TC', serif;
}
.card-text{
font-family: 'Noto Sans TC', sans-serif;
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
						<%@ include file="page1.file" %> 
						   <div class="card-columns">
								<c:forEach var="equipVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<FORM name="RentForm" action="<%=request.getContextPath()%>/RentCart/rentcart.do" method="POST">
											<div class="card text-center" style="width: 18rem;">
												 <a href="<%=request.getContextPath()%>/front-end/equip/listOneEquip.jsp?ep_seq=${equipVO.ep_seq}" style="color:cadetblue;">
													 <c:forEach var="ep_seq" items="${set2}">
														<c:if test="${ep_seq==equipVO.ep_seq}">
															<c:if test="${eqpicSvc.AEp_seq_All_Epic_seq(ep_seq).size()!=0}">
																<c:set var = "epic_seq" value="${eqpicSvc.AEp_seq_All_Epic_seq(ep_seq).get(0)}"/>
														 			<img src="<%=request.getContextPath()%>/PrintPic?epic_seq=${epic_seq}" style="width:6 em;height:12em;margin-top:3%;overflow:hidden;">
															</c:if>
														</c:if>
													</c:forEach>
												 </a>
						
												  <div class="card-body">
													    <h5 class="card-title">品名 : ${equipVO.ep_name}</h5>
													    
													    <p class="card-text">
																租金：${equipVO.ep_rp}
													    </p>
													    
													    <p class="card-text">
																尺寸：${equipVO.ep_size}
													    </p>
													    
													    <p class="card-text">
													    		可租賃數量：
															<c:set var="list7" value="${equipSvc.getAllByDA_NO$EP_NAME(equipVO.ds_no,equipVO.ep_name,equipVO.ep_size)}"/>
																<!-- ${list7.removeAll(list5)}  -->
																<c:if test="${list7.size()!=0}">
																		<select size="1" name="ep_seq">	
																			<%
																			int count=0;
																			%>
																			<c:forEach var="ep_seq" items="${equipSvc.getAllByDA_NO$EP_NAME(equipVO.ds_no,equipVO.ep_name,equipVO.ep_size)}" >
																						<c:if test="${!list5.contains(ep_seq)&&!(equipVO.epr_state.contains('出租') || equipVO.epr_state.contains('待取'))}">
																					<%  
																 						++count;
																 						pageContext.setAttribute("count",count);
																					%> 
																								<option value="${ep_seq}">${count}
																						</c:if> 
																			</c:forEach>
																	</select>
															 </c:if>	
													    </p>												    
														 <c:choose>

														    <c:when test="${list7.size()!=0&&!(equipVO.epr_state.contains('出租') || equipVO.epr_state.contains('待取'))}">
														        <input type="submit" class="btn btn-outline-info" value="我要租賃">
														    </c:when>

														    <c:otherwise>
														        <input type="submit" class="btn btn-outline-danger" value="請洽店家" disabled="disabled"style="color:red;">
														    </c:otherwise>
														
														</c:choose>   
												  </div>  
											</div>
														<input type="hidden" name="ds_no" value="${equipVO.ds_no}">
									     				<input type="hidden" name="ep_seq" value="${equipVO.ep_seq}">
									     				<input type="hidden" name="epc_no" value="${equipVO.epc_no}">
									    			  	<input type="hidden" name="ep_name" value="${equipVO.ep_name}">
									   				    <input type="hidden" name="ep_rp" value="${equipVO.ep_rp}">
									   				    <input type="hidden" name="ep_size" value="${equipVO.ep_size}">
														<input type="hidden" name="action" value="ADD">
										</FORM>
							</c:forEach>
						</div>								
								<div class="row">
									<%@ include file="page2.file" %>				 
								</div>								
					</div>
			  </div>
		 </div>
<script src='https://cdnjs.cloudflare.com/ajax/libs/vue/2.6.10/vue.min.js'></script>
<script src='https://unpkg.com/vue-the-mask@0.11.1/dist/vue-the-mask.js'></script><script  src="./script.js"></script>

</body>
<div style="position:relative; margin:auto; text-align:center;">
<%@include file="/HeaderFooter/footer.jsp"%>
</div>
</html>
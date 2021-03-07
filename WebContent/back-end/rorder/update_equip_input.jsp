<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.equip.model.*,com.equipc.model.*"%>

<%
	EquipVO equipVO = (EquipVO) request.getAttribute("equipVO");
	pageContext.setAttribute("equipVO", equipVO);
%>
<html>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<jsp:useBean id="equipCSvc" scope="page" class="com.equipc.model.EquipCService" />
<%
pageContext.setAttribute("diveshopVO",diveshopVO);
%>
<style>
@import url('https://fonts.googleapis.com/css?family=Patua+One&display=swap');
@import url('https://fonts.googleapis.com/css?family=Courgette|Noto+Sans+TC:500|Noto+Serif+TC:900|Patua+One&display=swap');
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+TC:500|Noto+Serif+TC:900&display=swap');
@import url('https://fonts.googleapis.com/css?family=Noto+Serif+TC:900&display=swap');

#right-panel{
background-color:white;
}
.content {
    padding: 0.875em;
}
body{
overflow-x: hidden;
display:block;
}

</style>

</head>

<body>
	<div class="container">
		<div class="row" style="font-size:20px">
			裝備資料修改：${equipVO.ep_name}
		</div>
		<div class="row" style="margin:1%;">
			<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font><br>
					<c:forEach var="message" items="${errorMsgs}">
						<font style="color: red">${message}</font>
					</c:forEach>
			</c:if>
		</div>
		
		<div class="row">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Equip/equip.do" name="form1">
				<div class="row form-group">
						<div class="col-3 col-md-4" style="padding-right:0%;">
							<label for="text-input" class=" form-control-label">裝備編號：</label>
						</div>
						<div class="col-9 col-md-8">
							<input type="text" id="ep_no" name="ep_no" placeholder="ex.GA0001" value="${equipVO.ep_no}" class="form-control">
						</div>
				</div>
				
				<div class="row form-group">
						<div class="col-3 col-md-4" style="padding-right:0%;">
							<label for="text-input" class=" form-control-label">裝備名稱：</label>
						</div>
			           <div class="col-9 col-md-8">
			            	<input type="text" id="ep_name" name="ep_name" placeholder="${equipVO.ep_name}" value="${equipVO.ep_name}" class="form-control">
			            </div>
		   		</div>
				<div class="row form-group">
					 	<div class="col-3 col-md-4" style="padding-right:0%;">
					 		<label for="select" class=" form-control-label" style="font-size:95%;">分類編號：</label>
					 	</div>			     
					    <div class="col-9 col-md-8">
							   <select name="epc_no" id="epc_no" class="form-control">
									<c:forEach var="equipCVO" items="${equipCSvc.all}">
										<option value="${equipCVO.epc_no}">${equipCVO.epc_name}
									</c:forEach>
							  </select>
					   </div>
				</div>
		   		
				<div class="row form-group">
						<div class="col-3 col-md-4" style="padding-right:0%;">
							<label for="text-input" class=" form-control-label">裝備買價：</label>
						</div>
			            <div class="col-9 col-md-8">
			            	<input type="text" id="ep_priz" name="ep_priz" placeholder="ex.5000" value="${equipVO.ep_priz}" class="form-control">
			            </div>
		   		</div>
		   		
				<div class="row form-group">
						<div class="col-3 col-md-4" style="padding-right:0%;">
							<label for="text-input" class=" form-control-label">租賃金額：</label>
						</div>
			             <div class="col-9 col-md-8">
			            	<input type="text" id="ep_rp" name="ep_rp" placeholder="ex.500" value="${equipVO.ep_rp}" class="form-control">
			            </div>
		   		</div>				
				
				<div class="row form-group">
					 		<div class="col-3 col-md-4" style="padding-right:0%;">
					 			<label for="select" class=" form-control-label">裝備尺寸：</label>
					 		</div>			     
					        <div class="col-9 col-md-8">
							   <select name="ep_size" id="ep_size" class="form-control">
									<option value="F"${(equipVO.ep_size=="F")? 'selected':'' } >F					
									<option value="S" ${(equipVO.ep_size=="S")? 'selected':'' } >S
									<option value="M "${(equipVO.ep_size=="M")? 'selected':'' } >M
									<option value="L"${(equipVO.ep_size=="L")? 'selected':'' } >L
									<option value="XL "${(equipVO.ep_size=="XL")? 'selected':'' } >XL
							        </select>
					        </div>
				</div>
				
				<div class="row form-group">
					 <div class="col-3 col-md-4" style="padding-right:0%;">
					 	<label for="select" class=" form-control-label">裝備狀態：</label>
					 </div>
					 <div class="col-9 col-md-8">
						<select name="ep_state" id="ep_state" class="form-control">
							<option value="良好" ${(equipVO.ep_state=="良好")? 'selected':'' } >良好
							<option value="損壞 "${(equipVO.ep_state=="損壞")? 'selected':'' } >損壞
							<option value="修繕中"${(equipVO.ep_state=="修繕中")? 'selected':'' } >修繕中
						</select>
					 </div>
				</div>
					                                    
				<div class="row form-group">
					    <div class="col-3 col-md-4" style="padding-right:0%;">
					    	<label for="select" class=" form-control-label">裝備租賃狀態：</label>
					    </div>			    
					    <div class="col-9 col-md-8">
							 <select name="epr_state" id="select" class="form-control">
									<option value="待取" ${(equipVO.epr_state=="待取")? 'selected':'' } >待取
									<option value="在店" ${(equipVO.epr_state=="在店")? 'selected':'' } >在店
									<option value="出租中" ${(equipVO.epr_state=="出租中")? 'selected':'' } >出租中
							  </select>
					     </div>
				</div>
				
				<div class="row form-group">
					    <div class="col-3 col-md-4" style="padding-right:0%;">
					         <label for="select" class=" form-control-label">裝備陳列狀態：</label>
					    </div>
					    <div class="col-9 col-md-8">
							 <select name="eps_state" id="eps_state" class="form-control">
								<option value="下架" ${(equipVO.eps_state=="下架")?'selected':''} >下架
								<option value="上架" ${(equipVO.eps_state=="上架")? 'selected':'' } >上架
							 </select>
					     </div>
				</div>
				
				<input type="hidden" name="action" value="update"> 
				<input type="hidden" name="ds_no" value="${equipVO.ds_no}">
				<input type="hidden" name="ds_name" value="${equipVO.ds_name}">
				<input type="hidden" name="ep_seq" value="${equipVO.ep_seq}">
				<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
				<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
				<input type="submit" value="送出修改">
			</FORM>
		</div>	
	</div>
	<!-- Scripts -->
</body>

<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</html>
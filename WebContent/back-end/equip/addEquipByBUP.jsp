<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.equip.model.*,com.equipc.model.*,com.eqpic.model.*,com.diveshop.model.*"%>
<%
	String ds_no = request.getParameter("ds_no");
	pageContext.setAttribute("ds_no",ds_no);

	EquipVO equipVO = (EquipVO) request.getAttribute("equipVO");	
	pageContext.setAttribute("equipVO",equipVO);
%>
<jsp:useBean id="equipCSvc" scope="page" class="com.equipc.model.EquipCService" />
<jsp:useBean id="eqpicSvc" scope="page" class="com.eqpic.model.EqpicService" />
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<%
pageContext.setAttribute("diveshopVO",diveshopVO);

%>
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
 <head>
 	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>BUBBLE UP 裝備新增頁面</title>
		<style>
			#right-panel{
			background-color:white;
			}
			body{
			overflow-x: hidden;
			display:block;
			}
			.card{
			background-color:white;
			width:98%;
			font-size:0.875em;
			margin:auto;
			}
			.card .card-body {
			background-color:white;
			padding:0.875rem;
			}
			#bootstrap-data-table_length{
			text-align:left;
			}
			#bootstrap-data-table_info{
			text-align:left;
			}
			.content{
			padding:0%;
			}
		</style>
</head>

<body>
	<div class="container">
		<div class="row" style="margin-top:3%;">
				<c:if test="${diveshopVO.ds_no==ds_no}">
			         <div style="font-size:40px;margin-bottom:3%;">
			               ${diveshopVO.ds_name} 裝備新增
			         </div>
			    </c:if>
		</div>
		
		<div class="row">
			<c:if test="${not empty errorMsgs}">
					<div  class="row" style="color: red">請修正以下錯誤：</div>
					<c:forEach var="message" items="${errorMsgs}">
						<div class="row" style="margin:1.2%;color: red;">${message}</div>
					</c:forEach>
			</c:if>
		</div>
		
		<div class="row">	             
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Equip/equip.do" name="form1" enctype="multipart/form-data">
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
						</select>
					 </div>
				</div>
					                                    
				<div class="row form-group">
					    <div class="col-3 col-md-4" style="padding-right:0%;">
					    	<label for="select" class=" form-control-label">裝備租賃狀態：</label>
					    </div>			    
					    <div class="col-9 col-md-8">
							 <select name="epr_state" id="select" class="form-control">
									<option value="在店" ${(equipVO.epr_state=="在店")? 'selected':'' } >在店
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
				
				<div class="row form-group">
		            <div class="col col-md-3">
		            	<label for="file-multiple-input" class=" form-control-label">裝備圖片:</label>
		            </div>
		            <div class="col-12 col-md-9">
		            	<input type="file" name="epic" onclick="previewImage()" multiple="multiple" id="ppic" style="position:relative;left:23%;">
			        	<div id="holder"></div>	
		        	</div>	
		        </div>	
		       <div class="row form-group">
					 <div class="col col-md-3">
		        		  <button type="button" id="magic" class="btn btn-outline-info btn-sm">神奇小按鈕</button>
		        	</div>
		       </div>
			         <input type="hidden" name="ds_no" value="${diveshopVO.ds_no}"> 
			         <input type="hidden" name="ds_name" value="${diveshopVO.ds_name}"> 
			         <input type="hidden" name="action" value="insertBUP"> 
			         <button type="submit" class="btn btn-outline-info btn-lg">確定新增</button>
			</FORM>
	</div>
</div>

<script type="text/javascript">
		var filereader_support = typeof FileReader != 'undefined';
		
		if (!filereader_support) {
			alert("No FileReader support");
		}
		
		acceptedTypes = {
				'image/png' : true,
				'image/jpeg' : true,
				'image/gif' : true
		};
		
		function previewImage() {
			var upfile = document.getElementById("ppic");
			upfile.addEventListener("change", function(event) {
				var files = event.target.files || event.dataTransfer.files;
				for (var i = 0; i < files.length; i++) {
					previewfile(files[i])
				}
			}, false);
		}
		
		
		function previewfile(file) {
			if (filereader_support === true && acceptedTypes[file.type] === true) {
				var reader = new FileReader();
				reader.onload = function(event) {
					var image = new Image();
					image.src = event.target.result;
					image.width = 100;
					holder.appendChild(image);
				};
				reader.readAsDataURL(file);
			} else {
				holder.innerHTML += "<p>" + "filename: <strong>" + file.name
						+ "</strong><br>" + "ContentTyp: <strong>" + file.type
						+ "</strong><br>" + "size: <strong>" + file.size
						+ "</strong> bytes</p>";
			}
		}
		</script>
		
		<script type="text/javascript">
				$(document).ready(function(){
					$("#magic").click(function(){
						$("#ep_no").val("GA0001")
						$("#ep_name").val("手套")
						$("#ep_priz").val("7000")
						$("#ep_rp").val("700")
					});
				});
</script>
		
		
		
		
	</body>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rorder.model.*"%>

<%
	String ds_no=request.getParameter("ds_no");
	ROrderVO rorderVO = (ROrderVO) request.getAttribute("rorderVO");
	pageContext.setAttribute("ds_no", ds_no);
	pageContext.setAttribute("rorderVO", rorderVO);
%>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<%
pageContext.setAttribute("diveshopVO",diveshopVO);
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>裝備資料修改 - update_equip_input.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />

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

table {
	width: 70%;
	background-color: white;
	margin:auto;
	margin-top:1%;
	margin-bottom: 2%;
	border-collapse:collapse;
}

table, th, td {
	border: 6px ridge #CCEEFF;
		text-align: center;
}

th, td {
	cellpadding:0px;
	padding:5px;
	padding-top:0px;
	padding-bottom:0px;
	text-align: center;
}
</style>

</head>
<body>
<div class="container" >
		<div class="row">
			<font style="font-size:40px;">
				${diveshopVO.ds_name} 訂單資料更新
			</font>
		</div>
		<div class="row">
			<c:if test="${not empty errorMsgs}">
				<div class="row" style="margin-bottom:1%;">
						請修正以下錯誤:
				</div>	
				<div class="row" style="color:red;">
				<br>
					<c:forEach var="message" items="${errorMsgs}">
							${message}
					</c:forEach>
				</div>	
			</c:if>
		</div>	
			
		<div class="row">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ROrder/rorder.do" name="form1">
				<div class="row form-group">				
					<div class="col-3 col-md-4" style="padding-right:0%;">
						<label for="text-input" class=" form-control-label">訂單編號:</label>
					</div>						
					<div class="col-9 col-md-8">
						${param.ro_no}
					</div>				
				</div>
						
				<div class="row form-group">
					<div class="col-3 col-md-4" style="padding-right:0%;">
						<label for="text-input" class="form-control-label">實際歸還日期:</label>
					</div>
					<div class="col-9 col-md-8">
						<input name="rr_date" id="rr_date" type="text">
					</div>
				</div>
						
				<div class="row form-group">
					<div class="col-3 col-md-4" style="padding-right:0%;">
						<label for="select" class=" form-control-label" style="font-size:95%;">付款狀態：</label>
					</div>			     					 
					<div class="col-9 col-md-8">
						<select name="op_state" id="op_state" class="form-control">
							<option value="已付款" ${(rorderVO.op_state=="已付款")? 'selected':'' } >已付款				
						</select>
					</div>
				</div>
				
				<div class="row form-group">
					<div class="col-3 col-md-4" style="padding-right:0%;">
						<label for="select" class=" form-control-label" style="font-size:95%;">歸還狀態：</label>
					</div>			     					 
					<div class="col-9 col-md-8">
						<select name="o_state" id="o_state" class="form-control">
							<option value="待取"${(rorderVO.o_state=="待取")? 'selected':'' } >待取				
							<option value="未歸還" ${(rorderVO.o_state=="未歸還")? 'selected':'' } >未歸還
							<option value="已歸還 "${(rorderVO.o_state=="已歸還")? 'selected':'' } >已歸還
						</select>
					</div>
				</div>
				   		
				<div class="row form-group">
					<div class="col-3 col-md-4" style="padding-right:0%;">
						<label for="text-input" class=" form-control-label">罰鍰：</label>
					</div>
					<div class="col-9 col-md-8">
						<c:if test="${rorderVO.ffine==0}">
					        <input type="text" id="ffine" name="ffine" placeholder="ex.500" value="${rorderVO.ffine}" class="form-control">
						</c:if>
					</div>		   		
				</div>
				   		
				<div class="row form-group">
					<div class="col-3 col-md-4" style="padding-right:0%;">
						<label for="text-input" class=" form-control-label">備註:</label>
					</div>			      
					<div class="col-9 col-md-8">
					    <textarea name="o_note" id="o_note" rows="9" placeholder="請輸入備註" class="form-control"></textarea>
					</div>
				</div>				
						
					<input type="hidden" name="action" value="updateByDS"> 
					<input type="hidden" name="ds_no" value="${ds_no}">
					<input type="hidden" name="ro_no" value="${param.ro_no}">
					<input type="submit" class="btn btn-info btn-lg" value="送出修改">
			 </FORM>
		</div>		
	</div>	
</body>

<% 
java.sql.Date rr_date = null;
rr_date = new java.sql.Date(System.currentTimeMillis());

%>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script>
$.datetimepicker.setLocale('zh');
$('#rr_date').datetimepicker({
   theme: '',              //theme: 'dark',
   timepicker:false,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d',         //format:'Y-m-d H:i:s',
   value: '<%=rr_date%>',  // value:new Date(),
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:               '-1970-071-01', // 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});
 </script>
<div style="margin-top:3%;">
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</div>
</html>
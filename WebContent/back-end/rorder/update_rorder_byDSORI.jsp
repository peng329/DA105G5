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

	<div class="container" style="margin:auto;" >
			 <div class="row" style="position:relative;left:42.5%;">
					<font style="font-size:40px;">
						${diveshopVO.ds_name}
					</font>
			</div>
			 <div class="row" style="position:relative;left:42.5%;">
				<font style="font-size:40px;">
					訂單更新
				</font>
			</div>

	</div>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ROrder/rorder.do" name="form1">
		<table>
			<tr>
				<td>訂單編號:</td>
				<td>${param.ro_no}</td>
			</tr>


			<tr>
				<td>實際歸還日期:<font color=red><b>*</b></font></td>
				<td width="80%" colspan="3">
				<input name="rr_date" id="rr_date1" type="text">
				</td>
			</tr>
			
			<tr>
				<td>付款狀態:<font color=red><b>*</b></font></td>
				<td>
				<select size="1" name="op_state">
					<option value="已付款" ${(rorderVO.op_state=="已付款")? 'selected':'' } >已付款				
					<option value="未歸還" ${(rorderVO.op_state=="未付款")? 'selected':'' } >未付款
				</select>
				</td>
			</tr>
			<tr>
				<td>訂單狀態:<font color=red><b>*</b></font></td>
				<td>
				<select size="1" name="o_state">
					<option value="待取"${(rorderVO.o_state=="待取")? 'selected':'' } >待取				
					<option value="未歸還" ${(rorderVO.o_state=="未歸還")? 'selected':'' } >未歸還
					<option value="已歸還 "${(rorderVO.o_state=="已歸還")? 'selected':'' } >已歸還
				</select>
				</td>
			</tr>
			<tr>
				<td>罰鍰</td>
				<td>
				<c:if test="${rorderVO.ffine==0}">
				<input type="TEXT" name="ffine" size="20" value="<%=rorderVO.getFfine()%>" />
				</c:if>
				</td>
			</tr>

			<tr>
				<td>備註:<font color=red><b>*</b></font></td>
				<td>
				<input type="TEXT" name="o_note" size="20" value="<%=rorderVO.getO_note()%>" />
				</td>
			</tr>
		</table>
		<div style="text-align:center;"> 
		<input type="hidden" name="action" value="updateByDS"> 
		<input type="hidden" name="ds_no" value="${ds_no}">
		<input type="hidden" name="ro_no" value="${param.ro_no}">
		<input type="hidden" name="o_state" value="${rorderVO.o_state}">
		<input type="hidden" name="op_state" value="${rorderVO.op_state}">
		<input type="hidden" name="ffine" value="${rorderVO.ffine}">
		<input type="hidden" name="o_note" value="${rorderVO.o_note}">
		<input type="submit" class="btn btn-info btn-lg" value="送出修改">
		</div>
	</FORM>
 
 </body>

<% 
java.sql.Date rr_date = null;
rr_date = new java.sql.Date(System.currentTimeMillis());
System.out.println(rr_date);

%>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script>
$.datetimepicker.setLocale('zh');
$('#rr_date1').datetimepicker({
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
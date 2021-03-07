<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.equip.model.*,com.rorder.model.*,com.mem.model.*,com.eqpic.model.*"%>
<%
	ROrderVO rorderVO = (ROrderVO)request.getAttribute("rorderVO");
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	EqpicService eqpicSvc = new EqpicService();
	pageContext.setAttribute("eqpicSvc", eqpicSvc);
	pageContext.setAttribute("memVO", memVO);
	pageContext.setAttribute("rorderVO", rorderVO);
%>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<%@include file="/HeaderFooter/header.jsp"%>
<style>
table{
text-align:center;
}
  td,th{
 	font-color:black;
 	text-align:center;
  }

</style>
</head>
<body>
 <% 
 @SuppressWarnings("unchecked")
   Vector<EquipVO> eplist = (Vector<EquipVO>) session.getAttribute("eplist");
%>

<c:if test="${not empty errorMsgs}">
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>


<c:if test="${eplist.size()==0||eplist==null}">
	<div class="container" style="text-align:center;margin-top:5%;margin-bottom:5%;">
		<div class="row justify-content-md-center" style="font-size:40px;font-family: 'Noto Sans TC';margin-top:1%;"> ${memVO.mem_name} 您好！</div>
		<div class="row justify-content-md-center" style="font-size:40px;font-family: 'Noto Sans TC';margin-top:1%;">您的購物車尚無商品</div>
	</div>
</c:if>

<%
if (eplist != null && (eplist.size() > 0)) {
	Integer tpriz=0;
	Integer tepc=eplist.size();
%>

<div class="container" style="margin-top:1.5%;">
		<div style="text-align:center;margin-top:1.5%;margin-bottom:1.5%;">
			<div class="row" style="font-size:26px;font-family:'Noto Sans TC';">
					<font style="text-align:center;">${memVO.mem_name} 您好！</font>
			</div>
			<div class="row" style="font-size:26px;font-family:'Noto Sans TC';">
					<font style="text-align:center;">您的購物車內容如下：</font>
			</div>
		</div>
		
		<div class="row" style="background-color:#65c4e0;text-align:center;
						 height:5%;color:white;font-size:25px;font-family:'Noto Sans TC';">
			<div class="col-md">裝備快照</div>
			<div class="col-md">裝備名稱</div>
			<div class="col-md">裝備尺寸</div>
			<div class="col-md">租金</div>
			<div class="col-md">刪除</div>
		</div>
		<%
	
		 for (int index = 0; index < eplist.size(); index++) {
			EquipVO equipVO = eplist.get(index);
			tpriz+=equipVO.getEp_rp();
			String ep_seq = equipVO.getEp_seq();
	 		pageContext.setAttribute("equipVO",equipVO);
	 		pageContext.setAttribute("ep_seq",ep_seq);
	 		pageContext.setAttribute("tepc",tepc);
			pageContext.setAttribute("tpriz",tpriz);
		%>
	
		<div class="row" style="text-align:center;font-size:22px;">
				<div class="col-md" style="width:50%;margin-top:1%;">
				  <c:if test="${eqpicSvc.AEp_seq_All_Epic_seq(ep_seq).size()!=0}">
					<c:set var = "epic_seq" value="${eqpicSvc.AEp_seq_All_Epic_seq(ep_seq).get(0)}"/>
							<a href="<%=request.getContextPath()%>/front-end/equip/listOneEquip.jsp?ep_seq=${ep_seq}">
							<img src="<%=request.getContextPath()%>/PrintPic?epic_seq=${epic_seq}" 
								 style="position:relative;width:50%;">
							</a>
						</c:if>
				</div>
				<div class="col-md" style="width:50%;margin:auto;">${equipVO.ep_name}</div>
				<div class="col-md" style="width:50%;margin:auto;">${equipVO.ep_size}</div>
				<div class="col-md" style="width:50%;margin:auto;">${equipVO.ep_rp}</div>
				<div class="col-md" style="width:50%;margin:auto;">
					<form name="deleteForm" action="<%=request.getContextPath()%>/RentCart/rentcart.do" method="POST" style="margin:6%;">
					      <input type="hidden" name="action"  value="DELETE">
					      <input type="hidden" name="del" value="<%= index %>">
					      <input type="submit" value="刪 除" class="btn btn-danger">
					</form>
				</div>
			</div>
		<%
		 	}
		 %>
	<FORM name="checkoutForm" action="<%=request.getContextPath()%>/RentCart/rentcart.do" method="POST" 
		  style="text-align:center;margin-top:5%;margin-bottom:3%;">
	
		<div class="row" style="margin:auto;font-size:20px">
		訂單備註：
			<textarea cols="160" rows="5" name="o_note"  
					  onFocus="if(this.value==this.defaultValue) this.value=''" 
					  onBlur="if(this.value=='') this.value=this.defaultValue">請輸入備註，文字上限為300字</textarea>
		</div>
		
		
		<div class="row" style="background-color:#65c4e0;height:5%;color:white;font-size:25px;margin-top:1%;font-family:'Noto Sans TC';" >
			<font style="position:relative;left:1%;">請選擇預計租賃日期：</font>
		</div>
		
		<div class="row" style="margin-top:1%;position:relative;left:1%;font-size:22px;">
			租賃起始日期：
			<input name="rs_date" id="start_date" type="text">
		</div>
		
		<div class="row" style="margin-top:1%;position:relative;left:1%;font-size:22px;">
			租賃結束日期：
			<input name="rd_date" id="end_date"  type="text" >
		</div>
		
		<div class="row" style="background-color:#65c4e0;height:5%;color:white;
			 font-size:24px;margin-top:1%;width:30%;position:relative;left:73%;" >
			<font style="position:relative;left:2%;font-family:'Noto Sans TC';">小計：</font>
		</div>
		
		<div class="row" style="position:relative;margin:auto;left:80%;font-size:25px;">
			總件數 : ${tepc}
		</div>
		
		<div class="row" style="position:relative;margin:auto;left:80%;font-size:25px;">
			總金額 : ${tpriz}
		</div>

		              <input type="hidden" name="tepc"  value="${tepc}">               
		              <input type="hidden" name="tepc"  value="${tepc}">
					  <input type="hidden" name="tpriz"  value="${tpriz}"> 
		              <input type="hidden" name="mem_no" value="${memVO.mem_no}"> 		              
		              <input type="hidden" name="action" value="CHECKOUT"> 
		              <input type="submit" value="送出訂單" class="btn btn-outline-info btn-lg" >
		</FORM>
</div>
<%}%>
<%@include file="/HeaderFooter/footer.jsp"%>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<% 
  java.sql.Date rs_date = null;
  try {
	  rs_date = rorderVO.getRr_date();
   } catch (Exception e) {
	   rs_date = new java.sql.Date(System.currentTimeMillis());
   }
%>

<script>
$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#start_date').datetimepicker({
	  format:'Y-m-d',
	  value: <%=rs_date %>,
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#end_date').val()?$('#end_date').val():false
	   })
	  },
	  timepicker:false
	 });
	 
	 $('#end_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#start_date').val()?$('#start_date').val():false
	   })
	  },
	  timepicker:false
	 });
});
</script>
<script>
function clearDefault(t) {  
    if (t.value == t.defaultValue) t.value = "";  
	}  
function recallDefault(t) {  
    if (t.value == "") t.value = t.defaultValue;  
}  
</script>
   <%session.setAttribute("A", "A"); %>
</body>
</html>
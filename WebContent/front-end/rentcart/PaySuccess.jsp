<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.equip.model.*,com.rorder.model.*,com.rodetail.model.*"%>

<% ;
ROrderVO rorderVO = (ROrderVO)request.getAttribute("rorderVO");
String ro_no =(String) request.getAttribute("ro_no");
pageContext.setAttribute("rorderVO", rorderVO);
pageContext.setAttribute("ro_no", ro_no);
System.out.println(ro_no);
%>
<%@include file="/HeaderFooter/header.jsp" %>
<style>
body {
  font-family: "Open Sans", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", Helvetica, Arial, sans-serif; 
}
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
<div class="container" style="margin-top:3%;margin-bottom:3%;text-align:center;">
	<div class="row justify-content-md-center" style="font-size:1.5em;font-family:'Noto Sans TC';">
	以下為您的訂單資訊：
	</div>	
	
	<div class="row" style="background-color:#65c4e0;margin-top:1%;margin-bottom:1%;color:white;height:5%;font-size:20px;font-family:'Noto Sans TC';">
		<div class="col-2">訂單編號</div>
		<div class="col">總件數</div>
		<div class="col">總價格</div>
		<div class="col">訂單狀態</div>
		<div class="col">租賃開始日</div>
		<div class="col">租賃結束日</div>
		<div class="col">訂單備註</div>
	</div>
	
	<div class="row" style="font-size:18px;">
		<div class="col-2">
			<a href="<%=request.getContextPath()%>/front-end/rentcart/rodetaillist.jsp?ro_no=${ro_no}" class="btn btn-outline-info">${ro_no}</a>
		</div>
		<div class="col">${rorderVO.tepc}</div>
		<div class="col">${rorderVO.tpriz}</div>
		<div class="col">${rorderVO.op_state}</div>
		<div class="col">${rorderVO.rs_date}</div>
		<div class="col">${rorderVO.rd_date}</div>
		<div class="col">
			<c:if test="${!rorderVO.o_note.contains('請輸入備註')}">
			${rorderVO.o_note}
			</c:if>
		</div>
	</div>
</div>
</body>
<%@include file="/HeaderFooter/footer.jsp"%>
</html>
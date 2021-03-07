<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.rorder.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
   ROrderService rorderSvc = new ROrderService();
   String mem_no = request.getParameter("mem_no");
    List<ROrderVO> list = rorderSvc.getAMenAllRo(mem_no);
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>會員的所有訂單資料- listAMemRO.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>會員的所有訂單資料- listAMemRO.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/rorder/ROselect_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>訂單編號</th>
		<th>潛店編號</th>
		<th>總件數</th>
		<th>總價格</th>
		<th>訂單狀態</th>
		<th>租賃開始日</th>
		<th>租賃結束日</th>
		<th>訂單備註</th>
	</tr>
<%@ include file="page1.file" %> 
	<c:forEach var="rorderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${rorderVO.ro_no}</td>
			<td>${rorderVO.ds_no}</td>
			<td>${rorderVO.tepc}</td>
			<td>${rorderVO.tpriz}</td>
			<td>	
				<FORM action="<%=request.getContextPath()%>/front-end/creditcard/credit_card.jsp" method="post">
				<c:if test="${rorderVO.op_state.equals('未付款')}">
					<input type="hidden" name="ro_no" value="${rorderVO.ro_no}">
					<input type="submit" value="未付款">
				</c:if>	
				</FORM>
				<c:if test="${rorderVO.op_state.equals('已付款')}">
					${rorderVO.op_state}	
				</c:if>	
			</td>		
			<td>${rorderVO.rs_date}</td>
			<td>${rorderVO.rd_date}</td>
			<td>${rorderVO.o_note}</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %> 

</body>
</html>
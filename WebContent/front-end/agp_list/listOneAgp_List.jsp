<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.agp_list.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Agp_List_VO agp_list_vo = (Agp_List_VO) request.getAttribute("agp_list_vo"); //Agp_List_Servlet.java(Concroller), 存入req的act_list_vo物件
%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->
<style>
body{
background-image: url("images/water2.png");
background-size: cover;
background-attachment: fixed;

}
.row{
background-color:white;
/* background:rgba(0,0,0,0.1); */
}

</style>
<!-- ----------------------------------------------------------------------- -->
<body bgcolor='white'>

<div class="container ">
<div class="row" style="height:15%;background:rgba(0,0,0,0);">
	</div>


	<div class="row align-items-center justify-content-center" style="border:2px #ccc solid;border-radius:10px;">
		<div class="col-lg-8" style=" margin: 20px; padding: 15px;border:2px #ccc solid;border-radius:10px;">

<!-- <h4>此頁暫練習採用 Script 的寫法取值:</h4> -->
<!-- <table id="table-1"> -->
<!-- 	<tr><td> -->
<!-- 		 <h3>資料 - ListOneAgp_List.jsp</h3> -->
<!-- 		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4> -->
<!-- 	</td></tr> -->
<!-- </table> -->

<table>
	<tr>
		<th>揪團編號</th>
		<th>會員編號</th>
		<th>潛水證號</th>
		<th>報名人數</th>
		<th>潛水證圖片</th>
		<th>報名狀態</th>
	</tr>

	<tr>
			<td>${agp_list_vo.act_list_no}</td>
			<td>${agp_list_vo.mem_no}</td>
			<td>${agp_list_vo.mem_lic}</td>
			<td>${agp_list_vo.act_num}</td>
<%-- 			<td>${agp_list_vo.mem_lic_pic}</td> --%>
			<td>
			<img src="<%=request.getContextPath()%>/front-end/agp_list/DBGifReader?act_list_no=${agp_list_vo.act_list_no}&mem_no=${agp_list_vo.mem_no}">
			</td>
			<td>${agp_list_vo.agp_state}</td>
	</tr>	
</table>
</div>
</div>
</div>
</body>
</html>
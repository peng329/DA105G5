<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gp_ept.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	Gp_Ept_VO gp_ept_vo = (Gp_Ept_VO) request.getAttribute("gp_ept_vo"); //Gp_Ept_Servlet.java(Concroller), 存入req的gp_ept_vo物件
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


	<table>
		<tr>
			<th>揪團裝備明細編號</th>
			<th>揪團編號</th>
			<th>會員編號</th>
			<th>裝備分類編號</th>
			<th>數量</th>
			<th>SIZE</th>
		</tr>
		<tr>
			<td>${gp_ept_vo.gp_ept_no}</td>
			<td>${gp_ept_vo.act_list_no}</td>
			<td>${gp_ept_vo.mem_no}</td>
			<td>${gp_ept_vo.epc_no}</td>
			<td>${gp_ept_vo.gp_t_num}</td>
			<td>${gp_ept_vo.gp_t_size}</td>
		</tr>
	</table>
</div>
</div>
</div>
</body>
</html>
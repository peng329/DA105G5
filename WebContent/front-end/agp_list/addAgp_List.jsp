<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.agp_list.model.*"%>
<%@ page import="com.mem.model.*"%>

<%
	Agp_List_VO agp_list_vo = (Agp_List_VO) request.getAttribute("agp_list_vo");
	String act_list_no =  (String)session.getAttribute("act_list_no");
	
	
	String mem_no =  (String)session.getAttribute("mem_no");

	MemVO mem_vo = (MemVO)session.getAttribute("memVO");
%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->
<style>
body{
background-image: url("images/ocean.jpg");
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


<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="agp_list.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
	<td>揪團編號:<font color=red><b>*</b></font></td>
		<td>
		<%=act_list_no%>
		</td>
	</tr>
	<tr>
		<td>團主會員:</td>
		<td>
		<input type="hidden" name="mem_no" size="45" value= "<%= mem_vo.getMem_no()%>" />
		<%= mem_vo.getMem_name()%>
		</td>
	</tr>
	<tr>
		<td>潛水證號:</td>
		<td><input type="TEXT" name="mem_lic" size="45" id="mem_lic"
			 value="<%= (agp_list_vo==null)? "" : agp_list_vo.getMem_lic()%>" /></td>
	</tr>
	<tr>
		<td>報名人數:</td>
		<td>
		<input class="minus btn"  type="button" value="-">
		<input type="number" name="act_num" size="45" min="1" class="count"	style="width:10%;" readonly=readonly  value="<%= (agp_list_vo==null)? "2" : agp_list_vo.getAct_num()%>" />
		<input class="plus btn"   type="button" value="+" >
		</td>
	</tr>
	<tr>
		<td>潛水證圖片:</td>
		<td>
		<input type="file"  name="mem_lic_pic" onchange="loadFile(event)"/>
		<div><img id="output"  style="height:30%;width:30%;"/></div> 
		</td>
	</tr>
<!-- 	<tr> -->
<!-- 		<td>報名狀態:</td> -->
<!-- 		<td> -->
<!-- 		<select size="1" name="agp_state"> -->
<%-- 				<option value= "待審核" ${( agp_list_vo.agp_state=="待審核") ? 'selected':'' }>待審核</option> --%>
<%-- 				<option value= "通過 " ${( agp_list_vo.agp_state=="通過") ? 'selected':'' }>通過</option> --%>
<%-- 				<option value= "不通過" ${( agp_list_vo.agp_state=="不通過") ? 'selected':'' }>不通過</option> --%>
<!-- 		</select> -->
<!-- 		</td> -->		
<!-- 	</tr> -->
</table>
<br>
<!-- <input type="hidden" name="action" value="Join_management2"> -->
<input type="hidden" name="agp_state"  value="待審核" >
<input type="hidden" name="action" value="insert">
<input type="submit" class="btn btn-info" value="送出新增"></FORM>

		<button class="btn btn-info btn-lg" type="button" id="star" style="float:right;">
			<i class="fa fa-star" style="color:yellow;"></i> 
		</button>

</div>
</div>
</div>

</body>
<!-- ======================================== 圖片預覽 ================================================== -->
<script>
var loadFile = function(event) {
 var output = document.getElementById('output');
 output.src = URL.createObjectURL(event.target.files[0]);
};        

<!-- ======================================== 計算 ================================================== -->

$(document).ready(function(){
    $('.count').prop('disabled', false);
		$(document).on('click','.plus',function(){
		$(this).prev('.count').val(parseInt($(this).prev('.count').val()) + 1 );
	});
	$(document).on('click','.minus',function(){
		$(this).next('.count').val(parseInt($(this).next('.count').val()) - 1 );
			if ($(this).next('.count').val() == 0) {
				$(this).next('.count').val(1);
			}
    	});
	}); 

<!-- ======================================== 神奇小按鈕 ================================================== -->

$(document).ready(function(){
	$('#star').click(function(){
// 		alert("凱鈞好胖");
		$('#mem_lic').val("P2-180002");
	})
});
</script>


</html>
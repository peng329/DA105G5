<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.agp_list.model.*"%>

<%
	Agp_List_VO agp_list_vo = (Agp_List_VO) request.getAttribute("agp_list_vo"); //Agp_ListServlet.java (Concroller) 存入req的agp_list_vo物件 (包括幫忙取出的agp_list_vo, 也包括輸入資料錯誤時的agp_list_vo物件)
%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->

<style>
.bg {
	background-image: url("<%=request.getContextPath()%>/front-end/act_list/images/ocean.jpg");
	background-size: cover;
	background-attachment: fixed;
	}

.row {
	background-color: white;
	/* background:rgba(0,0,0,0.1); */
}

</style>
<!-- ----------------------------------------------------------------------- -->
<body>
<div class="bg">
	<div class="container ">
		<div class="row" style="height: 15%; background: rgba(0, 0, 0, 0);">
		</div>


		<div class="row align-items-center justify-content-center"
			style="border: 2px #ccc solid; border-radius: 10px;">
			<div class="col-lg-8"
				style="margin: 20px; padding: 15px; border: 2px #ccc solid; border-radius: 10px;">

				<%-- 錯誤表列 --%>
<%-- 				<c:if test="${not empty errorMsgs}"> --%>
<!-- 					<font style="color: red">請修正以下錯誤:</font> -->
<!-- 					<ul> -->
<%-- 						<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 							<li style="color: red">${message}</li> --%>
<%-- 						</c:forEach> --%>
<!-- 					</ul> -->
<%-- 				</c:if> --%>

				<FORM METHOD="post" ACTION="agp_list.do" name="form1"
					enctype="multipart/form-data">
					<table>
						<tr>
							<td>揪團編號:<font color=red><b>*</b></font></td>
							<td><%=agp_list_vo.getAct_list_no()%></td>
						</tr>
						<tr>
							<td>會員編號:<font color=red><b>*</b></font></td>
							<td><%=agp_list_vo.getMem_no()%></td>
						</tr>
						<tr>
							<td>潛水證號:</td>
							<td><%=agp_list_vo.getMem_lic()%></td>
						</tr>
						<tr>
							<td>報名人數:</td>
							<td><%=agp_list_vo.getAct_num()%></td>
						</tr>
						<tr>
							<td>潛水證圖片:</td>
							<td>
							<td>
<!-- 								<input type="file"  name="mem_lic_pic" onchange="loadFile(event)"/> -->
<!-- 								<div><img id="output"  style="height:30%;width:30%;"/></div>  -->
<!-- 								<div id="image"> -->
									<img width="30%" height="30%"
										src="<%=request.getContextPath()%>/front-end/agp_list/DBGifReader?act_list_no=${agp_list_vo.act_list_no}&mem_no=${agp_list_vo.mem_no}" />
<!-- 								</div> -->
								
							</td>
						</tr>
						<tr>
							<td>報名狀態:</td>
							<td><select size="1" name="agp_state">
									<option value="待審核"
										${( agp_list_vo.agp_state=="待審核") ? 'selected':'' }>待審核</option>
									<option value="通過 "
										${( agp_list_vo.agp_state=="通過") ? 'selected':'' }>通過</option>
									<option value="不通過"
										${( agp_list_vo.agp_state=="不通過") ? 'selected':'' }>不通過</option>
							</select></td>
						</tr>



					</table>
					<br>
<!-- 					<input type="hidden" name="action" value="Join_management2"> -->
					<input type="hidden" name="act_list_no"	value="<%=agp_list_vo.getAct_list_no()%>">
					<input type="hidden" name="mem_no" value="<%=agp_list_vo.getMem_no()%>">
					<input type="hidden" name="mem_lic" value="<%=agp_list_vo.getMem_lic()%>">
					<input type="hidden" name="act_num" value="<%=agp_list_vo.getAct_num()%>">
					<input type="hidden"  name="mem_lic_pic" value="<%=agp_list_vo.getMem_lic_pic()%>">
					<input type="hidden" name="action" value="update">
					<input type="submit" class="btn btn-info " value="送出修改">
				</FORM>
				
			</div>
		</div>
		
		
	<!-- ----------------------------------------------------------------------- -->
			<div class="row" style="height:50%;background:rgba(0,0,0,0);">
		</div>
		
	</div>
	</div>
	
</body>

<!-- =================================================預覽圖片======================================================== -->
<script>
	document.getElementById('file').onchange = function() {
		var imgFile = this.files[0];
		var fr = new FileReader();
		fr.onload = function() {
			document.getElementById('image').getElementsByTagName('img')[0].src = fr.result;
		};
		fr.readAsDataURL(imgFile);
	};
</script>



</html>
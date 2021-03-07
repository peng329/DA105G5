<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp_ept.model.*"%>
<%@ page import="com.equipc.model.*"%>
<%@ page import="com.mem.model.*"%>

<%
		Gp_Ept_VO gp_ept_vo = (Gp_Ept_VO) request.getAttribute("gp_ept_vo");

		EquipCService equipcSvc = new EquipCService();
		List<EquipCVO> list = equipcSvc.getAll();
		pageContext.setAttribute("list", list);

		Vector<Gp_Ept_VO> list_ep = (Vector<Gp_Ept_VO>)session.getAttribute("list_ep"); 

		
		String act_list_no =  (String)session.getAttribute("act_list_no");
		String mem_no =  (String)session.getAttribute("mem_no");
		
		MemVO mem_vo = (MemVO)session.getAttribute("memVO");
%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->
<style>
.bg{
background-image: url("<%=request.getContextPath()%>/front-end/act_list/images/ocean.jpg");
background-size: cover;
background-attachment: fixed;

}
.row{
background-color:white;
/* background:rgba(0,0,0,0.1); */
}

.fixed-top {
padding-left: 20%;
}
th{
padding: 10px;
}
</style>
<!-- ----------------------------------------------------------------------- -->
<body>
<div class="bg">
	<div class="container ">
	<div class="row" style="height:10%;background:rgba(0,0,0,0);">
	</div>
				<h2 class="text-center">報名</h2>

		<!-- ----------------------------------裝備------------------------------------- -->
	<div class="row justify-content-center" style="border:2px #ccc solid;border-radius:10px;">

				<c:forEach var="EquipCVO" items="${list}">

				<div class="col-xl-2 col-md-3" style="margin: 20px; padding: 15px; ">

					<FORM METHOD="post" ACTION="gp_ept.do"
						style="margin-bottom: 0px;">
						<!-------------------------------------------------------------------------------->

						<div class="row align-items-center justify-content-center" style="border:2px #ccc solid;border-radius:10px;">

							<div class="card" >
								<img class="card-img-top" src="<%=request.getContextPath()%>/front-end/act_list/images/${EquipCVO.epc_no}.jpg" alt=""  />
								<div class="card-block">
									<h4 class="card-title" style="text-align:center;">${EquipCVO.epc_name}</h4>
									<!-------------------------------------------------------------------------------->
									<hr>
									<table>

										<tr>
											<th>裝備分類編號</th>
											<td>${EquipCVO.epc_no}</td>
										</tr>
										<tr>
											<th>裝備分類名稱</th>
											<td>${EquipCVO.epc_name}</td>

										</tr>
										<tr>
											<th>數量</th>
											<td>
												<input class="minus btn"  type="button" value="-">
												<input type="number" class="count"   name="gp_t_num" size="3" value=1  readonly=readonly style="width:30%;">
												<input class="plus btn"   type="button" value="+" >
											</td>
										</tr>
										<tr>
											<th>SIZE</th>

											<td>
												<div data-value="S class=" swatch-element plainsavailable">
													<input id="swatch-0-s" type="radio" name="epc_size"
														value="S" checked=""> <label for="swatch-0-s">
														S </label>
												</div>

												<div data-value="M" class="swatch-element plain m available">
													<input id="swatch-0-m" type="radio" name="epc_size"
														value="M"> <label for="swatch-0-m"> M </label>
												</div>

												<div data-value="L" class="swatch-element plain l available">
													<input id="swatch-0-l" type="radio" name="epc_size"
														value="L"> <label for="swatch-0-l"> L </label>
												</div>

												<div data-value="XL"
													class="swatch-element plain xl available">
													<input id="swatch-0-xl" type="radio" name="epc_size"
														value="XL"> <label for="swatch-0-xl"> XL </label>
												</div>

											</td>
										</tr>



									</table>
									<input type="submit" value="加入"
										class="btn btn-info btn-primary btn-block" style="padding:20px;"> <input
										type="hidden" name="epc_no" value="${EquipCVO.epc_no}">
									<input type="hidden" name="epc_name"
										value="${EquipCVO.epc_name}"> <input type="hidden"
										name="action" value="addep">
									<!-------------------------------------------------------------------------------->

								</div>

							</div>
						</div>

						<!-------------------------------------------------------------------------------->

					</FORM>


				</div>

			</c:forEach>

			</div>



			<!-- ----------------------------------------------------------------------- -->
			<div class="row" style="height:10%;background:rgba(0,0,0,0);">
			</div>
			
			<!-- ----------------------------------------------------------------------- -->
<div class="row align-items-center justify-content-center" style="border:2px #ccc solid;border-radius:10px;">
		<div class="col-lg-2">
			<div class="col-md-3 text-right">
				<button class="btn btn-info btn-primary btn-lg" type="button"
					data-toggle="modal" data-target="#cart">
					<i class="fa fa-list-alt"></i> 查看
				</button>
			</div>

		</div>
			<!-- ----------------------------------------------------------- -->
		<div class="col-lg-8" style=" margin: 20px; padding: 15px;border:2px #ccc solid;border-radius:10px;">

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="gp_ept.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
	<td>揪團編號:<font color=red><b>*</b></font></td>
	<td><%=act_list_no%></td>	
	</tr>
	<tr>
		<td>團主會員:</td>
		<td><input type="hidden" name="mem_no" size="45"
		value= "<%= mem_vo.getMem_no()%>" />
		<%= mem_vo.getMem_name()%>
		</td>
	</tr>
				

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" class="btn btn-info " value="送出新增"></FORM>
</div>
			</div>
	
	<!-- ----------------------------------------------------------- -->
	<div class="modal fade" id="cart">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">清單</h5>
					<button class="close" type="button" data-dismiss="modal">×</button>
				</div>
				<div class="modal-body">
					<table class="table">
						<tbody>
<!-- -------------------------------------------------------------------------------------------- -->
	<%if (list_ep != null && (list_ep.size() > 0)) {%>




	<tr bgcolor="#999999">
<!-- 		<th>揪團裝備明細編號</th> -->
<!-- 		<th>揪團編號</th> -->
<!-- 		<th>會員編號</th> -->
		<th>裝備大小</th>
		<th>裝備分類編號</th>
<!-- 		<th>裝備分類名稱</th> -->
		<th >數量</th>
		<th>移除</th>
	</tr>
	
	<%
	 for (int index = 0; index < list_ep.size(); index++) {
		 Gp_Ept_VO order = list_ep.get(index);
	%>
	<tr>
<%-- 		<td><div align="center"><%= order.getAct_list_no()%></div></td> --%>
<%-- 		<td><div align="center"><%= order.getGp_ept_no()%></div></td> --%>
<%-- 		<td><div align="center"><%= order.getMem_no()%></div></td> --%>
		<td><div align="center"><%= order.getGp_t_size()%></div></td>
		<td><div align="center"><%= order.getEpc_no()%></div></td>
		<td><div align="center"><%= order.getGp_t_num()%></div></td>
		<td>
	<form name="" action="gp_ept.do"method="POST">
	<input type="hidden" name="action" value="deletep">
	<input type="hidden" name="del" value="<%=index %>">
	<input type="submit" class="btn btn-outline-info" value="刪除">
	</form>	
		</td>
	</tr>
	<%}%>

<%}%>
<!-- -------------------------------------------------------------------------------------------- -->
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button class="btn btn-info btn-primary" data-dismiss="modal">確定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- ----------------------------------------------------------- -->
	</div>
	<div class="row" style="height:10%;background:rgba(0,0,0,0);">
			</div>
</div>
</body>
<script type="text/javascript">
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
</script>

</html>
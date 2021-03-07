<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act_list.model.*"%>
<%@ page import="com.gp_ept.model.*"%>
<%@ page import="com.equipc.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.dive_point.model.*"%>

<%
	Act_List_VO act_list_vo = (Act_List_VO) request.getAttribute("act_list_vo");
	Gp_Ept_VO gp_ept_vo = (Gp_Ept_VO) request.getAttribute("gp_ept_vo");

	EquipCService equipcSvc = new EquipCService();
	List<EquipCVO> list = equipcSvc.getAll();
	pageContext.setAttribute("list", list);

	Vector<Gp_Ept_VO> list_ep = (Vector<Gp_Ept_VO>) session.getAttribute("list_ep");
	
	MemVO mem_vo = (MemVO)session.getAttribute("memVO");
	
	DpService dpSvc = new DpService();
	List<DpVO> dplist = dpSvc.getAll();
	pageContext.setAttribute("dplist", dplist);

%>

<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->
<style>
.bg{
background-image: url("images/ocean.jpg");
background-attachment: fixed;
background-size: cover;
/* background-opacity: 0.3;  */
}
.row{
background-color:white;
/* background:rgba(0,0,0,0.1); */
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

					<FORM METHOD="post" ACTION="act_list.do"
						style="margin-bottom: 0px;">
						<!-------------------------------------------------------------------------------->

						<div class="row align-items-center justify-content-center" style="border:2px #ccc solid;border-radius:10px;">

							<div class="card" >
								<img class="card-img-top" src="images/${EquipCVO.epc_no}.jpg" alt=""  />
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

			<div class="col-md-3" style="margin-top:20px;">
				<button class="btn btn-info btn-lg" type="button" id="star">
					<i class="fa fa-star" style="color:yellow;"></i> 
				</button>
			</div>
			<!-- ----------------------------------------------------------- -->
		</div>
		
		<div class="col-lg-8" style=" margin: 20px; padding: 15px;border:2px #ccc solid;border-radius:10px;">
		
		<FORM METHOD="post" ACTION="act_list.do" name="form1"	enctype="multipart/form-data">

			
				<table>
					<tr>
						<td>團名:</td>
						<td><input type="TEXT" name="act_list_name" size="45" id="act_list_name"
							value="<%=(act_list_vo == null) ? "" : act_list_vo.getAct_list_name()%>" /></td>
					</tr>
					<tr>
						<td>團主會員:</td>
						<td><input type="hidden" name="mem_no" size="45" id="mem_no"
							value= "<%= mem_vo.getMem_no()%>" />
							<%= mem_vo.getMem_name()%>
							</td>
					</tr>
					<tr>
						<td>潛點編號:</td>
						<td>
						<select size="1" name="dp_no" id="dp_no">
							<c:forEach var="DpVO" items="${dplist}">
									<option value="${DpVO.dp_no}"${ DpVO.dp_no==act_list_vo.dp_no ? 'selected':'' }>${ DpVO.dp_name}</option>
							</c:forEach>
						</select>						
						</td>
					</tr>
					<tr>
						<td>報名起始日:</td>
						<td><input name="start_date" id="f_date1" type="text"></td>
					</tr>
					<tr>
						<td>報名截止日:</td>
						<td><input name="dual_date" id="f_date2" type="text"></td>
					</tr>
					<tr>
						<td>出團日:</td>
						<td><input name="action_date" id="f_date3" type="text"></td>
					</tr>
					<tr>
						<td>開團狀態:</td>
						<td><select size="1" name="act_state" id="act_state">
								<option value="發團中"
									${( act_list_vo.act_state=="發團中") ? 'selected':'' }>發團中</option>
								<option value="已開中"
									${( act_list_vo.act_state=="已開團") ? 'selected':'' }>已開團</option>
								<option value="檢舉"
									${( act_list_vo.act_state=="檢舉") ? 'selected':'' }>檢舉</option>
						</select></td>
					</tr>
					<tr>
						<td>揪團地點:</td>
						<td><input name="locale" id="address" size="45"
							class=twaddress
							value="<%=(act_list_vo == null) ? "" : act_list_vo.getLocale()%>" /></td>
					</tr>
					<tr>
						<td>參加人上限:</td>
						<td><input type="TEXT" name="act_max" size="45" id="act_max"
							value="<%=(act_list_vo == null) ? "" : act_list_vo.getAct_max()%>" /></td>
					</tr>
					<tr>
						<td>參加人下限:</td>
						<td><input type="TEXT" name="act_min" size="45" id="act_min"
							value="<%=(act_list_vo == null) ? "" : act_list_vo.getAct_min()%>" /></td>
					</tr>
					<tr>
						<td>揪團圖片:</td>
						<td><input type="file" name="gp_pic"
							onchange="loadFile(event)" />
							<div>
								<img id="output" style="height: 30%; width: 30%;" />
							</div></td>
					</tr>
					<tr>
						<td>揪團介紹:</td>
						<td><input type="TEXT" name="gp_info" size="45" id="gp_info"
							value="<%=(act_list_vo == null) ? "" : act_list_vo.getGp_info()%>" /></td>
					</tr>
					<tr>
						<td>天數:</td>
						<td><input type="TEXT" name="gp_days" size="45" id="gp_days"
							value="<%=(act_list_vo == null) ? "" : act_list_vo.getGp_days()%>" /></td>
					</tr>


				</table>

				<!-- ----------------------------------------------------------- -->
				<br> 
<!-- 				<input type="hidden" name="action" value="Group_management"> -->
				<input type="hidden" name="list_ep">
				<input type="hidden" name="action" value="insertWithGpEpt">
				<input type="submit"  class="btn btn-info" value="送出新增">
			</FORM>
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
									<%
										if (list_ep != null && (list_ep.size() > 0)) {
									%>


									<tr bgcolor="#999999">

										<th>裝備大小</th>
										<th>裝備分類編號</th>
										<th>數量</th>
										<th>移除</th>
									</tr>

									<%
										for (int index = 0; index < list_ep.size(); index++) {
												Gp_Ept_VO order = list_ep.get(index);
									%>
									<tr>
										<td><div align="center"><%=order.getGp_t_size()%></div></td>
										<td><div align="center"><%=order.getEpc_no()%></div></td>
										<td><div align="center"><%=order.getGp_t_num()%></div></td>
										<td>
											<form name="" action="act_list.do" method="POST">
												<input type="hidden" name="action" value="deletep">
												<input type="hidden" name="del" value="<%=index%>">
												<input type="submit" class="btn btn-outline-info" value="刪除">
											</form>
										</td>
									</tr>
									<%
										}
									%>

									<%
										}
									%>
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
		<!-- ----------------------------------------------------------------------- -->
			<div class="row" style="height:10%;background:rgba(0,0,0,0);">
			</div>
	</div>
</body>
<script>
<!-- ======================================== 圖片預覽 ================================================== -->
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
		$('#act_list_name').val("快樂無法擋");
		$('#dp_no').val("DP000001");
		$('#act_state').val("發團中");
// 		$('#address').val("桃園市中壢區中央路303號");
		$('#act_max').val(10);
		$('#act_min').val(2);
		$('#gp_info').val("走起");
		$('#gp_days').val(3);
	})
});
	
 </script>

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<%
	java.sql.Date start_date = null;
	try {
		start_date = act_list_vo.getStart_date();
	} catch (Exception e) {
		start_date = new java.sql.Date(System.currentTimeMillis());
	}
%>

<%
	java.sql.Date dual_date = null;
	try {
		dual_date = act_list_vo.getDual_date();
	} catch (Exception e) {
		dual_date = new java.sql.Date(System.currentTimeMillis());
	}
%>

<%
	java.sql.Date action_date = null;
	try {
		action_date = act_list_vo.getAction_date();
	} catch (Exception e) {
		action_date = new java.sql.Date(System.currentTimeMillis());
	}
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js">
	
	</script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
<%@ include file="address.file" %>
$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	$('#f_date1').datetimepicker({
		  format:'Y-m-d',
		  value:<%=start_date%>,
		  onShow:function(){
		   this.setOptions({
			   minDate:new Date()
		   })
		  },
		  timepicker:false
		 });
	
	
	
	 $('#f_date2').datetimepicker({
	  format:'Y-m-d',
	  value:<%=start_date%>,
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#f_date1').val()?$('#f_date1').val():false
	   })
	  },
	  timepicker:false
	 });
	 

	 
	 
	 $('#f_date3').datetimepicker({
	  format:'Y-m-d',
	  value:<%=dual_date%>,
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#f_date2').val()?$('#f_date2').val():false
	   })
	  },
	  timepicker:false
	 });
});
      
</script>
</html>
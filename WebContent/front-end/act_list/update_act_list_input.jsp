<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act_list.model.*"%>
<%@ page import="com.dive_point.model.*"%>

<%
Act_List_VO act_list_vo = (Act_List_VO) request.getAttribute("act_list_vo"); //Act_ListServlet.java (Concroller) 存入req的act_list_vo物件 (包括幫忙取出的act_list_vo, 也包括輸入資料錯誤時的act_list_vo物件)

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
background-image: url("<%=request.getContextPath()%>/front-end/act_list/images/ocean.jpg");
background-size: cover;
background-attachment: fixed;

}

.row{
background-color:white;
/* background:rgba(0,0,0,0.1); */
}

</style>
<!-- ----------------------------------------------------------------------- -->
<body>

<div class="bg">
<div class="container ">
<div class="row" style="height:10%;background:rgba(0,0,0,0);">
	</div>


	<div class="row align-items-center justify-content-center" style="border:2px #ccc solid;border-radius:10px;">
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
	
<FORM METHOD="post" ACTION="act_list.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>揪團編號:<font color=red><b>*</b></font></td>
		<td><%=act_list_vo.getAct_list_no()%></td>
	</tr>
	<tr>
		<td>團名:</td>
		<td><input type="TEXT" name="act_list_name" size="45" value="<%=act_list_vo.getAct_list_name()%>" /></td>
	</tr>
	<tr>
		<td>團主會員編號:<font color=red><b>*</b></font></td>
		<td><%=act_list_vo.getMem_no()%></td>
	</tr>
	<tr>
		<td>潛點:</td>
		<td>
			<select size="1" name="dp_no">
					<c:forEach var="DpVO" items="${dplist}">
							<option value="${DpVO.dp_no}"${ DpVO.dp_no==act_list_vo.dp_no ? 'selected':'' }>${ DpVO.dp_name}</option>
					</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>報名起始日:</td>
		<td><input name="start_date" id="f_date1" type="text" ></td>
	</tr>
		<tr>
		<td>報名截止日:</td>
		<td><input name="dual_date" id="f_date2" type="text" ></td>
	</tr>
	<tr>
		<td>出團日:</td>
		<td><input name="action_date" id="f_date3" type="text" ></td>
	</tr>
	<tr>
		<td>開團狀態:</td>
		<td>
		<select size="1" name="act_state" >
				<option value= "發團中" ${( act_list_vo.act_state=="發團中") ? 'selected':'' }>發團中</option>
				<option value= "已開團" ${( act_list_vo.act_state=="已開團") ? 'selected':'' }>已開團</option>
				<option value= "檢舉" ${( act_list_vo.act_state=="檢舉") ? 'selected':'' }>檢舉</option>
		</select>
		</td>
	</tr>
	<tr>
		<td>揪團地點:</td>
		<td><input type="TEXT" id="address" class=twaddress name="locale" size="45" value="<%=act_list_vo.getLocale()%>" /></td>
	</tr>
	<tr>
		<td>參加人上限:</td>
		<td><input type="TEXT" name="act_max" size="45" value="<%=act_list_vo.getAct_max()%>" /></td>
	</tr>
	<tr>
		<td>參加人下限:</td>
		<td><input type="TEXT" name="act_min" size="45" value="<%=act_list_vo.getAct_min()%>" /></td>
	</tr>
	<tr>	
		<td>揪團圖片:</td>
		<td>
		 	<input type="file" id="file" name="gp_pic" value="<%= act_list_vo.getGp_pic()%>" />
       		 <div id="image" > 
           	  <img width="30%" height="30%"  src="<%=request.getContextPath()%>/front-end/act_list/DBGifReader?act_list_no=${act_list_vo.act_list_no}"/>
        	 </div>
		</td>
	</tr>
	<tr>
		<td>揪團介紹:</td>
		<td>
		<input type="TEXT" name="gp_info" size="45" value="<%=act_list_vo.getGp_info()%>" />
		</td>
	</tr>
	<tr>
		<td>天數:</td>
		<td><input type="TEXT" name="gp_days" size="45" value="<%=act_list_vo.getGp_days()%>" /></td>
	</tr>				

</table>

<br>
<!-- <input type="hidden" name="action" value="Group_management"> -->
<input type="hidden" name="action" value="update">
<input type="hidden" name="act_list_no" value="<%=act_list_vo.getAct_list_no()%>">
<input type="hidden" name="mem_no" value="<%=act_list_vo.getMem_no()%>">
<input type="submit" class="btn btn-info " value="送出修改">

</FORM>

</div>
</div>
</div>
<!-- ----------------------------------------------------------------------- -->
			<div class="row" style="height:10%;background:rgba(0,0,0,0);">
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
<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
<%@ include file="address.file" %>
$.datetimepicker.setLocale('zh');
$('#f_date1').datetimepicker({
   theme: '',              //theme: 'dark',
   timepicker:false,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d',         //format:'Y-m-d H:i:s',
   value: '<%=act_list_vo.getDual_date()%>', // value:   new Date(),
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:               '-1970-01-01', // 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});

$.datetimepicker.setLocale('zh');
$('#f_date2').datetimepicker({
   theme: '',              //theme: 'dark',
   timepicker:false,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d',         //format:'Y-m-d H:i:s',
   value: '<%=act_list_vo.getAction_date()%>', // value:   new Date(),
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:               '-1970-01-01', // 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});

$.datetimepicker.setLocale('zh');
$('#f_date3').datetimepicker({
   theme: '',              //theme: 'dark',
   timepicker:false,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d',         //format:'Y-m-d H:i:s',
   value: '<%=act_list_vo.getStart_date()%>', // value:   new Date(),
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:               '-1970-01-01', // 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>
</html>
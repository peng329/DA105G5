<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*" %>
<%@ page import="com.diveshop.model.*" %>
<%
	LessonVO lessonVO = (LessonVO) request.getAttribute("lessonVO");
%>
<!-- ------------------------------------標頭------------------------------------------------------- -->
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>吐泡泡-潛店後台管理系統</title>
    <meta name="description" content="Ela Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">
    <link rel="shortcut icon" href="https://i.imgur.com/QRAUqs9.png">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/normalize.css@8.0.0/normalize.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lykmapipo/themify-icons@0.1.2/css/themify-icons.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pixeden-stroke-7-icon@1.2.3/pe-icon-7-stroke/dist/pe-icon-7-stroke.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.2.0/css/flag-icon.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/diveshop-master/assets/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/diveshop-master/assets/css/style.css">
    <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->
    <link href="https://cdn.jsdelivr.net/npm/chartist@0.11.0/dist/chartist.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/jqvmap@1.5.1/dist/jqvmap.min.css" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/weathericons@2.1.0/css/weather-icons.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@3.9.0/dist/fullcalendar.min.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
   <style>
    #weatherWidget .currentDesc {
        color: #ffffff!important;
    }
        .traffic-chart {
            min-height: 335px;
        }
        #flotPie1  {
            height: 150px;
        }
        #flotPie1 td {
            padding:3px;
        }
        #flotPie1 table {
            top: 20px!important;
            right: -10px!important;
        }
        .chart-container {
            display: table;
            min-width: 270px ;
            text-align: left;
            padding-top: 10px;
            padding-bottom: 10px;
        }
        #flotLine5  {
             height: 105px;
        }

        #flotBarChart {
            height: 150px;
        }
        #cellPaiChart{
            height: 160px;
        }
		.right-panel .navbar-brand img {
    		max-width: 35px;
		}
		
		.card {
			width:30%;
		}
		#right-panel{
		background-color:white;
		}

    </style>
</head>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<body >

<div class="content">
<!-- --------------------------------右邊欄內容----------------------------------- -->
	<h3 class="pb-3">資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lesson/lesson.do" enctype="multipart/form-data" class="form-horizontal">
		
		<div class="row form-group">
			<div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店編號:</label></div>
			<div class="col-12 col-md-9">${diveshopVO.ds_no }</div>
			<input type="hidden" name="ds_no" size="35" value="${diveshopVO.ds_no}"/>
		</div>
		<div class="row form-group">
			<div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店名稱:</label></div>
			<div class="col-12 col-md-9">${diveshopVO.ds_name }</div>
			<input type="hidden" name="ds_name" size="35" value="${diveshopVO.ds_name}"/>
		</div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程名稱:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_name" name="les_name" placeholder="請輸入課程名稱" value="<%=(lessonVO == null) ? "" : lessonVO.getLes_name()%>" class="form-control"></div>
        </div>
		<div class="row form-group">
            <div class="col col-md-3"><label for="textarea-input" class=" form-control-label">課程資訊:</label></div>
            <div class="col-12 col-md-9"><textarea name="les_info" id="les_info" rows="9" placeholder="Content..." class="form-control"><%=(lessonVO == null) ? "" : lessonVO.getLes_info()%></textarea></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">教練名稱:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="coach" name="coach" placeholder="請輸入教練名稱" value="<%=(lessonVO == null) ? "" : lessonVO.getCoach()%>" class="form-control"></div>
        </div>
		<div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">上課費用:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="cost" name="cost" placeholder="請輸入課程費用" value="<%=(lessonVO == null) ? "" : lessonVO.getCost()%>" class="form-control"></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程開始報名日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="signup_startdate" name="signup_startdate" placeholder="請選擇日期" class="form-control"></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程結束報名日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="signup_enddate" name="signup_enddate" placeholder="請選擇日期" class="form-control"></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程開始日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_startdate" name="les_startdate" placeholder="請選擇日期" class="form-control"></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程結束日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_enddate" name="les_enddate" placeholder="請選擇日期" class="form-control"></div>
        </div>
<!--         <div class="row form-group"> -->
<!--             <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程天數:</label></div> -->
            <div class="col-12 col-md-9"><input type="hidden" id="days" name="days" placeholder="請輸入課程天數" value="<%=(lessonVO == null) ? "" : lessonVO.getDays()%>" class="form-control"></div>
<!--         </div> -->
        <div class="row form-group">
            <div class="col col-md-3"><label class=" form-control-label">課程報名狀態:</label></div>
                 <div class="col col-md-9">
                      <div class="form-check-inline form-check">
                      <label for="les_state1" class="form-check-label ">
                          <input type="radio" id="les_state1" name="les_state" value="開放報名" class="form-check-input">開放報名
                      </label>
                      <label for="les_state2" class="form-check-label ">
                          <input type="radio" id="les_state2" name="les_state" value="結束報名" class="form-check-input">結束報名
                      </label>
                 	  </div>
            	</div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程報名人數上限:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_max" name="les_max" placeholder="請輸入人數上限" value="<%=(lessonVO == null) ? "" : lessonVO.getLes_max()%>" class="form-control"></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程報名人數下限:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_limit" name="les_limit" placeholder="請輸入人數下限" value="<%=(lessonVO == null) ? "" : lessonVO.getLes_max()%>" class="form-control"></div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label class=" form-control-label">課程上架狀態:</label></div>
                 <div class="col col-md-9">
                      <div class="form-check-inline form-check">
                      <label for="less_state1" class="form-check-label ">
                          <input type="radio" id="less_state1" name="less_state" value="上架" class="form-check-input">上架
                      </label>
                      <label for="less_state2" class="form-check-label ">
                          <input type="radio" id="less_state2" name="less_state" value="下架" class="form-check-input">下架
                      </label>
                 	  </div>
            	</div>
        </div>
        <div class="row form-group">
            <div class="col col-md-3"><label for="file-multiple-input" class=" form-control-label">課程圖片:</label></div>
            <div class="col-12 col-md-9">
            <div class="imgBox"></div>
            <input type="file" id="file-multiple-input" name="addPics" multiple class="uploadBox form-control-file"></div>
        </div>	
        <div class="form-actions form-group">
        	<input type="hidden" name="action" value="insert"> 
          	<button type="submit" class="btn btn-primary btn-sm">確定新增</button>
        </div>
	</FORM>
		<button type="submit" class="btn btn-primary btn-sm" id="magic">小按鈕</button>
		 </div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	$("#magic").click(function(){
		$("#les_name").val("無氣瓶潛水")
		$("#les_info").val("如何無氣瓶潛水")
		$("#coach").val("035321654")
		$("#address").val("台中市霧峰區光明路27巷28號")
		$("#ds_latlng").val("Aquaman")
		$("#cost").val("12000")
		$("#days").val("0")
		$("#signup_startdate").val("2020-03-10")
		$("#signup_enddate").val("2020-03-20")
		$("#dsinfo").val("優質潛水中心")
		$("#les_max").val("2")
		$("#les_limit").val("1")
		$("#les_startdate").val("2020-03-22")
		$("#les_enddate").val("2020-03-25")
		$("#les_state1").attr("checked",true)
		$("#less_state1").attr("checked",true)
	});
});
</script>

<% 
  java.sql.Date signup_startdate = null;
  try {
	  signup_startdate = lessonVO.getSignup_startdate();
   } catch (Exception e) {
	   signup_startdate = new java.sql.Date(System.currentTimeMillis());
   }
  
  java.sql.Date signup_enddate = null;
  try {
	  signup_enddate = lessonVO.getSignup_enddate();
   } catch (Exception e) {
	   signup_enddate = new java.sql.Date(System.currentTimeMillis());
   }
  
  java.sql.Date les_startdate = null;
  try {
	   les_startdate = lessonVO.getLes_startdate();
   } catch (Exception e) {
	   les_startdate = new java.sql.Date(System.currentTimeMillis());
   }
  
  java.sql.Date les_enddate = null;
  try {
	  les_enddate = lessonVO.getLes_enddate();
   } catch (Exception e) {
	   les_enddate = new java.sql.Date(System.currentTimeMillis());
   }
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>


<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  500px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
		$(".uploadBox").change(function(e) {
			var url = null;
			var img = null;
			var length = e.target.files.length; //上傳幾個檔案
			for (let i = 0; i < length; i++) {
				url = URL.createObjectURL(e.target.files[i]);
				img = $("<img>").attr("src", url).css("width","150px");
				$(".imgBox").append(img);
			}
		});
		
        $.datetimepicker.setLocale('zh');
        $(function(){
       	 $('#signup_startdate').datetimepicker({
       	  format:'Y-m-d',
       	  onShow:function(){
       	   this.setOptions({
       		minDate:               '-1970-01-01'
       	   })
       	  },
       	  timepicker:false
       	 });
       	 
       	 $('#signup_enddate').datetimepicker({
       	  format:'Y-m-d',
       	  onShow:function(){
       	   this.setOptions({
       	    minDate:$('#signup_startdate').val()?$('#signup_startdate').val():false
       	   })
       	  },
       	  timepicker:false
       	 });
       });
        
        $(function(){
          	 $('#les_startdate').datetimepicker({
          	  format:'Y-m-d',
          	  onShow:function(){
          	   this.setOptions({
          	    minDate:$('#signup_enddate').val()?$('#signup_enddate').val():false
          	   })
          	  },
          	  timepicker:false
          	 });
          	 
          	 $('#les_enddate').datetimepicker({
          	  format:'Y-m-d',
          	  onShow:function(){
          	   this.setOptions({
          	    minDate:$('#les_startdate').val()?$('#les_startdate').val():false
          	   })
          	  },
          	  timepicker:false
          	 });
          });

       
    </script>

</html>
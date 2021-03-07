<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*" %>
<%@ page import="com.diveshop.model.*" %>

<%
	LessonVO lessonVO = (LessonVO) request.getAttribute("lessonVO");
	session = request.getSession();
// 	DiveshopVO diveshopVO = (DiveshopVO) session.getAttribute("diveshopVO");
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
		
    </style>
</head>
<body >
<!-- Left Panel -->
<div id="left-panel" class="left-panel">
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
</div>

	<!-- Right Panel -->
    <div id="right-panel" class="right-panel">
        <!-- Header-->
        <header id="header" class="header">
            <div class="top-left">
                <div class="navbar-header">
                    <a class="navbar-brand" href="<%=request.getContextPath()%>/back-end/diveshop-master/diveshop-master.jsp"><img src="<%=request.getContextPath()%>/back-end/diveshop-master/images/Logo.jpg" alt="Logo" ></a>
                    <a class="navbar-brand hidden" href="./"><img src="/DA105G5/back-end/diveshop-master/images/Logo.jpg" alt="Logo" ></a>
                    <a id="menuToggle" class="menutoggle"><i class="fa fa-bars"></i></a>
                </div>
            </div>
            <div class="top-right">
                <div class="header-menu">
                    <div class="header-left">
                        <button class="search-trigger"><i class="fa fa-search"></i></button>
                        <div class="form-inline">
                            <form class="search-form">
                                <input class="form-control mr-sm-2" type="text" placeholder="Search ..." aria-label="Search">
                                <button class="search-close" type="submit"><i class="fa fa-close"></i></button>
                            </form>
                        </div>
                    </div>

                    <div class="user-area dropdown float-right">
                        <a href="#" class="dropdown-toggle active" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img class="user-avatar rounded" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?ds_no=${diveshopVO.ds_no}&index=0" alt="User Avatar">
                        </a>

                        <div class="user-menu dropdown-menu">
                            <a class="nav-link" href="#"><i class="fa fa- user"></i>My Profile</a>

                            <a class="nav-link" href="#"><i class="fa fa -cog"></i>Settings</a>

                            <a class="nav-link" href="#"><i class="fa fa-power -off"></i>Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <!-- /#header -->
         <div class="content">
<!-- --------------------------------右邊欄內容----------------------------------- -->
	<h3 class="pb-5">課程資料修改:</h3>
	<jsp:useBean id="lespicSvc" scope="page" class="com.lespic.model.LespicService"></jsp:useBean>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	
	<FORM METHOD="post" class="form-horizontal" ACTION="<%=request.getContextPath()%>/lesson/lesson.do"
		 enctype="multipart/form-data">
		
		<div class="row form-group">
			<div class="col col-md-3"><label for="text-input" class=" form-control-label">課程編號:</label></div>
			<div class="col-12 col-md-9">${lessonVO.les_no}</div>
			<input type="hidden" name="les_no" size="35" value="${lessonVO.les_no}"/>
		</div>
		
		<div class="row form-group">
			<div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店編號:</label></div>
			<div class="col-12 col-md-9">${lessonVO.ds_no}</div>
			<input type="hidden" name="ds_no" size="35" value="${lessonVO.ds_no}"/>
		</div>
		
		<div class="row form-group">
			<div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店名稱:</label></div>
			<div class="col-12 col-md-9">${lessonVO.ds_name}</div>
			<input type="hidden" name="ds_name" size="35" value="${lessonVO.ds_name}"/>
		</div>
		
		<div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程名稱:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_name" name="les_name" placeholder="請輸入文字" value="${lessonVO.les_name}" class="form-control"></div>
        </div>
		
		<div class="row form-group">
            <div class="col col-md-3"><label for="textarea-input" class=" form-control-label">課程資訊:</label></div>
            <div class="col-12 col-md-9"><textarea name="les_info" id="les_info" rows="9" placeholder="Content..." class="form-control">${lessonVO.les_info}</textarea></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">教練名稱:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="coach" name="coach" placeholder="請輸入文字" value="${lessonVO.coach}" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">上課費用:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="cost" name="cost" placeholder="請輸入文字" value="${lessonVO.cost}" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程開始報名日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="signup_startdate" name="signup_startdate" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程結束報名日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="signup_enddate" name="signup_enddate" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程天數:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="days" name="days" placeholder="請輸入文字" value="${lessonVO.days}" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label class=" form-control-label">報名狀態</label></div>
               <div class="col col-md-9">
             	 <div class="form-check-inline form-check">
                    <label for="les_state1" class="form-check-label ">
                        <input type="radio" id="les_state1" name="les_state" value="開放報名" ${(lessonVO.les_state=="開放報名")? "checked":"" } class="form-check-input">開放報名
                    </label>
                    <label for="les_state2" class="form-check-label ">
                        <input type="radio" id="les_state2" name="les_state" value="結束報名" ${(lessonVO.les_state=="結束報名")? "checked":"" } class="form-check-input">結束報名
                    </label>
                 </div>
              </div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程報名人數上限:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_max" name="les_max" placeholder="0" value="${lessonVO.les_max}" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程報名人數下限:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_limit" name="les_limit" placeholder="0" value="${lessonVO.les_limit}" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程開始日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_startdate" name="les_startdate" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">課程結束日期:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="les_enddate" name="les_enddate" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label class=" form-control-label">課程上架狀態:</label></div>
               <div class="col col-md-9">
             	 <div class="form-check-inline form-check">
                    <label for="less_state1" class="form-check-label ">
                        <input type="radio" id="less_state1" name="less_state" value="上架"  ${(lessonVO.less_state=="上架")? "checked":""} class="form-check-input">上架
                    </label>
                    <label for="less_state2" class="form-check-label ">
                        <input type="radio" id="less_state2" name="less_state" value="下架"  ${(lessonVO.less_state=="下架")? "checked":""} class="form-check-input">下架
                    </label>
                 </div>
              </div>
        </div>
        
        <div class="row form-group">
				<div class="col col-md-3"><label for="text-input" class=" form-control-label">課程照片:</label></div>
					<div class="col-12 col-md-9">
						<div class="row">
							<c:forEach var="lespicVO" items="${lespicSvc.getLespicByLes_no(lessonVO.les_no)}">
								<div class="col-md-6">
									<input type="checkbox" name="photo_update"  value="${lespicVO.lpic_seq}" >修改<br>
									<input type="checkbox" name="photo_delete"  value="${lespicVO.lpic_seq}" >刪除<br>
									<img class="dspic" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?lpic_seq=${lespicVO.lpic_seq}" style="width:150px;
										height:150px;"/><br>
									<input class="showDspic" type="file" name="upfile" onchange="loadFile(event)"><br>
								</div>
							</c:forEach>
						</div>
					</div>
			</div>
			
		<div class="row form-group">
            <div class="col col-md-3"><label for="file-multiple-input" class=" form-control-label">上傳圖片:</label></div>
            <div class="col-12 col-md-9">
            <div class="imgBox"></div>
            <input type="file" id="file-multiple-input" name="addPics" multiple class="uploadBox form-control-file"></div>
        </div>	
		
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="les_no" value="<%=lessonVO.getLes_no()%>">
		<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
		<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  
		<input type="submit" value="送出修改">
	</FORM>

		 </div>
		 <div class="clearfix"></div> <!-- Footer --> 
			
		<footer	class="navbar-fixed-bottom text-center">
				<div class="container" id='fot'>
					<p>
						copyright 2020 BUBBLE UP<br> 
						Take a deep breath<br>
						Contact us : <br>
					</p>
				</div>
			</footer> <!-- /.site-footer -->
		</div>
	<!-- Scripts -->
<!--     <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script> -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/main.js"></script>

    <!--  Chart js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.7.3/dist/Chart.bundle.min.js"></script>

    <!--Chartist Chart-->
    <script src="https://cdn.jsdelivr.net/npm/chartist@0.11.0/dist/chartist.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartist-plugin-legend@0.6.2/chartist-plugin-legend.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/jquery.flot@0.8.3/jquery.flot.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flot-pie@1.0.0/src/jquery.flot.pie.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flot-spline@0.0.1/js/jquery.flot.spline.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/simpleweather@3.1.0/jquery.simpleWeather.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/init/weather-init.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/moment@2.22.2/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@3.9.0/dist/fullcalendar.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/init/fullcalendar-init.js"></script>
</body>



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
$(".showDspic").change(function(e) {
	var url = URL.createObjectURL(e.target.files[0]);
	var img = $("<img>").attr("src", url);
	$(this).parent().append(img);
});
	
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
       	  value:'${lessonVO.signup_startdate}',
       	  onShow:function(){
       	   this.setOptions({
       		minDate:'-1970-01-01'
       	   })
       	  },
       	  timepicker:false
       	 });
       	 
       	 $('#signup_enddate').datetimepicker({
       	  format:'Y-m-d',
       	  value:'${lessonVO.signup_enddate}',
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
          	  value:'${lessonVO.les_startdate}',
          	  onShow:function(){
          	   this.setOptions({
          	    minDate:$('#signup_enddate').val()?$('#signup_enddate').val():false
          	   })
          	  },
          	  timepicker:false
          	 });
          	 
          	 $('#les_enddate').datetimepicker({
          	  format:'Y-m-d',
          	  value:'${lessonVO.les_enddate}',
          	  onShow:function(){
          	   this.setOptions({
          	    minDate:$('#les_startdate').val()?$('#les_startdate').val():false
          	   })
          	  },
          	  timepicker:false
          	 });
          });

       
    </script>
 <%
  java.sql.Date signup_startdate = null;
  try {
	  
	  signup_startdate = lessonVO.getSignup_startdate();
	  System.out.print(signup_startdate);
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

</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>
<%@ page import="java.util.*"%>
<%
	DiveshopVO diveshopVO = (DiveshopVO) request.getAttribute("diveshopVO");
%>
<jsp:useBean id="diveshopSvc" scope="page" class="com.diveshop.model.DiveshopService"></jsp:useBean>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Ela Admin - HTML5 Admin Template</title>
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
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCvS_7XU7xlsw0cWtSRBGBe6dPblebLkHM&callback=initMap" async defer>
	</script>
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
		#map {
			height: 400px;
			width: 400px;
			margin: 0;
			padding: 0;
		}
		
 		
    </style>
</head>

<body onload="initMap()">
    <!-- Left Panel -->
    <aside id="left-panel" class="left-panel">
        <nav class="navbar navbar-expand-sm navbar-default">
            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <ul class="nav navbar-nav">
                	 <li class="active">
                        <a href="<%=request.getContextPath()%>/back-end/diveshop-master/diveshop-master.jsp"><i class="menu-icon fa fa-laptop"></i>潛店基本資料管理 </a>
                    </li>
                    <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>課程管理</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i>
                            <a href="<%=request.getContextPath()%>/lesson/lesson.do?action=findByDsname&ds_no=${diveshopVO.ds_no}&requestURL=<%=request.getServletPath()%>">潛店課程</a></li>
                            <li><i class="menu-icon fa fa-th"></i>
                            	<a href="<%=request.getContextPath()%>/back-end/lesson/addLesson.jsp">新增課程</a></li>
                       		<li><i class="menu-icon fa fa-th"></i>
                            	<a href="<%=request.getContextPath()%>/lessonorder/lessonorder.do?action=findorder_By_Ds_no&ds_no=${diveshopVO.ds_no}&requestURL=<%=request.getServletPath()%>">課程訂單</a></li>
                        </ul>
                    </li>
                    <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>裝備管理</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i>
                            	<a href="<%=request.getContextPath()%>">新增裝備</a></li>
                            <li><i class="menu-icon fa fa-th"></i>
                            <a href="<%=request.getContextPath()%>">變更裝備</a></li>
                            <li><i class="menu-icon fa fa-th"></i>
                            <a href="<%=request.getContextPath()%>">查詢裝備訂單</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </nav>
    </aside>
    <!-- /#left-panel -->
    <!-- Right Panel -->
    <div id="right-panel" class="right-panel">
        <!-- Header-->
        <header id="header" class="header">
            <div class="top-left">
                <div class="navbar-header">
                    <a class="navbar-brand" href="<%=request.getContextPath()%>/back-end/diveshop-master/diveshop-master.jsp">
                    <img src="<%=request.getContextPath()%>/back-end/diveshop-master/images/Logo.jpg" alt="Logo" ></a>
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

                            <a class="nav-link" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=dsLogout"><i class="fa fa-power -off"></i>Logout</a>
                        </div>
                    </div>

                </div>
            </div>
        </header>
        <!-- /#header -->
        <!-- Content -->
        <div class="content">
<!-- 	----------------------------------------------------主要內容------------------------------------------------------------------- -->
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<jsp:useBean id="dspicSvc" scope="page"	class="com.dspic.model.DspicService"></jsp:useBean>
	
	<FORM class="form-horizontal" METHOD="post"	ACTION="<%=request.getContextPath()%>/diveshop/diveshop.do"  enctype="multipart/form-data">
						
		<div class="row form-group">
			<div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店編號:</label></div>
			<div class="col-12 col-md-9"><%=diveshopVO.getDs_no()%></div>
			<input type="hidden" name="ds_no" size="35" value="<%=diveshopVO.getDs_no()%>"/>
		</div>
		
		<div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店名稱:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="ds_name" name="ds_name" placeholder="請輸入文字" value="<%=diveshopVO.getDs_name()%>" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">營業登記證:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="brcid" name="brcid" placeholder="請輸入營業登記證" value="<%=diveshopVO.getBrcid()%>" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店電話:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="tel" name="tel" placeholder="請輸入電話" value="<%=diveshopVO.getTel()%>" class="form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">地址</label></div>
            <div class="col-12 col-md-9">
            <input type="text" id="address" name="address" placeholder="請輸入地址" value="<%=(diveshopVO == null) ? "桃園市中壢區中央路300號" : diveshopVO.getAddress()%>" 
            	class="twaddress form-control"></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">地圖</label></div>
        	<div id="map" ></div>
       		<div  style="position: relative;"><button type="button" class="btn btn-primary" id="submit" style="position: absolute; top: 90%;">取得緯經度</button></div>
        </div>
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店緯經度:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="ds_latlng" name="ds_latlng" placeholder="點擊按鈕取得緯精度" value="<%=diveshopVO.getDs_latlng()%>" class="form-control"></div>
        </div>
		
		<div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店帳號:</label></div>
            <div class="col-12 col-md-9"><input type="text" id="dsaccount" name="dsaccount" placeholder="請輸入帳號" value="<%=diveshopVO.getDsaccount()%>" class="form-control"></div>
        </div>
			
		<div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店密碼:</label></div>
            <div class="col-12 col-md-9"><input type="password" id="dspaw" name="dspaw" placeholder="請輸入密碼" value="<%=diveshopVO.getDspaw()%>" class="form-control"></div>
        </div>	
			
		<div class="row form-group">
            <div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店信箱:</label></div>
            <div class="col-12 col-md-9"><input type="email" id="dsmail" name="dsmail" placeholder="請輸入信箱" value="<%=diveshopVO.getDsmail()%>" class="form-control"></div>
        </div>	
        
        <div class="row form-group">
            <div class="col col-md-3"><label for="textarea-input" class=" form-control-label">潛店介紹:</label></div>
            <div class="col-12 col-md-9"><textarea name="dsinfo" id="dsinfo" rows="9" placeholder="Content..." class="form-control"><%=diveshopVO.getDsinfo()%></textarea></div>
        </div>
        
			<div class="row form-group">
				<div class="col col-md-3"><label for="text-input" class=" form-control-label">潛店照片:</label></div>
					<div class="col-12 col-md-9">
						<div class="row">
							<c:forEach var="dspicVO" items="${dspicSvc.getDspicByDsno(diveshopVO.ds_no)}">
								<div class="col-md-6">
									<input type="checkbox" name="photo_update" value="${dspicVO.dpic_seq}" >修改<br>
									<input type="checkbox" name="photo_delete" value="${dspicVO.dpic_seq}" >刪除<br>
									<img class="dspic" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?dpic_seq=${dspicVO.dpic_seq}" style="width:150px;
										height:150px;"/><br>
									<input class="showDspic" type="file" name="upfile" onchange="loadFile(event)"><br>
								</div>
							</c:forEach>
						</div>
					</div>
			</div>
		
         	
<!-- 		<div class="row form-group"> -->
<!-- 			<div class="col col-md-3"><label class="form-control-label">潛店狀態</label></div> -->
<%-- 			<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_state}</p></div> --%>
			<input type="hidden" name="ds_state" size="35" value="<%=diveshopVO.getDs_state()%>"/>
<!-- 		</div> -->

<!-- 		<div class="row form-group"> -->
<!-- 			<div class="col col-md-3"><label class="form-control-label">潛店評分</label></div> -->
<%-- 			<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_ascore}</p></div> --%>
			<input type="hidden" name="ds_ascore" size="35" value="<%=diveshopVO.getDs_ascore()%>"/>
<!-- 		</div> -->
		
<!--  		<div class="row form-group"> -->
<!-- 			<div class="col col-md-3"><label class="form-control-label">潛店檢舉次數</label></div> -->
<%-- 			<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_rep_no}</p></div> --%>
			<input type="hidden" name="ds_rep_no" size="35" value="<%=diveshopVO.getDs_rep_no()%>"/>
<!-- 		</div> -->
			
		<div class="row form-group">
            <div class="col col-md-3"><label for="file-multiple-input" class=" form-control-label">上傳圖片:</label></div>
            <div class="col-12 col-md-9">
            <div class="imgBox"></div>
            <input type="file" id="file-multiple-input" name="addPics" multiple class="uploadBox form-control-file"></div>
        </div>	
		
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="ds_no" value="<%=diveshopVO.getDs_no()%>">
		<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
		<input type="submit" value="送出修改">
	</FORM>
	
   
    <!-- /.content -->
        <div class="clearfix"></div>
        <!-- Footer -->
  <footer class="navbar-fixed-bottom text-center">
  <div class="container" id='fot'>
  <p>
  copyright 2020 BUBBLE UP<br>
  Take a deep breath<br>
  Contact us : <br>
  </p>
  </div>
</footer>
        <!-- /.site-footer -->
    </div>
<script>
			$(".showDspic").change(function(e) {
				var url = URL.createObjectURL(e.target.files[0]);
				var img = $("<img>").attr("src", url);
				$(this).parent().append(img);
			});
			$(".uploadBox.form-control-file").change(function(e) {
				var url = null;
				var img = null;
				var length = e.target.files.length; //上傳幾個檔案
				for (let i = 0; i < length; i++) {
					url = URL.createObjectURL(e.target.files[i]);
					img = $("<img>").attr("src", url).css("width","150px");
					$(".imgBox").append(img);
				}
			});

		<%@ include file="/back-end/diveshop/pages/address.file" %>
			
		</script>
 <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
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
<script>
			function initMap() {
				var map = new google.maps.Map(document.getElementById('map'), {
					zoom : 15,
					center : {
						lat : 24.967841,
						lng : 121.191830
					}
				});
				var geocoder = new google.maps.Geocoder();
				
				document.getElementById('submit').addEventListener('click',
						function() {
							geocodeAddress(geocoder, map);
						});
			}
			
			function geocodeAddress(geocoder, resultsMap) {	
				var ds_latlng = document.getElementById('ds_latlng');
				var ds_name = document.getElementById('ds_name').value;
		        var address = document.getElementById('address').value;
		        var marker = null;
		        var infowindow = new google.maps.InfoWindow({
		            content: '<h5>' + ds_name + '</h5>'
		          });
		        geocoder.geocode({'address': address}, function(results, status) {
		          if (status === 'OK') {
		            resultsMap.setCenter(results[0].geometry.location);
		            marker = new google.maps.Marker({
		              map: resultsMap,
		              position: results[0].geometry.location,
		              title:ds_name
		            });
		            ds_latlng.value =results[0].geometry.location;
		            var a = -1;
		            marker.addListener('click',function(){
		            	a = a * -1;
		                if(a > 0){
		                  infowindow.open(map, marker);
		                }else{
		                  infowindow.close();
		                }
		              });
		            marker.addListener('click',function(){
		                if(marker.getAnimation()==null){
		                  marker.setAnimation(google.maps.Animation.BOUNCE);
		                }else{
		                  marker.setAnimation(null);
		                }
		              });
		          } else {
		            alert('Geocode was not successful for the following reason: ' + status);
		          }
		        });
		      }
    </script>
</body>
</html>
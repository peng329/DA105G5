<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.diveshop.model.*,com.dspic.model.*"%>

<jsp:useBean id="diveshopSvc" scope="page" class="com.diveshop.model.DiveshopService"></jsp:useBean>

<%
	session = request.getSession();
	DiveshopVO diveshopVO = (DiveshopVO) session.getAttribute("diveshopVO");
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
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCvS_7XU7xlsw0cWtSRBGBe6dPblebLkHM&callback=initMap" async defer>
	</script>
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
                        <a href="<%=request.getContextPath()%>/back-end/diveshop-master/diveshop-master.jsp">
                        <i class="menu-icon fa fa-laptop"></i>潛店基本資料管理 </a>
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
                            	<a href="<%=request.getContextPath()%>/back-end/equip/addEquipByBUP.jsp?ds_no=${diveshopVO.ds_no}">新增裝備</a></li>
                            <li><i class="menu-icon fa fa-th"></i>
                            <a href="<%=request.getContextPath()%>/back-end/equip/listAllDSEquipByDs_no.jsp?ds_no=${diveshopVO.ds_no}">變更裝備</a></li>
                            <li><i class="menu-icon fa fa-th"></i>
                            <a href="<%=request.getContextPath()%>/back-end/rorder/listADSAllRO.jsp?ds_no=${diveshopVO.ds_no}">裝備訂單管理</a></li>
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

                            <a class="nav-link" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=dsLogout"><i class="fa fa-power -off"></i>Logout</a>
                        </div>
                    </div>

                </div>
            </div>
        </header>
        <!-- /#header -->
        <!-- Content -->
        <div class="content">
<!--         -------------------------------------------網頁內容------------------------------------------------------------------- -->
<%-- ${diveshopVO.ds_name}潛店後台首頁 --%>

<form class="form-horizontal" METHOD="post" action="<%=request.getContextPath()%>/diveshop/diveshop.do">
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店編號</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_no}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店名稱</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_name}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">營業登記證</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.brcid}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店電話</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.tel}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店地址</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.address}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店帳號</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.dsaccount}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店密碼</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static"></p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店信箱</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.dsmail}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店介紹</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.dsinfo}</p></div>
	</div>
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店緯經度</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_latlng}</p></div>
	</div>
<!-- 	<div class="row form-group"> -->
<!-- 		<div class="col col-md-3"><label class="form-control-label">潛店狀態</label></div> -->
<%-- 		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_state}</p></div> --%>
<!-- 	</div> -->
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label">潛店照片</label></div>
		<div class="col-12 col-md-9"><p class="form-control-static">
			<c:forEach var="dpic_seq" items="${diveshopSvc.getDpic_seqByDs_no(diveshopVO.ds_no)}" >
					<img class="dpic" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?dpic_seq=${dpic_seq}" style="width:150px;"/>				
			</c:forEach>		
		</p></div>
	</div>
<!-- 	<div class="row form-group"> -->
<!-- 		<div class="col col-md-3"><label class="form-control-label">潛店評分</label></div> -->
<%-- 		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_ascore}</p></div> --%>
<!-- 	</div> -->
<!--  	<div class="row form-group"> -->
<!-- 		<div class="col col-md-3"><label class="form-control-label">潛店檢舉次數</label></div> -->
<%-- 		<div class="col-12 col-md-9"><p class="form-control-static">${diveshopVO.ds_rep_no}</p></div> --%>
<!-- 	</div> -->
	
	<div class="row form-group">
		<div class="col col-md-3"><label class="form-control-label"> </label></div>
			<input type="submit" value="修改">
			<input type="hidden" name="ds_no" 		value="${diveshopVO.ds_no}">
			<input type="hidden" name="requestURL"  value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller  -->
			<input type="hidden" name="action" 	  	value="getOne_For_Update">
	</div>
</form>

<!-- <br>本網頁的路徑:<br> -->
<!-- <b> -->
<%-- <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br> --%>
<%-- <font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%> <br> --%>
<!-- </b> -->
            
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
    <!-- /#right-panel -->
	<!--         -------------------------------------------網頁內容------------------------------------------------------------------- -->
    <!-- Scripts -->
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

    <!--Local Stuff-->
   
    </div>
</body>
</html>

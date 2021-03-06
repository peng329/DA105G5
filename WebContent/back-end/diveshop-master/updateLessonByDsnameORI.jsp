<%@page import="java.io.PrintWriter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lesson.model.*"%>
<%@ page import="com.lespic.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>



<jsp:useBean id="dspicSvc" scope="page" class="com.dspic.model.DspicService"></jsp:useBean>
<jsp:useBean id="lespicSvc" scope="page" class="com.lespic.model.LespicService"></jsp:useBean>

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
<!--     <link href="https://cdn.jsdelivr.net/npm/chartist@0.11.0/dist/chartist.min.css" rel="stylesheet"> -->
<!--     <link href="https://cdn.jsdelivr.net/npm/jqvmap@1.5.1/dist/jqvmap.min.css" rel="stylesheet"> -->

<!--     <link href="https://cdn.jsdelivr.net/npm/weathericons@2.1.0/css/weather-icons.css" rel="stylesheet" /> -->
<!--     <link href="https://cdn.jsdelivr.net/npm/fullcalendar@3.9.0/dist/fullcalendar.min.css" rel="stylesheet" /> -->

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
		.card {
			width:30%;
		}
		#right-panel{
		background-color:white;
		}
		.card{
		width:100%;
		font-size:0.875em;
		}
		.card .card-body {
		padding:0.875rem;
		}
		#bootstrap-data-table_length{
		text-align:left;
		}
		#bootstrap-data-table_info{
		text-align:left;
		}
		#bootstrap-data-table_wrapper{
			padding:0%:
		}
    </style>
</head>

<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<body>

<!--     <div id="right-panel" class="right-panel"> -->
<!--   -------------------------------------??????????????????--------------------------------------------------- -->
    <div class="row">
					<%-- ???????????? --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">?????????????????????:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
	</div>				
	<div class="content" style="padding:0.875rem;">
			<div class="animated fadeIn">
				<div class="row">
					<div class="col-md-12" style="padding:0%;">
						<div class="card text-center">
                       		<div class="card-body" style="padding:0%;">
								<table id="bootstrap-data-table" class="table table-striped table-bordered" >
									<thead>
										<tr>
											<th>????????????</th>
											<th>????????????</th>
											<th>????????????</th>
											<th>????????????</th>
											<th>??????</th>
											<th>????????????</th>
											<th>????????????</th>
											<th>????????????</th>
											<th>????????????</th>
											<th>????????????</th>
											<th>??????????????????</th>
											<th>??????????????????</th>
											<th>????????????</th>
											<th>??????</th>
<!-- 											<th>??????</th> -->
										</tr>
									</thead>
									
									<tbody>
										<c:forEach var="lessonVO" items="${list}">
											<tr${(lessonOrderVO.les_o_no==param.les_o_no) ? 'bgcolor=#CCCCFF':'' }>
												<td>${lessonVO.les_no}</td>
												<td>${lessonVO.ds_no}</td>
												<td>${lessonVO.ds_name}</td>
												<td>${lessonVO.les_name}</td>
												<td>${lessonVO.coach}</td>
												<td>${lessonVO.signup_startdate}</td>
												<td>${lessonVO.signup_enddate}</td>
												<td>${lessonVO.les_state}</td>
												<td>${lessonVO.les_max}</td>
												<td>${lessonVO.les_limit}</td>
												<td>${lessonVO.les_startdate}</td>
												<td>${lessonVO.les_enddate}</td>
												<td>${lessonVO.less_state}</td>
												<td>	
													<form METHOD="post" action="<%=request.getContextPath()%>/lesson/lesson.do">
													<button type="submit" class="btn btn-outline-primary btn-sm">??????</button>
													<input type="hidden"  name="days" 		value="${lessonVO.days}"> 
													<input type="hidden"  name="les_no" 		value="${lessonVO.les_no}"> 
													<input type="hidden"  name="requestURL"     value="<%=request.getServletPath()%>"> 
													<input type="hidden"  name="action" 		value="getOne_For_Update">
													</form>
												</td>
<!-- 												<td> -->
<%-- 													<form METHOD="post" class="mb-2" action="<%=request.getContextPath()%>/lesson/lesson.do" --%>
<!-- 													style="margin-bottom: 0px;"> -->
<!-- 													<button type="submit" class="btn btn-outline-primary btn-sm">??????</button> -->
<%-- 													<input type="hidden"  name="les_no"     value="${lessonVO.les_no}"> --%>
<%-- 													<input type="hidden"  name="ds_no"     value="${lessonVO.ds_no}"> --%>
<%-- 													<input type="hidden"  name="ds_name"     value="${lessonVO.ds_name}">    --%>
<%-- 													<input type="hidden"  name="requestURL" value="<%=request.getServletPath()%>">  --%>
<!-- 													<input type="hidden"  name="action"     value="delete"> -->
<!-- 													</form> -->
<!-- 												</td> -->
												</tr>
										</c:forEach>                            
						          </tbody>
	                            </table>
	                       </div>
	                   </div>
	              </div>
	          </div>
	      </div><!-- .animated -->
	</div><!-- .content -->

					<br>??????????????????:<br> <b> <font color=blue>request.getServletPath():</font>
						<%=request.getServletPath()%><br> <font color=blue>request.getRequestURI():
					</font> <%=request.getRequestURI()%><br></b>

   <!--   -------------------------------------??????????????????--------------------------------------------------- -->
<!--     </div> -->
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
     <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/datatables.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/dataTables.bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/dataTables.buttons.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/buttons.bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/jszip.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/vfs_fonts.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/buttons.html5.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/buttons.print.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/lib/data-table/buttons.colVis.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-end/diveshop-master/assets/js/init/datatables-init.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {
          $('#bootstrap-data-table-export').DataTable();
      } );
  </script>
    
    <!-- Scripts -->
   <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
    
   
</body>
</html>

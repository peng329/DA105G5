<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>
<% 


%>
<!-- ------------------------------------標頭------------------------------------------------------- -->
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/diveshop-master/assets/css/lib/datatable/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/diveshop-master/assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
   <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->
	<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<style>
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
</style>
</head>
<body>
    <div class="content" style="padding:0.875rem;">
        <div class="animated fadeIn">
            <div class="row">
               <div class="col-md-12">
                   <div class="card text-center">
                       <div class="card-body">
                           <table id="bootstrap-data-table" class="table table-striped table-bordered">
									<thead>
										<tr>
											<th>課程訂單編號</th>
											<th>課程編號</th>
											<th>潛店編號</th>
											<th>課程名稱</th>
											<th>會員編號</th>
											<th>會員名稱</th>
											<th>教練</th>
											<th>課程費用</th>
											<th>訂單狀態</th>
											<th>修改</th>
											<th>刪除</th>
										</tr>
									</thead>
									
									<tbody>
										<c:forEach var="lessonOrderVO" items="${orderList}">
											<tr${(lessonOrderVO.les_o_no==param.les_o_no) ? 'bgcolor=#CCCCFF':'' }>
												<td>${lessonOrderVO.les_o_no}</td>
												<td>${lessonOrderVO.les_no}</td>
												<td>${lessonOrderVO.ds_no}</td>
												<td>${lessonOrderVO.les_name}</td>
												<td>${lessonOrderVO.mem_no}</td>
												<td>${lessonOrderVO.mem_name}</td>
												<td>${lessonOrderVO.coach}</td>
												<td>${lessonOrderVO.cost}</td>
												<td>${lessonOrderVO.lo_state}</td>
												<td>
													<form METHOD="post"
														action="<%=request.getContextPath()%>/lessonorder/lessonorder.do"
														style="margin-bottom: 0px;">
														<input type="submit" value="修改"> <input
															type="hidden" name="les_o_no"
															value="${lessonOrderVO.les_o_no}"> <input
															type="hidden" name="requestURL"
															value="<%=request.getServletPath()%>">
														<!--送出本網頁的路徑給Controller  -->
														<input type="hidden" name="action"
															value="getOne_For_Update">
													</form>
												</td>
												<td>
													<form METHOD="post"
														action="<%=request.getContextPath()%>/lessonorder/lessonorder.do"
														style="margin-bottom: 0px;">
														<input type="submit" value="刪除"> 
														<input type="hidden" name="ds_no" value="${lessonOrderVO.ds_no}">
														<input type="hidden" name="les_o_no" value="${lessonOrderVO.les_o_no}"> 
														<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
														<input type="hidden" name="action" value="delete">
													</form>
												</td>
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
    <!-- Scripts -->
<!--     <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script> -->
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js"></script>
    <script src="assets/js/main.js"></script>

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
</body>

</html>

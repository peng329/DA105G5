<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.rorder.model.*,com.diveshop.model.*,com.mem.model.*"%>

<%
		String ds_no = request.getParameter("ds_no");
		ROrderService rosvc = new ROrderService();
		MemService memSvc = new MemService();
		List<ROrderVO> list = rosvc.getDsAllRo(ds_no);
		List<MemVO> list2 = memSvc.getAll();
		pageContext.setAttribute("list", list);
		pageContext.setAttribute("list2", list2);
%>

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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/diveshop-master/assets/css/lib/datatable/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/diveshop-master/assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
   <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
<style>
#right-panel{
background-color:white;
}
body{
overflow-x: hidden;
display:block;
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
    <div class="content" style="padding:0.675rem;">
        <div class="animated fadeIn">
            <div class="row">
               <div class="col-md-12">
                   <div class="card text-center">
                       <div class="card-body">
                            <table id="bootstrap-data-table" class="table table-striped table-bordered">
                                <thead>
                                    <tr>
										<th style="padding:0.70rem">訂單編號</th>
										<th style="padding:0.70rem">會員名稱</th>
										<th style="padding:0.65rem">總件數</th>
										<th style="padding:0.65rem">總價格</th>
										<th style="padding:0.70rem">付款狀態</th>
										<th style="padding:0.70rem">租賃開始日</th>
										<th style="padding:0.70rem">租賃結束日</th>
										<th style="padding:0.70rem">實際歸還日</th>
										<th style="padding:0.70rem">歸還狀態</th>
										<th style="padding:0.70rem">罰鍰</th>
										<th style="padding:0.70rem">訂單備註</th>
										<th style="padding:0.70rem">修改</th>
										<th style="padding:0.70rem">明細</th>
                                    </tr>
                                </thead>
								
								<tbody>
									<c:forEach var="rorderVO" items="${list}">
	                                    <tr>
											<td>${rorderVO.ro_no}</td>
										<c:forEach var="memVO" items="${list2}">
											<c:if test="${rorderVO.mem_no.contains(memVO.mem_no)}">
												<td>${memVO.mem_name}</td>
											</c:if>
										</c:forEach>
											<td>${rorderVO.tepc}</td>
											<td>${rorderVO.tpriz}</td>
											<td>${rorderVO.op_state}</td>
											<td>${rorderVO.rs_date}</td>
											<td>${rorderVO.rd_date}</td>
											<td>${rorderVO.rr_date}</td>
											<td>${rorderVO.o_state}</td>
											<td>${rorderVO.ffine}</td>
										
										<c:if test="${!rorderVO.o_note.contains('請輸入備註，文字上限為300字')}">	
											<td>${rorderVO.o_note}</td>
										</c:if>
										<c:if test="${rorderVO.o_note.contains('請輸入備註，文字上限為300字')}">	
											<td></td>
										</c:if>
											
											<td>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ROrder/rorder.do" style="margin-bottom: 0px;">
													<c:choose>												
														<c:when test="${rorderVO.rr_date!=null}">
															 <input type="submit" class="btn btn-outline-info btn-sm" value="修改" disabled="disabled">
														</c:when>												
														<c:otherwise>
															<input type="submit" value="查閱" class="btn btn-outline-info btn-sm"> 
														</c:otherwise>
													</c:choose>
														<input type="hidden" name="ds_no" value="${param.ds_no}"> 
														<input type="hidden" name="ro_no" value="${rorderVO.ro_no}"> 
														<input type="hidden" name="mem_no" value="${rorderVO.mem_no}"> 
														<input type="hidden" name="action" value="getAMem_A_RO">
												</FORM>
											</td>
											<td>
												<FORM name="detail"action="<%=request.getContextPath()%>/back-end/rorder/rodetail.jsp?ro_no=${rorderVO.ro_no}"  method="POST">
													 <input type="submit" class="btn btn-outline-info btn-sm" value="明細" >
												</FORM>
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
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</html>
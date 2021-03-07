<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.dspic.model.*"%>
<%@ page import="com.eqpic.model.*,com.equip.model.*" %>

<%
	EquipVO equipVO = (EquipVO) request.getAttribute("equipVO");
// 	String ep_seq=(String)request.getAttribute("ep_seq");
	String ds_no = request.getParameter("ds_no");
	EquipService equipSvc = new EquipService();
	List<EquipVO> list = equipSvc.getDSAll(ds_no);
	EqpicService eqpicSvc = new EqpicService();
	pageContext.setAttribute("ds_no",ds_no);
// 	pageContext.setAttribute("ep_seq",ep_seq);
	pageContext.setAttribute("list", list);
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
										<th style="padding:0.75rem">裝備分類編號</th>
										<th style="padding:0.75rem">裝備名稱</th>
										<th style="padding:0.75rem">裝備編號</th>
										<th style="padding:0.75rem">裝備價格</th>
										<th style="padding:0.75rem">裝備租金</th>
										<th style="padding:0.75rem">裝備尺寸</th>
										<th style="padding:0.75rem">裝備狀態</th>
										<th style="padding:0.75rem">裝備租賃狀態</th>
										<th style="padding:0.75rem">裝備陳列狀態</th>
										<th style="padding:0.75rem">圖片</th>
										<th style="padding:0.75rem">修改</th>
                                    </tr>
                                </thead>
					    		
					    		<tbody>
									<c:forEach var="equipVO" items="${list}">
										<tr ${(equipVO.ep_seq==param.ep_seq) ? 'bgcolor=#CCEEFF':''}>
											<td>${equipVO.epc_no}</td>
											<td>${equipVO.ep_name}</td>
											<td>${equipVO.ep_no}</td>
											<td>${equipVO.ep_priz}</td>
											<td>${equipVO.ep_rp}</td>
											<td>${equipVO.ep_size}</td>
											<td>${equipVO.ep_state}</td>
											<td>${equipVO.epr_state}</td>
											<td>${equipVO.eps_state}</td>
						
											<td>	
												<FORM METHOD="post"  ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" style="margin:4% 2% 4% 2%;">						
												<input type="submit" class="btn btn-outline-info btn-sm" value="查閱"> 
												<input type="hidden" name="ds_no" value="${equipVO.ds_no}"> 
												<input type="hidden" name="ep_seq" value="${equipVO.ep_seq}"> 
												<input type="hidden" name="ep_name" value="${equipVO.ep_name}"> 
												<input type="hidden" name="action" value="get_EPAL_Display">
												</FORM>	
											</td>
											
											<td>
												<FORM METHOD="POST" ACTION="<%=request.getContextPath()%>/Equip/equip.do" style="margin:4% 2% 4% 2%;">
													<input type="submit" class="btn btn-outline-info btn-sm" value="修改" > 
													<input type="hidden" name="ds_no" value="${equipVO.ds_no}"> 
													<input type="hidden" name="ep_seq" value="${equipVO.ep_seq}"> 
													<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
													<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
													<input type="hidden" name="action" value="getOne_For_Update">
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

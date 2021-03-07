<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mrds_record.model.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>



<%	
WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO");
Authority_manageService amSvc = new Authority_manageService();
List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
pageContext.setAttribute("sessionWmVO",sessionWmVO);
pageContext.setAttribute("fc_noList",fc_noList);

	
    Mrds_recordService mrds_recordSvc = new Mrds_recordService();


    List<Mrds_recordVO> list = mrds_recordSvc.getAll();
    pageContext.setAttribute("list",list);
    
%>


<!DOCTYPE html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Bubble up index</title>
    
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>


<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>
    
<link href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css" rel="stylesheet" type="text/css">  
    
<link href="../../css/style.css" rel="stylesheet" type="text/css">
  </head>
  
<body>
  	<div id="page-container" class="main-admin show-menu">
	<!--------------------------------------------- 上邊與側邊欄位 ------------------------------------------------------>
<%@ include file="/back-end/webmaster/adminSidebar.file"%>

<!-- --------------------------------右邊內容 -------------------------------------->
  	<div class="main-body-content w-100 ets-pt">
  		<div class="table-responsive bg-light">


<div class="container search-table">
            <div class="search-box">
                <div class="row">
                    <div class="col-md-3">
                        <h5>關鍵字搜尋</h5>
                    </div>
                    <div class="col-md-6">
                        <input type="text" id="myInput" onkeyup="myFunction()" class="form-control" placeholder="輸入關鍵字">
                        <script>
                            $(document).ready(function () {
                                $("#myInput").on("keyup", function () {
                                    var value = $(this).val().toLowerCase();
                                    $("#myTable tr").filter(function () {
                                    	
                                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                                    });
                                });
                            });
                        </script>
                    </div> 
                </div>
            </div>


   <!-- Container Fluid-->
   <div class="search-list table-responsive-lg">
    <div class="container-fluid" id="container-wrapper">
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <span class="trip_manage">檢舉潛店</span>
        </div>
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <a class="nav-item nav-link" id="unCheck-tab" data-toggle="tab" href="#unCheck" role="tab" aria-controls="unCheck" aria-selected="true">未審核</a>
                <a class="nav-item nav-link" id="checked-tab" data-toggle="tab" href="#checked" role="tab" aria-controls="checked" aria-selected="false">已審核</a>
        </nav>
        
        <div class="tab-content" id="nav-tabContent">


            <div class="tab-pane fade show active" id="unCheck" role="tabpanel" aria-labelledby="unCheck-tab">
                <div>
                    
                    <!------------------- 文章檢舉未審核表格內容 ------------------------->
				<%@ include file="/back-end/mrds_record/allMrds_record_unCheck.file"%>
				<!------------------------------ / ---------------------------->
            </div>
            </div>
            

            <div class="tab-pane fade" id="checked" role="tabpanel" aria-labelledby="checked-tab">
                <div>
                    <!------------------- 文章檢舉已審核表格內容 ------------------------->
				<%@ include file="/back-end/mrds_record/allMrds_record_checked.file"%>
				<!------------------------------ / ---------------------------->
            </div>
            </div>
        
        </div>
  		</div>
  	</div>
</div>
    <!-- Optional JavaScript -->

    <script type="text/javascript">
    	jQuery(document).ready(function(){
    		jQuery("#open-menu").click(function(){
    			if(jQuery('#page-container').hasClass('show-menu')){
    			jQuery("#page-container").removeClass('show-menu');
    		}
    			
    			else{
    			jQuery("#page-container").addClass('show-menu');
    			}
    		});
    	});
    </script>
  </body>

</html>

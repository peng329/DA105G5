<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mra_record.model.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>

<%
WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO");
Authority_manageService amSvc = new Authority_manageService();
List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
pageContext.setAttribute("sessionWmVO",sessionWmVO);
pageContext.setAttribute("fc_noList",fc_noList);
    

Mra_recordService mra_recordSvc = new Mra_recordService();

List<Mra_recordVO> list = mra_recordSvc.getAll();
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
 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<!--引用jQuery-->
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
<!--引用SweetAlert2.js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>    

 

  
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
            <span class="trip_manage">檢舉文章</span>
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
				<%@ include file="/back-end/mra_record/allMra_record_unCheck.file"%>
				<!------------------------------ / ---------------------------->
            </div>
            </div>
            

            <div class="tab-pane fade" id="checked" role="tabpanel" aria-labelledby="checked-tab">
                <div>
                    <!------------------- 文章檢舉已審核表格內容 ------------------------->
				<%@ include file="/back-end/mra_record/allMra_record_checked.file"%>
				<!------------------------------ / ---------------------------->
            </div>
            </div>
        
        </div>
  		</div>
  	</div>
</div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->

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
    	

//         $(function () {
//             $(".checkBtn").click(function () {
//                 //confirm dialog範例
//                 swal({
//                     title: "確定刪除？",
//                     html: "按下確定後資料會永久刪除",
//                     type: "question",
//                     showCancelButton: true//顯示取消按鈕
//                 }).then(
//                     function (result) {
//                         if (result.value) {
//                             //使用者按下「確定」要做的事
//                             swal("完成!", "資料已經刪除", "success");
//                             $(this).next('.un_state').attr('value', '通過');
//                             $('.doCheck').submit();
//                         } else if (result.dismiss === "cancel"){
//                              //使用者按下「取消」要做的事
//                             swal("取消", "資料未被刪除", "error");
//                             $(this).next('.un_state').attr('value', '不通過');
//                             $('.doCheck').submit();
//                         }//end else  
//                     });//end then 
//             });
//         });
        
//     	jQuery(document).ready(function(){
//     		jQuery(".checkBtn").click(function(){
//     			 swal({
//     				  title: '審核通過嗎？', 
//     				  type: 'warning',
//     				  showCancelButton: true, 
//     				  confirmButtonColor: '#3085d6',
//     				  cancelButtonColor: '#d33',
//     				  confirmButtonText: '審核通過！', 
//     				  cancelButtonText: '不通過！',
//     				  confirmButtonClass: 'btn btn-success',
//     				  cancelButtonClass: 'btn btn-danger',
//     				  buttonsStyling: false
//     				}).then(function() {
//     					$(this).next('.un_state').attr('value', '通過');
//     				  swal(
//     				    '通過！',
//     				    '文章將被隱藏。',
//     				    'success'
    				    
//     				  );
    				  
//     				}, function(dismiss) {
//     				  // dismiss的值可以是'cancel', 'overlay',
//     				  // 'close', 'timer'
//     				  if (dismiss === 'cancel') {
//     					  $(this).next('.un_state').attr('value', '不通過');
    					  
//     				    swal(
//     				      '不通過！',
//     				      '文章保持原狀:)',
//     				      'error'
    				      
//     				    ); 
    				   
//     				  } 
//     				})
    				
//     		});
    		 
//     	});
    	
    	
    </script>


</body>
</html>

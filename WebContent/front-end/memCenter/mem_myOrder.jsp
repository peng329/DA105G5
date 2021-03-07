<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lessonorder.model.*" %>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.util.*,com.rorder.model.*"%>

<%
	MemVO memVO = (MemVO) session.getAttribute("memVO"); //
	String mem_no = memVO.getMem_no();
	
	ROrderService rorderSvc = new ROrderService();
	List<ROrderVO> rorderList = rorderSvc.getAMenAllRo(mem_no);
    pageContext.setAttribute("rorderList",rorderList);
	
	LessonOrderService lessonorderSvc = new LessonOrderService();
	List<LessonOrderVO> lessonOrderList = lessonorderSvc.getLessonOrderByMemno(mem_no);
	pageContext.setAttribute("lessonOrderList", lessonOrderList);
%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<!--引用jQuery-->
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
<!--引用SweetAlert2.js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>    
  

</head>

<body bgcolor='white'>
 	<%@ include file="/HeaderFooter/memHeader.jsp"%>

    <!-- 會員頁面 -->
     <div class="view intro-2">
        <div class="full-bg-img">
            <div class="mask rgba-black-light flex-center">   
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-4">
	<!-- 側邊攔 -->
	<%@ include file="/front-end/memCenter/memSidebar.file"%>

					<div class="col-md-8 grid-margin stretch-card">

						<div class="row">
							<div class="col-md-12">


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
            <span class="trip_manage">我的訂單</span>
        </div>
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                 <a class="nav-item nav-link" id="checked-tab" data-toggle="tab" href="#checked" role="tab" aria-controls="checked" aria-selected="false">課程報名訂單</a>
                <a class="nav-item nav-link" id="unCheck-tab" data-toggle="tab" href="#unCheck" role="tab" aria-controls="unCheck" aria-selected="true">裝備租賃訂單</a>
        </nav>
        
        <div class="tab-content" id="nav-tabContent">


            <div class="tab-pane fade show active" id="checked" role="tabpanel" aria-labelledby="checked-tab">
                <div>
                    
                    <!-------------------課程報名訂單表格內容 ------------------------->
				<%@ include file="/front-end/memCenter/memlistAllLessonorder.file"%>
				<!------------------------------ / ---------------------------->
            </div>
            </div>
            

            
            <div class="tab-pane fade" id="unCheck" role="tabpanel" aria-labelledby="unCheck-tab">
                <div>
                    <!-------------------裝備租賃訂單表格內容 ------------------------->
				<%@ include file="/front-end/memCenter/memListAllRO.file"%>
				<!------------------------------ / ---------------------------->
            </div>
            </div>
            
            
            
			</div>
		</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	<!--------------------------------------------------底部--------------------------------------------->

	<%@ include file="/HeaderFooter/footer.jsp"%>



</body>
</html>
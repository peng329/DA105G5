<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mdst_record.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>


<%
//MemVO memVO = (MemVO)request.getAttribute("memVO"); //MemServlet.java(Concroller), 存入req的memVO物件

MemService memSrc = new MemService();
MemVO memVO = (MemVO)session.getAttribute("memVO"); //
String mem_no = memVO.getMem_no();

DiveshopService diveshopSvc = new DiveshopService();
pageContext.setAttribute("diveshopSvc",diveshopSvc);

List<Mdst_recordVO> list = (List<Mdst_recordVO>)request.getAttribute("list");
pageContext.setAttribute("list",list);   

%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Bubble up index</title>
<title>Bubble up index</title>

  
 <style type="text/css">

.card-body {

    background-color: #FAFDFB;
}
    </style>
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
            <div class="search-list table-responsive-lg">
                <table class="table table-sm table-striped table-hover" >
                    <thead class="thead-dark">
						<tr>
							<tr>

							<th>潛店編號</th>
							<th>潛店名稱</th>
							<th>查看潛店</th>
							<th></th>
							<th>取消追蹤</th>
							</tr>
						</tr>
                    </thead>
                    
                    
                    
                    <tbody id="myTable">
									<%@ include file="page1.file" %> 
	<c:forEach var="mdst_recordVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			
			<td>${mdst_recordVO.ds_no}</td>
			<td>${diveshopSvc.getOneDiveshop(mdst_recordVO.ds_no).ds_name}</td>
			<td><a href="<%=request.getContextPath()%>/diveshop/diveshop.do?action=getOne_For_Display&ds_no=${mdst_recordVO.ds_no}">查看潛店</a><td>
			<td>
			<div class="top_add_fav_div mx-auto">
					<span class="top_add_fav_span">
						<form METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/mdst_record/mdst_record.do" style="margin-bottom: 0px;">

							
			    
			     <input type="hidden" name="mem_no"  value="${mdst_recordVO.mem_no}">
			      <input type="hidden" name="ds_no"  value="${mdst_recordVO.ds_no}">
			     <input type="hidden" name="action" value="delete">
									
								
					<button type="submit" class="btn top_add_fav" name="action" value="delete">
						<i class="fa fa-heart" style="font-size: 28px; color: red;"></i>
					</button>

						</form>
					</span>
				</div>
			</td>


		</tr>
	</c:forEach>
                    </tbody>
                </table>
<%@ include file="page2.file" %>		                
		                
		                
		    

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
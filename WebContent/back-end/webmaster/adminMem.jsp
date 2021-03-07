<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>

<%
	WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO");
	Authority_manageService amSvc = new Authority_manageService();
	List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
	pageContext.setAttribute("sessionWmVO",sessionWmVO);
	pageContext.setAttribute("fc_noList",fc_noList);
	
	
    MemService memSvc = new MemService();
	//DeptService ds = new DeptService();

    List<MemVO> list = memSvc.getAll();
    //List<MemVO> list = ds.mems.getAll();
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
    

    
<link href="<%=request.getContextPath()%>/back-end/webmaster/css/style.css" rel="stylesheet" type="text/css"> 

<link href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css" rel="stylesheet" type="text/css">  


</head>
  
<body>
  	<div id="page-container" class="main-admin show-menu">

<!--------------------------------------------- 上邊與側邊欄位 ------------------------------------------------------>
<%@ include file="/back-end/webmaster/adminSidebar.file"%>

<!-- --------------------------------右邊內容 -------------------------------------->
  	<div class="main-body-content w-100 ets-pt">
  		<div class="table-responsive bg-light">

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

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
                        <tr >
							<th>編號</th>
							<th>大頭照</th>
							<th>帳號</th>
							<th>密碼</th>
							<th>名稱</th>
							<th>性別</th>
							<th>生日</th>
							<th>email</th>
							<th>phone</th>
							<th>地址</th>
							<th>狀態</th>
							<th>編輯</th>
                        </tr>
                    </thead>
                    
                    
                    
                    <tbody id="myTable">
<%@ include file="page1.file" %> 
	<c:forEach var="memVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr >
			<td>${memVO.mem_no}</td>
			<td><img src="<%=request.getContextPath()%>/DBGifReader2?mem_no=${memVO.mem_no}" width="100"></td>
			<td>${memVO.mem_id}</td>
			<td>${memVO.mem_psw}</td>
			<td>${memVO.mem_name}</td>
			<td>${(memVO.mem_sex == 1)?'男':'女'}</td>
			<td>${memVO.mem_bd}</td> 
			<td>${memVO.mem_mail}</td>
			<td>${memVO.mem_phone}</td>
			<td>${memVO.mem_add}</td>	
			
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/mem/mem.do" style="margin-bottom: 0px;">
			<td><select size="1" name="mem_state" >			
			<option value="待驗證" ${(memVO.mem_state eq '待驗證')?'selected':''}>待驗證
			<option value="通過" ${(memVO.mem_state eq '通過')?'selected':''}>通過
			<option value="停權" ${(memVO.mem_state eq '停權')?'selected':''}>停權
			</select></td>	
			
			<td>
			  
			     <input type="submit" class="btn btn-primary" value="修改">
			     <input type="hidden" name="mem_id"  value="${memVO.mem_id}">
			     <input type="hidden" name="action"	value="upMemState_by_admin"></FORM>
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
    	
  
  </body>
</html>


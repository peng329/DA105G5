<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>

<%
WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO");
Authority_manageService amSvc = new Authority_manageService();
List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
pageContext.setAttribute("sessionWmVO",sessionWmVO);
pageContext.setAttribute("fc_noList",fc_noList);
	
    WebmasterService webmasterSvc = new WebmasterService();
	
	pageContext.setAttribute("amSvc",amSvc);

    List<WebmasterVO> list = webmasterSvc.getAll();
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
 
<link href="<%=request.getContextPath()%>/back-end/webmaster/css/style.css" rel="stylesheet" type="text/css">


<style type="text/css">



 nav{ 
 	opacity: .8;
 } 
</style>
  
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
						<tr>
							<th>編號</th>
							<th>姓名</th>
							<th>性別</th>
							<th>帳號</th>
							<th>密碼</th>
							<th>信箱</th>
							<th>到職日期</th>
							<th>離職日期</th>						
							<th>權限</th>
							<th>修改</th>
						</tr>
                    </thead>
                    
                    
                    
                    <tbody id="myTable">
<%@ include file="page1.file" %> 
	<c:forEach var="webmasterVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${webmasterVO.wm_no}</td>
			<td>${webmasterVO.wm_name}</td>
			<td>${(webmasterVO.wm_sex == 1)?'男':'女'}</td>
			<td>${webmasterVO.wm_id}</td>
			<td>${webmasterVO.wm_psw}</td>
			<td>${webmasterVO.wm_mail}</td> 
			<td>${webmasterVO.ob_date}</td>
			<td>${webmasterVO.dd_date}</td>
			<td>
			<c:forEach var="stringArry"  items= "${amSvc.getAuthority_manageVOsByContrast(webmasterVO.wm_no)}">	
			<c:if test="${stringArry[0] eq '1'}">
				<p>${stringArry[2]}</p>
			</c:if>		
			</c:forEach>
			</td>

			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/webmaster/webmaster.do" style="margin-bottom: 0px;">
			     <input type="submit" class="btn btn-primary" value="修改">
			     <input type="hidden" name="wm_no"  value="${webmasterVO.wm_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
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


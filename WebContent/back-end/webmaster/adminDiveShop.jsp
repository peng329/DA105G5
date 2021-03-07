<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.diveshop.model.*" %>
<%@ page import="com.dspic.model.*" %>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>


<jsp:useBean id="diveshopSvc" scope="page" class="com.diveshop.model.DiveshopService"></jsp:useBean>
<%
	WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO");
	Authority_manageService amSvc = new Authority_manageService();
	List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
	pageContext.setAttribute("sessionWmVO",sessionWmVO);
	pageContext.setAttribute("fc_noList",fc_noList);
	
	
	List<DiveshopVO> list = diveshopSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="dspicSvc" scope="page" class="com.dspic.model.DspicService"></jsp:useBean>

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
                       	<tr>
							<th>潛店編號</th>
							<th>潛店名稱</th>
							<th>營業登記證</th>
							<th>潛店電話</th>
							<th>潛店地址</th>
							<th>潛店帳號</th>
							<th>潛店密碼</th>
							<th>潛店信箱</th>
							<th>緯經度</th>
							<th>潛店圖片</th>
							<th>潛店評分</th>
							<th>潛店檢舉次數</th>
							<th>資格審核</th>
							<th>審核修改</th>

						</tr>
                    </thead>
                    
                    
                    
                    <tbody id="myTable">
<%@ include file="page1.file" %> 
	<c:forEach var="diveshopVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr ${(diveshopVO.ds_no==param.ds_no) ? 'bgcolor=#CCCCFF':'' }>
			<td>${diveshopVO.ds_no}</td>
			<td>${diveshopVO.ds_name}</td>
			<td>${diveshopVO.brcid}</td>
			<td>${diveshopVO.tel}</td>
			<td>${diveshopVO.address}</td>
			<td>${diveshopVO.dsaccount}</td>
			<td>${diveshopVO.dspaw}</td>
			<td>${diveshopVO.dsmail}</td>
			<td>${diveshopVO.ds_latlng}</td>			
			<td>				  
				<c:forEach var="dpic_seq" items="${diveshopSvc.getDpic_seqByDs_no(diveshopVO.ds_no)}" >
				
					<img class="dpic" width="100" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?dpic_seq=${dpic_seq}"/>				
				</c:forEach>
				
			</td>
			<td>${diveshopVO.ds_ascore}</td>
			<td>${diveshopVO.ds_rep_no}</td>
			
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/diveshop/diveshop.do" style="margin-bottom: 0px;">
			<td><select size="1" name="ds_state" >			
			<option value="未審核" ${(diveshopVO.ds_state eq '待驗證')?'selected':''}>未審核
			<option value="審核通過" ${(diveshopVO.ds_state eq '審核通過')?'selected':''}>審核通過
			<option value="停權" ${(diveshopVO.ds_state eq '停權')?'selected':''}>停權
			</select></td>				
			<td>
			  	<input type="submit" class="btn btn-primary" value="修改">
			     <input type="hidden" name="action"	value="upDsState_by_admin">
			    <input type="hidden" name="ds_no"	value="${diveshopVO.ds_no}">
			     </FORM>
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
    </script>
    
    
  </body>
</html>


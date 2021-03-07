<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>
<%@ page import="com.func.model.*"%>

<%
	//登入員工的VO
  	WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO"); 
	Authority_manageService amSvc = new Authority_manageService();
	List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());
	pageContext.setAttribute("sessionWmVO",sessionWmVO);
	pageContext.setAttribute("fc_noList",fc_noList);
  
	//為了取得修改的該筆員工VO
	WebmasterVO webmasterVO = (WebmasterVO)request.getAttribute("webmasterVO");
	String wm_no = webmasterVO.getWm_no();
	Authority_manageService authority_manageSvc = new Authority_manageService();
	List<String[]> list = authority_manageSvc.getAuthority_manageVOsByContrast(wm_no);
  
    pageContext.setAttribute("list",list);
    pageContext.setAttribute("wm_no",wm_no);
    
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


</head>
  
<body>
  	<div id="page-container" class="main-admin show-menu">
	 <!--------------------------------------------- 上邊與側邊欄位 ------------------------------------------------------>
<%@ include file="/back-end/webmaster/adminSidebar.file"%>

<!-- --------------------------------右邊內容 -------------------------------------->
  	<div class="main-body-content w-100 ets-pt">
  	<div class="col-md-8 grid-margin stretch-card">
  	
<!--   	員工資料修改 -->
 		<div class="card">
		        <div class="card-body">
		            <div class="row">

		                <div class="col-md-12">
		                    <h4>員工資料修改</h4>
		                     <hr>
		                </div>
		               
		            </div>
		            <div class="row">
						<div class="col-md-3">
		                </div>
		                <div class="col-md-6">
		                
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color:red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color:red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
		                
		                    <form METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/webmaster/webmaster.do" name="form1">
                              <div class="form-group row">
                                <label for="text" class="col-4 col-form-label">員工編號</label> 
                                <div class="col-8">
                                  <%=webmasterVO.getWm_no()%>
                                </div>
                              </div>
                              
                              <div class="form-group row">
                                <label for="username" class="col-4 col-form-label">帳號<font color=red><b>*</b></font></label> 
                                <div class="col-8">
                                  <%=webmasterVO.getWm_id()%>
                                </div>
                              </div>
  
                              <div class="form-group row">
                                <label for="PASSWORD" class="col-4 col-form-label">密碼</label> 
                                <div class="col-8">
                                  <input type="PASSWORD" name="wm_psw" value="<%=webmasterVO.getWm_psw()%>" class="form-control here" >
                                </div>
                              </div>
                              <div class="form-group row">
                                <label for="TEXT" class="col-4 col-form-label">姓名</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="wm_name" value="<%=webmasterVO.getWm_name()%>" class="form-control here" >
                                </div>
                              </div>
                              
                              <div class="form-group row">
                                <label for="select" class="col-4 col-form-label">性別</label> 
                                <div class="col-8">
                                  <select size="1" name="wm_sex" class="custom-select">
                                    <option value="1" <% if(webmasterVO.getWm_sex() == 1) out.println("selected"); %>>男
									<option value="2" <% if(webmasterVO.getWm_sex() == 2) out.println("selected"); %>>女
                                  </select>
                                </div>
                              </div>
           
                              <div class="form-group row">
                                <label for="email" class="col-4 col-form-label">Email*</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="wm_mail" value="<%=webmasterVO.getWm_mail()%>" class="form-control here" required="required">
                                </div>
                              </div>
                              <div class="form-group row">
                                <label for="date" class="col-4 col-form-label">到職日期</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="ob_date" id="start_date" autocomplete="off" class="form-control here">
                                </div>
                              </div>
                               <div class="form-group row">
                                <label for="date" class="col-4 col-form-label">離職日期</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="dd_date" id="end_date" autocomplete="off" class="form-control here" >
                                </div>
                              </div>
                              
<!-- -------------------------------------權限 ------------------------------------------------------>
								
								 <div class="form-group row">
								 <label for="checkbox" class="col-4 col-form-label">權限</label>
								<div class="col-8">
								<c:forEach var="listArry" items="${list}">
									<div class="custom-control custom-checkbox">
								      <input type="checkbox" class="custom-control-input amAuth" id="${listArry[1]}" name="fc_no" value="${listArry[1]}" ${(listArry[0] eq '0')?'':'checked'}/>
								      <label class="custom-control-label" for="${listArry[1]}">${listArry[2]}</label>
									</div>
									<script type="text/javascript">
									
// 									$('#${listArry[1]}').prop('${listArry[2]}', true);
// 										$(document).ready(function() {
// 										    $("#${listArry[1]}").click(function(){
// 										    	if(this.checked) {
// 										    		var addOrDelete = "insert";		    		
// 										    	}else{
// 										    		var addOrDelete = "delete";		    		
// 										    	}        
// 										        $.ajax({
<%-- 													url:"<%=request.getContextPath()%>/back-end/authority_manage/authority_manage.do", --%>
// 													data:{wm_no:"${wm_no}", fc_no:"${listArry[1]}", action:addOrDelete},
// 													type: "POST",
// 													dataType: "json",
// 													success: function(data){	
// 													}
// 												});
// 											});	   
// 										    });
										</script>
									  <br>
								</c:forEach>
								     </div>
                              </div>
										<!-- 權限  尾-->		
                       
                              
                                </div>
                              </div> 
                              <div class="form-group row">
                                <div class="offset-4 col-8">
                                <input type="hidden" name="action" value="update">
								<!-- 一些不能改的用hidden傳回 -->
								<input type="hidden" name="wm_no" value="<%=webmasterVO.getWm_no()%>">
								<input type="hidden" name="wm_id" value="<%=webmasterVO.getWm_id()%>">
								<input type="hidden" name="reg_time" value="<%=webmasterVO.getReg_time()%>">
								
								
				
																
                                  <button name="submit" type="submit" class="btn btn-primary">修改</button>
                                </div>
                              </div>
                            </form>
		                </div>
		            </div>
		            <!--   	員工資料修改  尾 -->
		            
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
    	
    	
    	
    	//權限勾選增刪ajax
//     	$('.amAuth').prop('${listArry[2]}', true);
			$(document).ready(function() {
			    $('.amAuth').click(function(){
			    	if(this.checked) {
			    		var addOrDelete = "insert";		    		
			    	}else{
			    		var addOrDelete = "delete";		    		
			    	}        
			        $.ajax({
						url:"<%=request.getContextPath()%>/back-end/authority_manage/authority_manage.do",
						data:{wm_no:"${wm_no}", fc_no:$(this).val(), action:addOrDelete},
						type: "POST",
						dataType: "json",
						success: function(data){	
						}
					});
				});	   
	    	});
    </script>


<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>



<% 
  java.sql.Date ob_date = null;
  try {
	  ob_date = webmasterVO.getOb_date();

   } catch (Exception e) {
	   ob_date = new java.sql.Date(System.currentTimeMillis());
   }
  
  java.sql.Date dd_date = null;
  try {
	  dd_date = webmasterVO.getDd_date();

   } catch (Exception e) {
	   dd_date = null;
   }
  
%>



<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>

   
        
     jQuery(function(){
   	 jQuery('#start_date').datetimepicker({
   	  format:'Y-m-d',
      value:'<%=ob_date%>',
   	  //theme: '',
   	  onShow:function( ct ){
   	   this.setOptions({
   	    maxDate:jQuery('#end_date').val()?jQuery('#end_date').val():false
   	   })
   	  },
   	  
   	  timepicker:false
   	 });
   	 jQuery('#end_date').datetimepicker({
   	  format:'Y-m-d',
   	  value:'${webmasterVO.dd_date}',
   	  //theme: '',
   	  onShow:function( ct ){
   	   this.setOptions({
   	    minDate:jQuery('#start_date').val()?jQuery('#start_date').val():false
   	   })
   	  },
   	  
   	  timepicker:false
   	 });
   	});
     
     </script>
</body>
</html>

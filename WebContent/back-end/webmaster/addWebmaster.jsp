<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>
<%@ page import="com.func.model.*"%>

<%
  	WebmasterVO sessionWmVO = (WebmasterVO) session.getAttribute("webmasterVO");
	pageContext.setAttribute("sessionWmVO",sessionWmVO);
	
	WebmasterVO webmasterVO = (WebmasterVO) request.getAttribute("webmasterVO");
	pageContext.setAttribute("webmasterVO",webmasterVO);
	
	FuncService funcSvc = new FuncService();
  
	Authority_manageService amSvc = new Authority_manageService();
	List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());	
	pageContext.setAttribute("fc_noList",fc_noList);
	
	String[] fc_noArray = (String[])request.getAttribute("fc_noArray");
   if(fc_noArray != null){
		ArrayList<String> fc_noArray2 = new ArrayList(Arrays.asList(fc_noArray));
		pageContext.setAttribute("fc_noArray2",fc_noArray2);
	   	System.out.println(fc_noArray2);
	   }
	
  pageContext.setAttribute("funcSvc",funcSvc);  

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
  	<div class="col-md-8 grid-margin stretch-card">
  	
<!--   	員工資料修改 -->
 		<div class="card">
		        <div class="card-body">
		            <div class="row">

		                <div class="col-md-12">
		                    <h4>新增員工</h4>
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
                                <label for="username" class="col-4 col-form-label">帳號</label> 
                                <div class="col-8">
                                  <input type="text" name="wm_id" id="wm_id" value='<%= (webmasterVO==null)? "" : webmasterVO.getWm_id()%>' class="form-control here" >
                                </div>
                              </div>
  
                              <div class="form-group row">
                                <label for="PASSWORD" class="col-4 col-form-label">密碼</label> 
                                <div class="col-8">
                                  		由系統產生
                                </div>
                              </div>
                              
                              <div class="form-group row">
                                <label for="TEXT" class="col-4 col-form-label" >姓名</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="wm_name" id="inputname" value='<%= (webmasterVO==null)? "" : webmasterVO.getWm_name()%>' class="form-control here" >
                                </div>
                              </div>
                              
                              <div class="form-group row">
                                <label for="select" class="col-4 col-form-label">性別</label> 
                                <div class="col-8">
                                  <select size="1" name="wm_sex" class="custom-select">
                                    <option value="1" ${(webmasterVO.wm_sex == 1)?'selected':''}>男
									<option value="2" ${(webmasterVO.wm_sex == 2)?'selected':''}>女
                                  </select>
                                </div>
                              </div>
           
                              <div class="form-group row">
                                <label for="email" class="col-4 col-form-label">Email</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="wm_mail" id="email" value='<%= (webmasterVO==null)? "" : webmasterVO.getWm_mail()%>' class="form-control here" required="required">
                                </div>
                              </div>
                              <div class="form-group row">
                                <label for="date" class="col-4 col-form-label">到職日期</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="ob_date" id="start_date" autocomplete="off" class="form-control here">
                                </div>
                              </div>

                              
                              								<!-- 權限 -->
								
								 <div class="form-group row">
								 <label for="checkbox" class="col-4 col-form-label">權限</label>
								<div class="col-8">
								
								<c:forEach var="funcVO"  items="${funcSvc.all}">
								<div class="custom-control custom-checkbox">
								      <input type="checkbox" class="custom-control-input"  id="${funcVO.fc_no}" name="fc_no" value="${funcVO.fc_no}" ${ fc_noArray2.contains(funcVO.fc_no)?'checked':''} />
<%-- 								      <input type="checkbox" class="custom-control-input"  id="${funcVO.fc_no}" name="fc_no" value="${funcVO.fc_no}" ${ (fc_noMap[funcVO.fc_no] != null)?'checked':''} /> --%>
								      <label class="custom-control-label" for="${funcVO.fc_no}">${funcVO.fc_name}</label>
									</div>	
								      
									
									
									  <br>
								</c:forEach>
								     </div>
                              </div>
										<!-- 權限  尾-->		
                       
                              
                                </div>
                              </div> 
                              <div class="form-group row">
                                <div class="offset-4 col-8">
                              <input type="hidden" name="action" value="insert">							
								<!-- 一些不能改的用hidden傳回 -->						
                                  <button name="submit" type="submit" class="btn btn-primary">新增</button>
                                  <hr>
                                  <button type="button" class="btn btn-primary" id="magic">神奇小按鈕</button>
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
    	
    	
    	//神奇小按鈕
    	$(document).ready(function(){
    		$('#magic').click(function(){
    			$('#wm_id').val('andy');   		
    			$('#email').val('da105g5@gmail.com');
    			$('#inputname').val('andy');
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
//         $.datetimepicker.setLocale('zh');
//         $('#f_date1').datetimepicker({
//            theme: '',              //theme: 'dark',
//  	       timepicker:false,       //timepicker:true,
//  	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
//  	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
<%--  		   value: '<%=webmasterVO.getOb_date()%>', // value:   new Date(), --%>
//            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//            //startDate:	            '2017/07/10',  // 起始日
//            //minDate:               '-1970-01-01', // 去除今日(不含)之前
//            //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
//         });
        
        
        
$.datetimepicker.setLocale('zh');
$('#start_date').datetimepicker({
  theme: '',              //theme: 'dark',
   timepicker:false,       //timepicker:true,
   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
   format:'Y-m-d',         //format:'Y-m-d H:i:s',
   value: '<%=ob_date%>', // value:   new Date(),
//    disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//    startDate:	            '2017/07/10',  // 起始日
//    minDate:               '-1970-01-01', // 去除今日(不含)之前
//    maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});
     
     </script>
</body>
</html>

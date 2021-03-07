<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.webmaster.model.*"%>
<%@ page import="com.authority_manage.model.*"%>
<%@ page import="com.func.model.*"%>

<%
WebmasterVO sessionWmVO = (WebmasterVO) session.getAttribute("webmasterVO");
pageContext.setAttribute("sessionWmVO",sessionWmVO);


Authority_manageService amSvc = new Authority_manageService();
List<String> fc_noList = amSvc.getFc_noByWm(sessionWmVO.getWm_no());	
pageContext.setAttribute("fc_noList",fc_noList);




pageContext.setAttribute("amSvc",amSvc);    


WebmasterVO webmasterVO = (WebmasterVO) request.getAttribute("webmasterVO");
pageContext.setAttribute("webmasterVO",webmasterVO);

  

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
		                    <h4>個人資料修改</h4>
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
                                <label for="username" class="col-4 col-form-label">帳號</label> 
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
                                <label for="email" class="col-4 col-form-label">Email</label> 
                                <div class="col-8">
                                  <input type="TEXT" name="wm_mail" value="<%=webmasterVO.getWm_mail()%>" class="form-control here" required="required">
                                </div>
                              </div>
                              <div class="form-group row">
                                <label for="date" class="col-4 col-form-label">到職日期</label> 
                                <div class="col-8">
                                  <%=webmasterVO.getOb_date()%>
                                </div>
                              </div>
                              
                            <div class="form-group row">
                                <label for="date" class="col-4 col-form-label">權限</label> 
                                <div class="col-8">
                                  	<c:forEach var="stringArry"  items= "${amSvc.getAuthority_manageVOsByContrast(webmasterVO.wm_no)}">	
										<c:if test="${stringArry[0] eq '1'}">
											<p>${stringArry[2]}</p>
										</c:if>		
									</c:forEach>
                                </div>
                              </div>
                              	
                       
                              
                                </div>
                              </div> 
                              <div class="form-group row">
                                <div class="offset-4 col-8">
                                <input type="hidden" name="action" value="oneUpdate">
								<!-- 一些不能改的用hidden傳回 -->
								<input type="hidden" name="wm_no" value="<%=webmasterVO.getWm_no()%>">
								<input type="hidden" name="wm_id" value="<%=webmasterVO.getWm_id()%>">
								<input type="hidden" name="ob_date" value="<%=webmasterVO.getOb_date()%>">
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

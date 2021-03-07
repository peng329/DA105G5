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


<link href="<%=request.getContextPath()%>/back-end/webmaster/css/style.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css" rel="stylesheet" type="text/css">       

<link href="css/adminIndex.css" rel="stylesheet" type="text/css">    
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>


<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>
    


  
  </head>
  
<body>
  	<div id="page-container" class="main-admin show-menu">
<!--------------------------------------------- 上邊與側邊欄位 ------------------------------------------------------>
<%@ include file="/back-end/webmaster/adminSidebar.file"%>
<!------------------------------------ 內容 ------------------->

<!-- --------------------------------右邊內容 -------------------------------------->
  	<div class="main-body-content w-100 ets-pt">

  	
  	<div class="col-md-8 grid-margin stretch-card position-fixed">
  	
  	
  	<header >
	<video autoplay playsinline muted loop preload poster="https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/oceanshot.jpg">
		<source src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/ocean-small.webm" />
    <source src="http://thenewcode.com/assets/videos/ocean-small.mp4" />
	</video>
 	<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 285 80" preserveAspectRatio="xMidYMid slice">
    <defs>
    <mask id="mask" x="0" y="0" width="100%" height="100%" >
      <rect x="0" y="0" width="100%" height="100%" />
    <text x="85"  y="30">BUBBLE</text>
      <text x="100"  y="55" >&nbsp;&nbsp;&nbsp;&nbsp;UP</text>
      </mask>
  </defs>
 <rect x="0" y="0" width="100%" height="100%" />
  </svg>
</header>


		            
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
    	
    	
    	//內容大動態文字用
    	const video = document.querySelector("video");
			if (window.matchMedia('(prefers-reduced-motion)').matches) {
			  video.removeAttribute("autoplay");
			  video.pause();
			}
    </script>



   
</body>
</html>

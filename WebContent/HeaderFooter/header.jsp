<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/css/BubbleUpStyle.css" rel="stylesheet" type="text/css">
	
<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>

<title>dive shop</title>
</head>

<header>
	<!--js-->

	<!--CSS-->
	<style>
	.nav-item {
    padding-right: 2.5em;
    padding-left: 2.5em;
	}
	
	.user-profile{
	    max-height: 3em;
    	max-width: 3em;
	}
	</style>
	
	<!------------------------------------>
	
	<div class="container-fluid" id="con1">
	 <div class="row justify-content-center">
	<nav class="navbar navbar-expand-lg navbar-light bg-white navbar-scroll ">
		<a class="navbar-brand" href="<%=request.getContextPath()%>/front-end/index.jsp"> 
			<img src="<%=request.getContextPath()%>/HeaderFooter/Logo.jpg"	height=100px width=100px>
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"	data-target="#navbarSupportedContent"	aria-controls="navbarSupportedContent" aria-expanded="false"	aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse inline"	id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto inline">
				<li class="nav-item">
					<a class="nav-link" href="<%=request.getContextPath()%>/front-end/locale/locale.jsp">潛點資訊</a>	
				</li>
				<li class="nav-item">
					 <a class="nav-link" href="<%=request.getContextPath()%>/front-end/diveshop/showAllDiveshop.jsp">潛店散策</a>
				</li>
				<li class="nav-item dropdown">
        			<a class="nav-link dropdown-toggle" href="<%=request.getContextPath()%>/front-end/locale/locale.jsp" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		         	潛水社群
		        	</a>
		        		<div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          			<a class="dropdown-item" href="#">施工中</a>
		          			<a class="dropdown-item" href="#">施工中</a>
		          		<div class="dropdown-divider"></div>
		          			<a class="dropdown-item" href="#">施工中</a>
		        		</div>
		      	</li>
				<li class="nav-item">
					<a class="nav-link"	href="<%=request.getContextPath()%>/front-end/act_list/listAllAct_List1.jsp">揪團趣</a>
				</li>
				
				<%--------------判斷會員是否登入，載入不同的右上角-----------------------------------------%>
				<%--------------未登入-----------------------------------------%>
				<c:if test="${memVO == null}">
			     <li class="nav-item dropdown">
      				<div id="buycar">
    				  <a href="<%=request.getContextPath()%>/front-end/rentcart/Cart.jsp">購物車/
    				  	<c:if test="${eplistsize!=null}">
    				  		(${eplistsize})
    				  	</c:if>
    				  </a>
      				  <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
     				      登入
        			  </a>
        				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
         					 <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem/memLogin.jsp">會員登入</a>
         				 <div class="dropdown-divider"></div>
         					 <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem/dsLogin.jsp">潛店登入</a>
        				</div>
     				 </div>
    			 </li>
    			 </c:if>
    			 
    			 <%--------------已登入-----------------------------------------%>
    			 <c:if test="${memVO != null}">
    			 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/memCenter/css/memcenter.css">
					<link href="<%=request.getContextPath()%>/back-end/webmaster/css/admin.css" rel="stylesheet" type="text/css">
					
					 <li class="nav-item dropdown">
      				<div id="buycar">
    				  <a href="<%=request.getContextPath()%>/front-end/rentcart/Cart.jsp">購物車
    				  	<c:if test="${eplistsize!=null}">
    				  		(${eplistsize})
    				  	</c:if>
    				  </a>
			      <li class="nav-item avatar dropdown">
			      <a href="#" id="navbarDropdownMenuLink-55" role="button" data-toggle="dropdown" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
			      <span class="navdropdown-title"><div class="user-avatar-wrapper img-circle">
			      <span class=" lazy-load-image-background blur lazy-load-image-loaded" style="background-image:;background-size:;color:transparent;display:inline-block">
			      <img class="border user-profile rounded-circle" src="<%=request.getContextPath()%>/DBGifReader2?mem_no=${memVO.mem_no}" >
			      </span></div>${memVO.mem_name}</span></a>
			        <div class="dropdown-menu dropdown-menu-lg-right dropdown-secondary"
			          aria-labelledby="navbarDropdownMenuLink-55">
			          <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/memCenter/memCenter.jsp">會員頁面</a>
			          <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=memLogout">登出</a>
			        </div>
			      </li>
    			 
    			 </c:if>
			</ul>
			</div>
	</nav>		
	</div>
	</div>
	
</header>


</html>
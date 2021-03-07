<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Bubble up index</title>


</head>
<body bgcolor='white'>
		<!--====================導覽頁===========================  -->
	<header>

	<%@ include file="/HeaderFooter/header.jsp"%>
	

	</header>

<body>

<section>
<div class ='container' id="con2">
 <div class="embed-responsive embed-responsive-16by9">
<video loop="true" autoplay="autoplay"  muted="true">
  
<!-----------------------影片我有改短的，之後再整合------------------------------------------- -->
  <source src="<%=request.getContextPath()%>/網站影片/Project Name.mp4">
</video>
</div>
</div>
<br>
<div class='container' id="con3">
<h1><i>關於我們</i></h1>
<h3 align="center">
＂在那海水酣睡的宮殿里，鋪滿了多少奇珍異寶？＂
</h3>

<p>    大海，孕育萬物的起源，<br>
  四面環海的台灣，有著豐富的海洋資源，但多數人對我們切身相關的海洋卻不甚熟悉。<br>
</p>
<p>  
我們希望，打造一個友善潛水新手的社群平台，<br>
讓更多人藉潛水認識台灣之美的同時，也可以透過無界的網路，找尋志同道合的潛伴。<br>
</p>
<h3 align="center" id="slogn">
"How long haven't you take a deep breath?"
</h3>
</div>
<br>

<div class='container' id="con4">
  <div class="row">
    <div class="col-md-6">
      <figure>
      <a href="<%=request.getContextPath()%>/front-end/act_list/listAllAct_List1.jsp">
        <img src="<%=request.getContextPath()%>/首頁圖片/環保活動.jpeg" alt="">
        <figcaption><p>揪團</p></figcaption>
       </a>
        </figure>
    </div>
    <div class="col-md-6">
      <figure> 
       <a href="<%=request.getContextPath()%>/front-end/locale/locale.jsp">     
        <img src="<%=request.getContextPath()%>/首頁圖片/熱門潛點2.jpg" alt="">
        <figcaption><p>潛點</p></figcaption>
       </a>
        </figure>
    </div>
  </div>

  <div class="row">

    <div class="col-md-6">
      <figure>
      <a href="<%=request.getContextPath()%>/front-end/diveshop/showAllDiveshop.jsp">
        <img src="<%=request.getContextPath()%>/首頁圖片/熱門文章2.jpg" alt="">
        <figcaption><p>文章<p></figcaption>
       </a>
        </figure>
    </div>
        <div class="col-md-6">      
      <figure>
      <a href="<%=request.getContextPath()%>/front-end/diveshop/showAllDiveshop.jsp">
        <img src="<%=request.getContextPath()%>/首頁圖片/潛店.jpg" alt="">
        <figcaption><p>潛店<p></figcaption>
      </a>
        </figure>
    </div>
  </div>
</div>

</section>

</body>

	<!--------------------------------------------------底部--------------------------------------------->

	<%@ include file="/HeaderFooter/footer.jsp"%>

</html>
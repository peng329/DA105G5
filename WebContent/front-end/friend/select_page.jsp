<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<html>
<head>
<title>IBM Friend: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>DA105G5 Friend: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for DA105G5 Friend: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllFriend.jsp'>List</a> all Friends.  <br><br></li>
  


 <jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
   
 
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/friend/friend.do" >
       <b>我的好友(通過)選擇會員編號:</b>
       <select size="1" name="mem_no">
         <c:forEach var="memVO" items="${memSvc.all}" > 
          <option value="${memVO.mem_no}">${memVO.mem_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getAllMemFriend_Display">
       <input type="submit" value="送出">
       </FORM>
       
       <br>
       <br>
       <br>
       
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/friend/friend.do" >
       <b>加我為好友(待通過)，選擇會員編號:</b>
       <select size="1" name="mem_no">
         <c:forEach var="memVO" items="${memSvc.all}" > 
          <option value="${memVO.mem_no}">${memVO.mem_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getAllMemFriendUnc_Display">
       <input type="submit" value="送出">
    </FORM>
 
  



<h3>好友管理</h3>

<ul>
  <li><a href='addFriend.jsp'>Add</a> a new Friend.</li>
</ul>

</body>
</html>
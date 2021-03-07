<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<html>
<head>
<title>IBM Authority_manage: Home</title>

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
   <tr><td><h3>DA105G5 Authority_manage: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for DA105G5 Authority_manage: Home</p>

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
  <li><a href='listAllAuthority_manage.jsp'>List</a> all Authority_manages.  <br><br></li>
  


 <jsp:useBean id="webmasterSvc" scope="page" class="com.webmaster.model.WebmasterService" />
   
 
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/authority_manage/authority_manage.do" >
       <b>選擇員工編號:</b>
       <select size="1" name="wm_no">
         <c:forEach var="webmasterVO" items="${webmasterSvc.all}" > 
          <option value="${webmasterVO.wm_no}">${webmasterVO.wm_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getAllWmAuthority_manage_Display">
       <input type="submit" value="送出">
    </FORM>
 
  



<h3>權限管理</h3>

<ul>
  <li><a href='addAuthority_manage.jsp'>Add</a> a new Authority_manage.</li>
</ul>

</body>
</html>
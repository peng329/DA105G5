<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Equipment Picture: Home</title>

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
   <tr><td><h3>IBM Equipment Picture:</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Equipment Picture: Home</p>

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
    <jsp:useBean id="eqpicSvc" scope="page" class="com.eqpic.model.EqpicService" />
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" >
        <b>請輸入潛店編號 (如DS0001):</b>
        <input type="text" name="ds_no">
        <input type="hidden" name="action" value="getADS_All_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" >
        <b>請輸入裝備流水號(如EP0001):</b>
       <input type="text" name="ep_seq">       
        <input type="hidden" name="action" value="get_EPAL_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

</ul>


<h3>圖片管理</h3>

<ul>
  <li><a href='addEpic.jsp'>Add</a> a new Equipment Picture.</li>
</ul>

</body>
</html>
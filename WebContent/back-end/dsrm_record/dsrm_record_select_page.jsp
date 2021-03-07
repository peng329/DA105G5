<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Dsrm_record_select_page</title>
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
<body>
<table id="table-1">
   <tr><td><h3>Dsrm Record : Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Dsrm Record : Home</p>

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
  <li><a href='<%=request.getContextPath()%>/back-end/dsrm_record/listAllDsrm_record.jsp'>List</a> all Dsrm Record.  <br><br></li>
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dsrm_record/dsrm_record.do" >
        <b>輸入檢舉編號:</b>
        <input type="text" name="rdsr_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>    
  </li>
</ul>

<ul>
  <li>
  	<form method="post" Action="<%=request.getContextPath()%>/dsrm_record/dsrm_record.do">
  		<b><font color=blue>複合查詢:</font></b><br>
  		<b>輸入潛店編號:(如:DSR001)</b>
  		<input type="text" name="ds_no" value="DS0001"><br>
  		<b>輸入會員編號:</b>
  		<input type="text" name="mem_no" value="M000001"><br>
  		<b>選擇審核狀態</b>
  		<input type="radio" size="1" name="rep_state" value="未審核" id="Unreviewed"/><label for="Unreviewed">未審核</label>
		<input type="radio" size="1" name="rep_state" value="審核通過" id="reviewed"/>	<label for="reviewed">審核通過</label>
		<input type="submit" value="送出">
    	<input type="hidden" name="action" value="listDsrm_record_ByCompositeQuery">
  	</form>
  </li>
</ul>

<h3>課程管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/dsrm_record/addDsrm_record.jsp'>Add</a> a new Dive Shop.</li>
</ul>
</body>
</html>
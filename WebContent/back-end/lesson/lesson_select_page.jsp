<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Lesson_select_page</title>
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
   <tr><td><h3>Dive Shop Lesson: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Dive Shop Lesson: Home</p>

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
  <li><a href='<%=request.getContextPath()%>/back-end/lesson/listAllLesson.jsp'>List</a> all Dive Shops Lesson.  <br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lesson/lesson.do" >
        <b>輸入課程編號 (如LE0001):</b>
        <input type="text" name="les_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>


<li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lesson/lesson.do" >
        <b>輸入潛店名稱 (如潛水貨艙):</b>
        <input type="text" name="ds_name">
        <input type="hidden" name="action" value="findByDsname">
        <input type="submit" value="送出">
    </FORM>
  </li>
</ul>

<ul>
  <li>
  	<form METHOD="post" ACTION="<%=request.getContextPath()%>/lesson/lesson.do">
  	<b><font color=blue>複合查詢:</font></b> <br>
  	<b>輸入課程名稱</b>
  	<input type="text" name="les_name" value="OW"><br>
  	<b>輸入潛店名稱</b>
  	<input type="text" name="ds_name" value="潛水貨艙"><br>
  	<b>選擇課程狀態</b>
  	<select size="1" name="les_state">
  		<option value="">
  		<option value="開放報名">開放報名
  		<option value="結束報名">結束報名
  	</select>
  	<b>課程陳列狀態</b>
  	<select size="1" name="less_state">
  		<option value="">
  		<option value="上架">上架
  		<option value="下架">下架
  	</select>
  	<input type="submit" value="送出">
    <input type="hidden" name="action" value="listLesson_ByCompositeQuery">
  	</form>
  </li>
</ul>
<h3>課程管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/lesson/addLesson.jsp'>Add</a> a new Dive Shop.</li>
</ul>
</body>
</html>
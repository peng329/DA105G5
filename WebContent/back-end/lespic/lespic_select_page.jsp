<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>lespic_select_page</title>

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
   		<tr><td><h3>Dive Shop Lesson Pic: Home</h3><h4>( MVC )</h4></td></tr>
	</table>
<p>This is the Home page for Dive Shop Lesson Pic: Home</p>

<h3>資料查詢</h3>

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
  <li><a href='<%=request.getContextPath()%>/back-end/lespic/listAllLespic.jsp'>List</a> all Dive Shops Lesson Pic.  <br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lespic/lespic.do" >
        <b>輸入照片流水號 (如DSP001):</b>
        <input type="text" name="lpic_seq">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>
  
  <jsp:useBean id="lespicSvc" scope="page" class="com.lespic.model.LespicService"/>
  <li>
  	<form METHOD ="post" ACTION="<%=request.getContextPath()%>/lespic/lespic.do">
  		<b>選擇照片流水號 :</b>
  		<select size="1" name="lpic_seq">
  			<c:forEach var="lespicVO" items="${lespicSvc.all}">
  				<option value="${lespicVO.lpic_seq }"/>${lespicVO.lpic_seq }
  			</c:forEach>
  		</select>
  		<input type="hidden" name="action" value="getOne_For_Display">
  		<input type="submit" value="Send">
  	</form>
  </li>
  
  <li>
  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/lespic/lespic.do">
  		<b>選擇課程編號：</b>
  		<select size="1" name="lpic_seq">
  			<c:forEach var="lespicVO" items="${lespicSvc.all}">
  				<option value="${lespicVO.lpic_seq}"/>${lespicVO.les_no}
  			</c:forEach>
  		</select>
  		<input type="hidden" name="action" value="getOne_For_Display">
  		<input type="submit" value="Send">
  	</FORM>
  </li>

</ul>

<h3>潛店照片管理</h3>

<ul>
	<li><a href='<%=request.getContextPath()%>/back-end/lespic/addLespic.jsp'>add</a>a new Dive Shop picture.</li>
</ul>
</body>
</html>
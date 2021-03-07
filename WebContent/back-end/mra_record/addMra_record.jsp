<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mra_record.model.*"%>



<%
  Mra_recordVO mra_recordVO = (Mra_recordVO) request.getAttribute("mra_recordVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>文章檢舉新增 - addMra_record.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
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

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>文章檢舉新增 - addMra_record.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="80" height="80" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/mra_record/mra_record.do" name="form1">
<table>
			
	<tr>
		<td>會員編號:</td>
		<td><input type="TEXT" name="mem_no" size="45" 
			 value="<%= (mra_recordVO==null)? "" : mra_recordVO.getMem_no()%>" /></td>
	</tr>
	<tr>
		<td>文章編號:</td>
		<td><input type="TEXT" name="mpo_no" size="45" 
			 value="<%= (mra_recordVO==null)? "" : mra_recordVO.getMpo_no()%>" /></td>
	</tr>
	

	<tr>
		<td>檢舉內容:</td>
		<td><textarea name="rep_content" 
			 value="<%= (mra_recordVO==null)? "" : mra_recordVO.getRep_content()%>" ></textarea></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>



</html>
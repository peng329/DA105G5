<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.eqpic.model.*"%>

<%
  EqpicVO eqpicVO = (EqpicVO) request.getAttribute("eqpicVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>圖片新增 - addEqpic.jsp</title>

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
		 <h3>圖片資料新增 - addEqpic.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>潛店編號:</td>
		<td><input type="TEXT" name="ds_no" size="45" 
			 value="<%= (eqpicVO==null)? "DS0001" : eqpicVO.getDs_no()%>" /></td>
	</tr>
	<tr>
		<td>裝備流水號:</td>
		<td><input type="TEXT" name="ep_seq" size="45"
			 value="<%= (eqpicVO==null)? "EP0001" : eqpicVO.getEp_seq()%>" /></td>
	</tr>
	<tr>
		<td>圖片:</td>
		<td>
        <input type="file" name="epic" id="ppic">
        <img id="img" width="150px" height="150px">
        </td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>




<script>
ppic.addEventListener('change',function(){
	 var file =this.files[0];
	img.src=URL.createObjectURL(file);
});


</script>
<%session.setAttribute("A", "A"); %>
</body>
</html>
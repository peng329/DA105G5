<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mrds_record.model.*"%>



<%
  Mrds_recordVO mrds_recordVO = (Mrds_recordVO) request.getAttribute("mrds_recordVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>�|�����|�穱�s�W - addMrds_record.jsp</title>

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
		 <h3>�|�����|�穱�s�W - addMrds_record.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="80" height="80" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��Ʒs�W:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/mrds_record/mrds_record.do" name="form1">
<table>
			
	<tr>
		<td>�|���s��:</td>
		<td><input type="TEXT" name="mem_no" size="45" 
			 value="<%= (mrds_recordVO==null)? "" : mrds_recordVO.getMem_no()%>" /></td>
	</tr>
	<tr>
		<td>�穱�s��:</td>
		<td><input type="TEXT" name="ds_no" size="45" 
			 value="<%= (mrds_recordVO==null)? "" : mrds_recordVO.getDs_no()%>" /></td>
	</tr>
	

	<tr>
		<td>���|���e:</td>
		<td><textarea name="rep_content" 
			 value="<%= (mrds_recordVO==null)? "" : mrds_recordVO.getRep_content()%>" ></textarea></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>



</html>
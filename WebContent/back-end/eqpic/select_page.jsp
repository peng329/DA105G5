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

<h3>��Ƭd��:</h3>
	
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
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
        <b>�п�J�穱�s�� (�pDS0001):</b>
        <input type="text" name="ds_no">
        <input type="hidden" name="action" value="getADS_All_Display">
        <input type="submit" value="�e�X">
    </FORM>
  </li>
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" >
        <b>�п�J�˳Ƭy����(�pEP0001):</b>
       <input type="text" name="ep_seq">       
        <input type="hidden" name="action" value="get_EPAL_Display">
        <input type="submit" value="�e�X">
    </FORM>
  </li>

</ul>


<h3>�Ϥ��޲z</h3>

<ul>
  <li><a href='addEpic.jsp'>Add</a> a new Equipment Picture.</li>
</ul>

</body>
</html>
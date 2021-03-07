<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%
Map<String,String> ds_no = new LinkedHashMap<String,String>();
ds_no.put("DS0001","潛水貨艙");
ds_no.put("DS0002","潛立方");
ds_no.put("DS0003","舒服潛水");
ds_no.put("DS0004","藍莎潛水中心");
ds_no.put("DS0005","資策會潛水中心");
pageContext.setAttribute("ds_no", ds_no);
%>
<html>
<head>
<title>IBM Rent Order</title>

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
   <tr><td><h3>IBM ROselect_page</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Rent Order</p>

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

  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ROrder/rorder.do" >
        <b>請輸入會員編號:</b>
        <input type="text" name="mem_no">
        <input type="hidden" name="action" value="getAMem_All_RO">
        <input type="submit" value="送出">
    </FORM>
  </li>

    <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ROrder/rorder.do" >
       <b>請輸入潛店編號:</b>
       <select size="1" name="ds_no">
         <c:forEach var="ds_no" items="${ds_no}" > 
          <option value="${ds_no.getKey()}">${ds_no.getValue()}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getADS_All_RO">
       <input type="submit" value="送出">
    </FORM>
  </li>

</ul>

</body>
</html>
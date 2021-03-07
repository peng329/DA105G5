<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*,com.equip.model.*"%>

<%
Map<String,String> ds_no = new LinkedHashMap<String,String>();
ds_no.put("DS0001","潛水貨艙");
ds_no.put("DS0002","潛立方");
ds_no.put("DS0003","舒服潛水");
ds_no.put("DS0004","藍莎潛水中心");
ds_no.put("DS0005","資策會潛水中心");

pageContext.setAttribute("ds_no", ds_no);

%>
 <jsp:useBean id="equipSvc" scope="page" class="com.equip.model.EquipService" />
 <jsp:useBean id="equipCSvc" scope="page" class="com.equipc.model.EquipCService" />

<html>
<head>
<title>IBM Equip : Home</title>

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
   <tr><td><h3>IBM Equip</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Equip: Home</p>

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
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Equip/equip.do" >
        <b>輸入裝備流水號 (如EP0001):</b>
        <input type="text" name="ep_seq">
        <input type="hidden" name="action" value="getOne_For_Eq">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Equip/equip.do" >
        <b>請選擇潛店</b>
 		<select size="1" name="ds_no">
        <c:forEach var="ds_no" items="${ds_no}">
        <option value="${ds_no.getKey()}">${ds_no.getValue()}
        </c:forEach>
        </select>
       <input type="hidden" name="action" value="getOne_DS_EQAll">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Equip/equip.do" >
        <b>複合查詢</b>
        <br>
        <b>潛店查詢</b>
        <input type="text" name="ds_name">
        <br>
        <b>裝備分類查詢</b>
 		<select size="1" name="epc_no">
 		<option value="">
        <c:forEach var="equipCVO" items="${equipCSvc.all}">
        <option value="${equipCVO.epc_no}">${equipCVO.epc_name}
        </c:forEach>
        </select>
        <br>
        <b>裝備查詢</b>
        <input type="text" name="ep_name">
       <input type="hidden" name="action" value="Equipment_ByCompositeQuery">
        <input type="submit" value="送出">
    </FORM>
  </li>
</ul>


<h3>裝備管理</h3>

<ul>
  <li><a href='addEquipByBUP.jsp'>Add</a> a new Equip(BUP)</li>
</ul>

</body>
</html>
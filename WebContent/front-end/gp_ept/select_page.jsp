<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>GP_EPT: Home</title>

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
   <tr><td><h3>GP_EPT: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for GP_EPT: Home</p>

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
  <li><a href='listAllGp_Ept.jsp'>List</a> all listAllGp_Ept    <h4>(byDAO).         </h4></li>
<!--   <li><a href='gp_ept.do?action=getAll'> List</a> all Gp_Ept   <h4>(getFromSession).</h4> <br><br><br></li> -->
  <li><a href='addGp_Ept.jsp'>Add</a> add   <h4>(byDAO).         </h4></li>
  
  <%-- 揪團裝備明細編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="gp_ept.do" >
        <b>輸入揪團裝備明細編號 :</b>
        <input type="text" name="gp_ept_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出" >                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
  <%-- 揪團編號--%>
  
  <li>
    <FORM METHOD="post" ACTION="gp_ept.do" >
        <b>輸入揪團編號 :</b>
        <input type="text" name="act_list_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
<%-- 會員編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="gp_ept.do" name="form1">
        <b>輸入團主會員編號 :</b>
        <input type="text" name="mem_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="button" value="送出" >  					<h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
 
<%-- 裝備分類編號--%>
  
  <li>
    <FORM METHOD="post" ACTION="gp_ept.do" >
        <b>輸入裝備分類編號:</b>
        <input type="text" name="epc_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  <%-- 數量 --%>
  <li>
    <FORM METHOD="post" ACTION="gp_ept.do" >
        <b>數量 :</b>
        <input type="text" name="gp_t_num">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  <%-- SIZE --%>
  <li>
    <FORM METHOD="post" ACTION="gp_ept.do" >
        <b>SIZE :</b>
        <input type="text" name="gp_t_size">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
</ul>



</body>
</html>
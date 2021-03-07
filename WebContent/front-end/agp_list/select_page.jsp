<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>AGP_LIST: Home</title>

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
   <tr><td><h3>AGP_LIST: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for AGP_LIST: Home</p>

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
  <li><a href='listAllAgp_List.jsp'>List</a> all listAllAgp_List    <h4>(byDAO).         </h4></li>
<!--   <li><a href='agp_list.do?action=getAll'> List</a> all AGP_LIST    <h4>(getFromSession).</h4> <br><br><br></li> -->
  <li><a href='addAgp_List.jsp'>Add</a> add   <h4>(byDAO).         </h4></li>
  
  <%-- 揪團編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="agp_list.do" >
        <b>輸入揪團編號 :</b>
        <input type="text" name="act_list_no">
        <br>
         <b>輸入會員編號 :</b>
        <input type="text" name="mem_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出" >                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
  <%-- 會員編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="agp_list.do" name="form1">
        <b>輸入會員編號 :</b>
        <input type="text" name="mem_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="button" value="送出" >  					<h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
<%-- 潛水證號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="agp_list.do" >
        <b>輸入潛水證號 :</b>
        <input type="text" name="mem_lic">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
 
<%-- 報名人數 --%>
  
  <li>
    <FORM METHOD="post" ACTION="agp_list.do" >
        <b>輸入報名人數:</b>
        <input type="text" name="act_num">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
<%-- 潛水證圖片 --%>
  <li>
    <FORM METHOD="post" ACTION="agp_list.do" >
        <b>輸入潛水證圖片 :</b>
        <input type="text" name="mem_lic_pic">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 報名狀態 --%>
  <li>
    <FORM METHOD="post" ACTION="agp_list.do" >
        <b>輸入報名狀態 :</b>
        <input type="text" name="agp_state">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
</ul>



</body>
</html>
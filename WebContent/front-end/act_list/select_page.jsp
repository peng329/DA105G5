<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>ACT_LIST: Home</title>

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
   <tr><td><h3>ACT_LIST: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for ACT_LIST: Home</p>

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
  <li><a href='listAllAct_List.jsp'>List</a> all listAllAct_List    <h4>(byDAO).         </h4></li>
<!--   <li><a href='act_list.do?action=getAll'> List</a> all ACT_LIST    <h4>(getFromSession).</h4> <br><br><br></li> -->
  <li><a href='addAct_List.jsp'>Add</a> add   <h4>(byDAO).         </h4></li>
  
  <%-- 揪團編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入揪團編號 :</b>
        <input type="text" name="act_list_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出" >                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
  <%-- 團主會員編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="act_list.do" name="form1">
        <b>輸入團主會員編號 :</b>
        <input type="text" name="mem_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="button" value="送出" >  					<h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
<%-- 揪團編號 --%>
  
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入揪團編號 :</b>
        <input type="text" name="dp_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
 
<%-- 報名起始日 --%>
  
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>報名起始日:</b>
        <input type="text" name="start_date">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  <%-- 報名截止日 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入報名截止日 :</b>
        <input type="text" name="dual_date">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  <%-- 出團日--%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入出團日 :</b>
        <input type="text" name="action_date">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 開團狀態 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入開團狀態 :</b>
        <input type="text" name="act_state">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 揪團地點 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入揪團地點 :</b>
        <input type="text" name="locale">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 參加人上限 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入參加人上限 :</b>
        <input type="text" name="act_max">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 參加人下限 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入參加人下限 :</b>
        <input type="text" name="act_min">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 揪團圖片 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入揪團圖片 :</b>
        <input type="text" name="gp_pic">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 揪團介紹 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入揪團介紹 :</b>
        <input type="text" name="gp_info">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
<%-- 天數 --%>
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入天數 :</b>
        <input type="text" name="gp_days">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  <%-- 團名 --%>
  
  <li>
    <FORM METHOD="post" ACTION="act_list.do" >
        <b>輸入團名 :</b>
        <input type="text" name="act_list_name">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出" >                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
</ul>



</body>
</html>
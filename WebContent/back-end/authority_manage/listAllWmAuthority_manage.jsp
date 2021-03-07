<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.authority_manage.model.*"%>
<%@ page import="com.func.model.*"%>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<% 
	List<String[]> list = (List<String[]>)request.getAttribute("list");
    pageContext.setAttribute("list",list);
    
    String wm_no = (String)request.getAttribute("wm_no");
    pageContext.setAttribute("wm_no",wm_no);
    
%>


<html>
<head>
<title>員工所有權限資料 - listAllAuthority_manage.jsp</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Bubble up index</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <link href="../../css/style.css" rel="stylesheet" type="text/css">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../css/memcenter.css">
    <script src="../../kit.fontawesome.com/79fc4c3e25.js"></script>
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
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
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>員工權限資料 - listAllAuthority_manage.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/authority_manage/authority_manage.do" style="margin-bottom: 0px;">
<c:forEach var="listArry" items="${list}">
	<div class="custom-control custom-checkbox">
      <input type="checkbox" class="custom-control-input" id="${listArry[1]}" name="fc_no" value="${listArry[1]}" ${(listArry[0] eq '0')?'':'checked'}/>
      <label class="custom-control-label" for="${listArry[1]}">${listArry[2]}</label>
	</div>
	<script type="text/javascript">
	
	$('#${listArry[1]}').prop('${listArry[2]}', true);
		$(document).ready(function() {
		    $("#${listArry[1]}").click(function(){
		    	if(this.checked) {
		    		var addOrDelete = "insert";		    		
		    	}else{
		    		var addOrDelete = "delete";		    		
		    	}        
		        $.ajax({
					url:"<%=request.getContextPath()%>/back-end/authority_manage/authority_manage.do",
					data:{wm_no:"${wm_no}", fc_no:"${listArry[1]}", action:addOrDelete},
					type: "POST",
					dataType: "json",
					success: function(data){	
					}
				});
			});	   
		    });
		</script>
	  <br>
</c:forEach>
      <input type="submit" value="修改"/>
</form>




</body>
</html>
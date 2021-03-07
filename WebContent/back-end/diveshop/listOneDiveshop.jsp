<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*" %>
<% DiveshopVO diveshopVO = (DiveshopVO) request.getAttribute("diveshopVO"); %>
<jsp:useBean id="dspicSvc" scope="page" class="com.dspic.model.DspicService"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>潛店資料 - listOneDiveshop.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;"C:/Users/Java/Downloads/商品詳情/diveshop_index.html"
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
  img{
  	width:150px;
  	height:150px;
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
<body>
<table id="table-1">
	<tr><td>
		<h3>潛店資料 - listOneDiveshop.jsp</h3>
		<h4><a href="<%= request.getContextPath()%>/diveshop_select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>潛店編號</th>
		<th>潛店名稱</th>
		<th>營業登記證</th>
		<th>潛店電話</th>
		<th>潛店地址</th>
		<th>潛店帳號</th>
		<th>潛店密碼</th>
		<th>潛店信箱</th>
		<th>潛店介紹</th>
		<th>緯經度</th>
		<th>狀態</th>
		<th>潛店照片</th>
		<th>潛店評分</th>
		<th>潛店檢舉次數</th>
	</tr>
	<tr>
		<td><%=diveshopVO.getDs_no()%></td>
		<td><%=diveshopVO.getDs_name()%></td>
		<td><%=diveshopVO.getBrcid()%></td>
		<td><%=diveshopVO.getTel()%></td>
		<td><%=diveshopVO.getAddress()%></td>
		<td><%=diveshopVO.getDsaccount()%></td>
		<td><%=diveshopVO.getDspaw()%></td>
		<td><%=diveshopVO.getDsmail()%></td>
		<td><%=diveshopVO.getDsinfo()%></td>
		<td><%=diveshopVO.getDs_latlng()%></td>
		<td><%=diveshopVO.getDs_state()%></td>
		<td>		  
			<c:forEach var="index" begin="0" end="${dspicSvc.getDspicByDsno(diveshopVO.ds_no).size()-1}" >
					<img style="width=150px;height=150px;" src="<%=request.getContextPath()%>/dspic/DBGifReader4.do?ds_no=${diveshopVO.ds_no}&index=${index}"/>				
			</c:forEach>
		</td>
		<td><%=diveshopVO.getDs_ascore()%></td>
		<td><%=diveshopVO.getDs_rep_no()%></td>
	</tr>
</table>
</body>
</html>
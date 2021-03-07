<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.diveshop.model.*" %>
<%@ page import="com.dspic.model.*" %>

<%
DiveshopService diveshopSvc = new DiveshopService();
List<DiveshopVO> list = diveshopSvc.getAll();
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>潛店資料 - List Selected Diveshop.jsp</title>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCvS_7XU7xlsw0cWtSRBGBe6dPblebLkHM&callback=initMap" async defer></script>
    
    <script>
      var map;
      function initMap() {
        map = new google.maps.Map(document.getElementById('map-canvas'), {
          center: {lat: -34.397, lng: 150.644},
          zoom: 8
        });
      }
    </script>

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
  #map-canvas{
  border:solid 2px red;
  }
</style>
</head>
<body onload="initialize()">
<table id="table-1">
	<tr><td>
		<h3>潛店資料 - List Selected Diveshop.jsp</h3>
		<h4><a href="<%= request.getContextPath()%>/diveshop_select_page.jsp">回首頁</a></h4>
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

<div id="map-canvas" style="width: 500px; height: 500px"></div>

</body>
</html>
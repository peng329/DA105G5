<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dspic.model.*"%>
<%@ page import="com.diveshop.model.*"%>
<% DspicVO dspicVO = (DspicVO) request.getAttribute("dspicVO"); %>
 <% 
	DiveshopService diveshopSvc = new DiveshopService();
 	DiveshopVO diveshopVO = (DiveshopVO)request.getAttribute("diveshopVO");
 %>

<!DOCTYPE html>
<html>
<head>

<title>潛店圖片 - list One Dive Shop picture</title>

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
  img{
  width:150px;
  height:150px;
  }
</style>
</head>
<body>
<table id="table-1">
	<tr><td>
		<h3>潛店圖片 - list One Dive Shop picture</h3>
		<h4><a href="<%=request.getContextPath()%>/dspic_select_page.jsp">Home</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>潛店圖片流水號</th>
		<th>潛店編號</th>
		<th>潛店圖片</th>
	</tr>
	<tr>
		<td><%=dspicVO.getDpic_seq()%></td>
		<td><%=dspicVO.getDs_no()%></td>
		<td><img src="<%= request.getContextPath() %>/dspic/DBGifReader4.do?dpic_seq=${dspicVO.dpic_seq}"/></td>
	</tr>
</table>
</body>
</html>
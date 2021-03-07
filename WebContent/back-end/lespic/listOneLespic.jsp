<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lespic.model.*"%>
<%@ page import="com.lesson.model.*"%>
<% LespicVO lespicVO = (LespicVO) request.getAttribute("lespicVO"); %>
 <% 
	LessonService lessonSvc = new LessonService();
 	LessonVO lessonVO = (LessonVO)request.getAttribute("lessonVO");
 %>

<!DOCTYPE html>
<html>
<head>

<title>潛店課程圖片 - list One Dive Shop Lesson picture</title>

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
		<h3>潛店圖片 - list One Dive Shop Lesson picture</h3>
		<h4><a href="<%=request.getContextPath()%>/lespic_select_page.jsp">Home</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>課程圖片流水號</th>
		<th>課程編號</th>
		<th>課程圖片</th>
	</tr>
	<tr>
		<td><%=lespicVO.getLpic_seq()%></td>
		<td><%=lespicVO.getLes_no()%></td>
		<td><img src="<%= request.getContextPath() %>/dspic/DBGifReader4.do?lpic_seq=${lespicVO.lpic_seq}"/></td>
	</tr>
</table>
</body>
</html>
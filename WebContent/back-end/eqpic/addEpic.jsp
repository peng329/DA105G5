<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.equip.model.*,com.eqpic.model.*,java.util.*"%>
 <jsp:useBean id="equipSvc" scope="page" class="com.equip.model.EquipService" />
 <% 
 	String ds_no =request.getParameter("ds_no");
 	String ep_seq =request.getParameter("ep_seq");
 	EquipVO equipVO =equipSvc.getOneEq(ep_seq);
 	pageContext.setAttribute("ds_no", ds_no);
 	pageContext.setAttribute("ep_seq", ep_seq);
 	pageContext.setAttribute("equipVO", equipVO);
 %>
 
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsheader.jsp"%>
 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>圖片新增 - addEqpic.jsp</title>

<style>
@import url('https://fonts.googleapis.com/css?family=Patua+One&display=swap');
@import url('https://fonts.googleapis.com/css?family=Courgette|Noto+Sans+TC:500|Noto+Serif+TC:900|Patua+One&display=swap');
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+TC:500|Noto+Serif+TC:900&display=swap');
@import url('https://fonts.googleapis.com/css?family=Noto+Serif+TC:900&display=swap');

#right-panel{
background-color:white;
}
.content {
    padding: 0.875em;
}
body{
overflow-x: hidden;
display:block;
}

h3{
}
</style>

</head>
<body bgcolor='white'>
<h3>${diveshopVO.ds_name} - 新增圖片</h3>
<h3>裝備名稱: ${equipVO.ep_name}</h3>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Eqpic/eqpic.do" name="form1" enctype="multipart/form-data">
<table>
			<tr>
				<td>圖片:</td>
				<td>
		           <input type="file" name="epic" onclick="previewImage()" multiple="multiple" id="ppic">
		        </td>
			</tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="hidden" name="ds_no" value="${ds_no}">
<input type="hidden" name="ep_seq" value="${ep_seq}">
<input type="submit" value="送出新增"></FORM>
<div id="holder"></div>
<div><a href="<%=request.getContextPath()%>/back-end/equip/listAllDSEquipByDs_no.jsp?ds_no=${diveshopVO.ds_no}"style="text-decoration:none;color:blue;">回前頁</a></div>
<script type="text/javascript">
var filereader_support = typeof FileReader != 'undefined';

if (!filereader_support) {
	alert("No FileReader support");
}

acceptedTypes = {
		'image/png' : true,
		'image/jpeg' : true,
		'image/gif' : true
};

function previewImage() {
	var upfile = document.getElementById("ppic");
	upfile.addEventListener("change", function(event) {
		var files = event.target.files || event.dataTransfer.files;
		for (var i = 0; i < files.length; i++) {
			previewfile(files[i])
		}
	}, false);
}

function previewfile(file) {
	if (filereader_support === true && acceptedTypes[file.type] === true) {
		var reader = new FileReader();
		reader.onload = function(event) {
			var image = new Image();
			image.src = event.target.result;
			image.width = 100;
			holder.appendChild(image);
		};
		reader.readAsDataURL(file);
	} else {
		holder.innerHTML += "<p>" + "filename: <strong>" + file.name
				+ "</strong><br>" + "ContentTyp: <strong>" + file.type
				+ "</strong><br>" + "size: <strong>" + file.size
				+ "</strong> bytes</p>";
	}
}
</script>
	
<%session.setAttribute("A", "A"); %>
</body>
<%@include file="/back-end/diveshop-master/dsheaderfooter/dsfooter.jsp"%>
</html>
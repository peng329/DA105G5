<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mat_record.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>


<%
//MemVO memVO = (MemVO)request.getAttribute("memVO"); //MemServlet.java(Concroller), 存入req的memVO物件

MemService memSrc = new MemService();
MemVO memVO = (MemVO)session.getAttribute("memVO"); //


List<Mat_recordVO> list = (List<Mat_recordVO>)request.getAttribute("list");
pageContext.setAttribute("list",list); 

Mat_recordService mat_recordSvc = new Mat_recordService();
pageContext.setAttribute("mat_recordSvc",mat_recordSvc);
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Bubble up index</title>
<script src="<%=request.getContextPath()%>/js/jquery-3.4.1.slim.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" >
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js" ></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/memcenter.css">
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
  
/* 去除點擊愛心後的邊框 */
.btn:focus, .btn:active:focus, .btn.active:focus{
    outline:none;
    box-shadow:none;
}

</style>
</head>


                    <div class="col-md-8 grid-margin stretch-card">
		            <div class="row">
		                <div class="col-md-12">
<table>
	<tr>
		
		<th>文章編號</th>
		<th>追蹤</th>
	</tr>
	
		<tr>		
			<td>MP000001</td>
			<td>
			<div class="top_add_fav_div mx-auto">
					<span class="top_add_fav_span">
<%-- 						<form METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/mat_record/mat_record.do" style="margin-bottom: 0px;"> --%>
 
			     <input type="hidden" name="mem_no"  value="M000001">
			      <input type="hidden" name="mpo_no"  value="MP000001">
									
									<c:if test='${ mat_recordSvc.getOneMat_record(memVO.mem_no, "MP000001") == null}'>
										<button id="fllow" type="submit" class="btn top_add_fav btn-outline-none"  value="insert" >
										<i class="fa fa-heart-o" aria-hidden="true" style="font-size: 28px; color: red;" id="heart"></i>
										</button>
									</c:if>
									<c:if test='${ mat_recordSvc.getOneMat_record(memVO.mem_no, "MP000001") != null}'>
										<button id="fllow" type="submit" class="btn top_add_fav btn-outline-none"  value="delete" >
										<i class="fa fa-heart" aria-hidden="true" style="font-size: 28px; color: red;" id="heart"></i>
										</button>
									</c:if>
									
					<input type="hidden" name="mem_no"  value="M000001">
			        <input type="hidden" name="mpo_no"  value="MP000001">
			        
			        	<script type="text/javascript">
			        	
		$(document).ready(function() {
		    $("#fllow").click(function(){
		    	
		    	if($("#fllow").val()==="insert") {
// 		    		$("#fllow").htmlText("");
// 		    		$("#fllow").htmlText("<i class='fa fa-heart' style='font-size: 28px; color: red;'></i>");
					//$(".fa fa-heart-o")
		    		
		    		$("#heart").attr('class','fa fa-heart');
		    		$("#fllow").val("delete");
		    		
		    		var addOrDelete = "ajaxInsert";
		    	}else{
		    		//$("#fllow").htmlText("");
				    //$("#fllow").htmlText("<i class='fa fa-heart-o' style='font-size: 28px; color: red'></i>");
		    		
		    		$("#heart").attr('class','fa fa-heart-o');
		    		$("#fllow").val("insert");
		    		var addOrDelete = "ajaxDelete";
		    	} 
		    	
// 		    	if(this.checked) {
// 		    		var addOrDelete = "insert";		    		
// 		    	}else{
// 		    		var addOrDelete = "delete";		    		
// 		    	}        
		        $.ajax({
					url:"<%=request.getContextPath()%>/front-end/mat_record/mat_record.do",
					data:{mem_no:"${memVO.mem_no}", mpo_no:"MP000001", action:addOrDelete},
					type: "POST",
					dataType: "json",
					success: function(data){	
					}
				});
			});	   
		    });
		</script>
			        
<!-- 						</form> -->
					</span>
				</div>
			</td>
		</tr>
		
		
		
		
		

</table>


                    </div>
                </div>
            </div>
        </div>    
           </div>
        </div>  


	<footer class="navbar-fixed-bottom">
		<div class="container" id='fot'>
			<p>
				copyright 2020 BUBBLE UP<br> Take a deep breath<br>
				Contact us : <br>
			</p>
		</div>
	</footer>
</body>




</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.agp_list.model.*"%>
<%@ page import="com.mem.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
Agp_List_JDBCDAO dao = new Agp_List_JDBCDAO();
List<Agp_List_VO> list = dao.getAll();
pageContext.setAttribute("list",list);

Set<Agp_List_VO> Join_management1 =(Set) request.getAttribute("Join_management1");
pageContext.setAttribute("Join_management1",Join_management1);


MemVO mem_vo = (MemVO)session.getAttribute("memVO");

%>


<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->


<style>


	.butrow {
		margin-top: 30px;
	}
	
	th{ 
 	border: 2px solid #ccc; 
 	padding:10px;
 	text-align:center;
  	border-radius:10px; 
 	} 
	
 	td{ 
 	border: 2px solid #ccc; 
 	padding:10px;
 	text-align:center;
 	} 
</style>
<body bgcolor='white'>
<!-- -------------------------------------------------------- -->
	<div class="container-fluid">
		

		<div class="row clearfix">
			<div class="col-lg-2 column">
			</div>
			<!--------------------------------揪團按鈕列---------------------------------------------->
			<%@ include file="grouplistbtn.file" %>

			<!---------------------------------------------------------------------------------------->

			<div class="col-lg-6 column">
			
				<div class="page-header" style="text-align:center;">
					<h1>
						歷史參團
					</h1>
					<hr>
				</div>
			<div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <table id="bootstrap-data-table" class="table table-striped table-bordered">
                                       <thead>                                       
											<tr>
												<th>揪團編號</th>
												<th>會員編號</th>
												<th>潛水證號</th>
												<th>報名人數</th>
												<th>潛水證圖片</th>
												<th>報名狀態</th>
										
											</tr>
										 </thead>
									<c:forEach var="agp_list_vo" items="${Join_management1}" >
										<tr>
											<td>${agp_list_vo.act_list_no}</td>
											<td>${agp_list_vo.mem_no}</td>
											<td>${agp_list_vo.mem_lic}</td>
											<td>${agp_list_vo.act_num}</td>
											<td>
											<img src="<%=request.getContextPath()%>/front-end/agp_list/DBGifReader?act_list_no=${agp_list_vo.act_list_no}&mem_no=${agp_list_vo.mem_no}"  width=100 heigh=100 >
											</td>
											<td>${agp_list_vo.agp_state}</td>
											
								
										</tr>
									</c:forEach>
								</table>		 
							 </div>
                        </div>
                    </div>
                </div>
			
		
			
			</div>
			<div class="col-lg-2 column"></div>
		</div>
		<div class="row" style="height:10%;background:rgba(0,0,0,0);">
	</div>
	</div>

	<footer>
	</footer>







</body>
	<%@ include file="tablefile.file" %>
	<%@ include file="/HeaderFooter/footer.jsp" %>

</html>
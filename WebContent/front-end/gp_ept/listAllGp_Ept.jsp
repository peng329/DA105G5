<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp_ept.model.*"%>
<%@ page import="com.mem.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
Gp_Ept_JDBCDAO dao = new Gp_Ept_JDBCDAO();
List<Gp_Ept_VO> list = dao.getAll();
pageContext.setAttribute("list",list);

MemVO mem_vo = (MemVO)session.getAttribute("memVO");

%>


<html>
	<!-- ------------------------------head跟header跟footer----------------------------------------- -->	
		
			<%@ include file="/HeaderFooter/memHeader.jsp" %>

	<!-- ---------------------------------------------------------------------------------- -->


<style>
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

	.butrow {
		margin-top: 30px;
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
						團租管理
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
												<th>揪團裝備明細編號</th>
												<th>揪團編號</th>
												<th>會員編號</th>
												<th>裝備分類編號</th>
												<th>數量</th>
												<th>SIZE</th>
												<th>刪除</th>
											</tr>
										 </thead>
									<c:forEach var="gp_ept_vo" items="${list}" >
												<c:if test="${gp_ept_vo.mem_no==memVO.mem_no}">
										
										<tr>
											<td>${gp_ept_vo.gp_ept_no}</td>
											<td>${gp_ept_vo.act_list_no}</td>
											<td>${gp_ept_vo.mem_no}</td>
											<td>${gp_ept_vo.epc_no}</td>
											<td>${gp_ept_vo.gp_t_num}</td>
											<td>${gp_ept_vo.gp_t_size}</td>
											<td>
											  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/gp_ept/gp_ept.do" style="margin-bottom: 0px;">
											     <input type="submit" class="btn btn-outline-info" value="刪除">
											     <input type="hidden" name="gp_ept_no"  value="${gp_ept_vo.gp_ept_no}">
											     <input type="hidden" name="action" value="delete"></FORM>
											</td>
										</tr>
										</c:if>
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



</body>
	<%@ include file="tablefile.file" %>
	<%@ include file="/HeaderFooter/footer.jsp" %>
	

</html>
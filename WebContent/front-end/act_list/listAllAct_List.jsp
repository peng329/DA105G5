<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act_list.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.dive_point.model.*"%>



<%
	Act_List_JDBCDAO dao = new Act_List_JDBCDAO();
    List<Act_List_VO> list = dao.getAll();
    pageContext.setAttribute("list",list);
    
    Set<Act_List_VO> filter_act_list_vo =(Set) request.getAttribute("filter_act_list_vo");
    pageContext.setAttribute("filter_act_list_vo",filter_act_list_vo);

    MemVO mem_vo = (MemVO)session.getAttribute("memVO");

    DpService dpSvc = new DpService();
    List<DpVO> dplist = dpSvc.getAll();
    pageContext.setAttribute("dplist", dplist);
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

<body>

	<div class="container-fluid">
		<div class="row clearfix">
		
<!-- --------------------------row-左2 ------------------------------------------------ -->
			
			<div class="col-lg-2 column"></div>		
			
<!------------------------row-左2-左2---------------------------------------------------------------------------------->
<!----------------------------揪團按鈕列---------------------------------------------->

			<%@ include file="grouplistbtn.file" %>
			
<!-------------------------------row-左2-左2-左8------------------------------------------------------------------>

			<div class="col-lg-8 column">
				<div class="page-header" style="text-align:center;">
					<h1>
						開團管理
					</h1>
					<hr>
				</div>

<!------------------------------------------------------------------------------------------------->
				<div class="row">

                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body">
                                <table id="bootstrap-data-table" class="table table-striped table-bordered">
                                       <thead>
		                                    <tr>
												<th>揪團編號</th>
												<th>團名</th>
<!-- 												<th>團主會員編號</th> -->
												<th>潛點編號</th>
												<th>報名起始日</th>
												<th>報名截止日</th>
												<th>出團日</th>
												<th>開團狀態</th>
												<th>揪團地點</th>
												<th>參加人上限</th>
												<th>參加人下限</th>
												<th>揪團圖片</th>
												<th>揪團介紹</th>
												<th>天數</th>
												<th>修改</th>
												<th>刪除</th>
											</tr>
                                   		 </thead>
											<c:forEach var="act_list_vo" items="${filter_act_list_vo}" >		
												<tr>
													<td>${act_list_vo.act_list_no}</td>
													<td>${act_list_vo.act_list_name}</td>
<%-- 													<td>${act_list_vo.mem_no}</td> --%>
													<td>${act_list_vo.dp_no}</td>
													<td>${act_list_vo.start_date}</td>
													<td>${act_list_vo.dual_date}</td>
													<td>${act_list_vo.action_date}</td>
													<td>${act_list_vo.act_state}</td>
													<td>${act_list_vo.locale}</td>
													<td>${act_list_vo.act_max}</td>
													<td>${act_list_vo.act_min}</td>
													<td>
													<img src="<%=request.getContextPath()%>/front-end/act_list/DBGifReader?act_list_no=${act_list_vo.act_list_no}" width=100 heigh=100>
													</td>
													<td>${act_list_vo.gp_info}</td>
													<td>${act_list_vo.gp_days}</td>
													<td>
													  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/act_list/act_list.do" style="margin-bottom: 0px;">
													     <input type="submit" value="修改" class="btn btn-outline-info">
													     <input type="hidden" name="act_list_no"  value="${act_list_vo.act_list_no}">
													     <input type="hidden" name="action"	value="getOne_For_Update">
													     </FORM>
													</td>
													<td>
													  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/act_list/act_list.do" style="margin-bottom: 0px;">
													     <input type="submit" class="btn btn-outline-info" value="刪除">
													     <input type="hidden" name="act_list_no"  value="${act_list_vo.act_list_no}">
													     <input type="hidden" name="action" value="delete"></FORM>
													</td>
												</tr>
										
									</c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>


                </div>
<!------------------------------------------------------------------------------------------------->

			</div>
			

		</div>
		<div class="row" style="height:10%;background:rgba(0,0,0,0);">
	</div>
	</div>


</body>

	<%@ include file="tablefile.file" %>
	<%@ include file="/HeaderFooter/footer.jsp" %>
</html>
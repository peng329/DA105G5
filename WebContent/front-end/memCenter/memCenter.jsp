<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>


<%
	//MemVO memVO = (MemVO)request.getAttribute("memVO"); //MemServlet.java(Concroller), 存入req的memVO物件
	MemVO memVO = (MemVO) session.getAttribute("memVO"); //
	String mem_no = memVO.getMem_no();

	//從請求取得圖片物件
	byte[] mem_pic2 = (byte[]) session.getAttribute("mem_pic2");
	//當從請求得來圖片的物件為空時，拿會員1的圖片為預設圖片，並給屬性meme_pic2
	if (mem_pic2 == null) {
		mem_pic2 = memVO.getMem_pic();

	}

	//將一定有內容的圖片物件給請求，不是預設圖，就是上次送請求的圖
	session.setAttribute("mem_pic2", mem_pic2);

	String imgSrc = (String) session.getAttribute("imgSrc");
	session.setAttribute("imgSrc", imgSrc);
	if (imgSrc == null) {
		imgSrc = request.getContextPath() + "/DBGifReader2?mem_no=" + memVO.getMem_no();
	}
	session.setAttribute("imgSrc", imgSrc);
%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>





</head>



<body bgcolor='white'>

<%@ include file="/HeaderFooter/memHeader.jsp"%>

  
    <!-- 會員頁面 -->
     <div class="view intro-2">
        <div class="full-bg-img">
            <div class="mask rgba-black-light flex-center">   
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-4">
	<!-- 側邊攔 -->
	<%@ include file="/front-end/memCenter/memSidebar.file"%>

	


	<div class="col-md-8 grid-margin stretch-card">
		<div class="card">
			<div class="card-body">
				<div class="row">
					<div class="col-md-12">
						<h4>Your Profile</h4>
						<hr>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<form METHOD="post"
							ACTION="<%=request.getContextPath()%>/back-end/mem/mem.do"
							name="form1" enctype="multipart/form-data">
							<div class="form-group row">
								<label for="file"  class="col-4 col-form-label">大頭貼</label>
								<div class="col-5">
									<div>
										<label class="btn btn-info"> <i
											class="fa fa-upload fa-2x" aria-hidden="true"></i>&nbsp;&nbsp;修改個人頭貼
											<input type="file" class="custom-file-input" name="mem_pic"
											size="45" id="mem_pic" accept="image/*"> <img
											src="<%=imgSrc%>" name="mem_pic" id="img1" style="width: 50%">
										</label>
									</div>
								</div>
								<div class="col-3"></div>
							</div>
							<div class="form-group row">
								<label for="username" class="col-4 col-form-label">帳號</label>
								<div class="col-8">
									<%=memVO.getMem_id()%>
								</div>
							</div>

							<div class="form-group row">
								<label for="text" class="col-4 col-form-label">密碼</label>
								<div class="col-8">
									<input type="PASSWORD" name="mem_psw"
										value="<%=memVO.getMem_psw()%>" class="form-control here">
								</div>
							</div>
							<div class="form-group row">
								<label for="lastname" class="col-4 col-form-label">暱稱</label>
								<div class="col-8">
									<input type="TEXT" name="mem_name"
										value="<%=memVO.getMem_name()%>" class="form-control here">
								</div>
							</div>

							<div class="form-group row">
								<label for="select" class="col-4 col-form-label">性別</label>
								<div class="col-8">
									<select size="1" name="mem_sex" class="custom-select">
										<option value="1"
											<%if (memVO.getMem_sex() == 1)out.println("selected");%>>男


										
										<option value="2"
											<%if (memVO.getMem_sex() == 2)out.println("selected");%>>女</select>
								</div>
							</div>
							<div class="form-group row">
								<label for="date" class="col-4 col-form-label">生日</label>
								<div class="col-8">
									<input name="mem_bd" id="f_date1" type="text"
										class="form-control here">
								</div>
							</div>

							<div class="form-group row">
								<label for="email" class="col-4 col-form-label">Email</label>
								<div class="col-8">
									<input type="TEXT" name="mem_mail"
										value="<%=memVO.getMem_mail()%>" class="form-control here"
										required="required"></div>
							</div>
							<div class="form-group row">
								<label for="phone" class="col-4 col-form-label">手機</label>
								<div class="col-8">
									<input type="TEXT" name="mem_phone"
										value="<%=memVO.getMem_phone()%>" class="form-control here">
								</div>
							</div>
							<div class="form-group row">
								<label for="add" class="col-4 col-form-label">地址</label>
								<div class="col-8">
									<input input name="mem_add" id="address"
										value="<%=memVO.getMem_add()%>"
										class="twaddress form-control here">
								</div>
							</div>


							<div class="form-group row">
								<div class="offset-4 col-8">
									<input type="hidden" name="action" value="update">
									<!-- 一些不能改的用hidden傳回 -->
									<input type="hidden" name="mem_no"
										value="<%=memVO.getMem_no()%>"> <input type="hidden"
										name="mem_id" value="<%=memVO.getMem_id()%>"> <input
										type="hidden" name="reg_time" value="<%=memVO.getReg_time()%>">
									<input type="hidden" name="mem_rep_no"
										value="<%=memVO.getMem_rep_no()%>"> <input
										type="hidden" name="mem_state"
										value="<%=memVO.getMem_state()%>"> <input
										type="hidden" name="imgSrc" value="<%=imgSrc%>" id="imgSrc">

									<button name="submit" type="submit" class="btn btn-primary">修改</button>
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>
	</div>


	</div>
	</div>
	</div>
	</div>
	</div>


	<!--------------------------------------------------底部--------------------------------------------->

	<%@ include file="/HeaderFooter/footer.jsp"%>

</body>


<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}


</style>




<script type="text/javascript">

<%@ include file="address.file" %>
	
	var upInput1 = document.getElementById("mem_pic");
	var img1 = document.getElementById("img1");
	var imgSrc = document.getElementById("imgSrc");
	
	
	function frontView(){
		var selectFile = upInput1.files[0];
	    var url = URL.createObjectURL(selectFile);
	    
		img1.src = url;
		imgSrc.value = url;	
		
	}
	
	function init(){	
		upInput1.addEventListener("change", frontView, false);	
	}
	
	window.onload = init;

        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=memVO.getMem_bd()%>',
	
	// value:   new Date(),
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});


</script>

</html>
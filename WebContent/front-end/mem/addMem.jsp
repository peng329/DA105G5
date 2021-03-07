<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.io.*"%>

<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");

	//下2句，用來取得會員1的VO物件
	MemService memService = new MemService();
	MemVO memVO2 = memService.getOneMem("M000001");
	
	//從請求取得圖片物件
	byte[] mem_pic2 = (byte[])session.getAttribute("mem_pic2");
	//當從請求得來圖片的物件為空時，拿會員1的圖片為預設圖片，並給屬性meme_pic2
	if(mem_pic2 == null){
		mem_pic2 = memVO2.getMem_pic();

	}
	
	//將一定有內容的圖片物件給請求，不是預設圖，就是上次送請求的圖
	session.setAttribute("mem_pic2", mem_pic2);
	
	String imgSrc = (String)session.getAttribute("imgSrc");
	
	if(imgSrc == null){
		//imgSrc = request.getContextPath() + "/DBGifReader2?mem_no=M000001";
		imgSrc = request.getContextPath() + "/images/default_profile.png";
		
	}
	session.setAttribute("imgSrc", imgSrc);

%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>

<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
        <!--引用jQuery-->
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
<!--引用SweetAlert2.js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>


</head>


<body>
	<!--====================導覽頁===========================  -->
	
	<%@ include file="/HeaderFooter/header.jsp"%>
	
<!------------------------------- 註冊頁要用的 ------------------------------------------>
<link href="<%=request.getContextPath()%>/front-end/mem/css/mem.style.css" rel="stylesheet" type="text/css">
	<!--====================註冊區塊===========================  -->
	<div class="container logging">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="card card-signin person-card my-6">
					<div class="card-body">
						<img id="img_sex" class="person-img" src="images/2x.gif">
						<h2 class="card-title text-center">
							<i class="fa fa-user-plus" aria-hidden="true"></i>&nbsp;註冊會員
						</h2>

						<hr>
						<div class="forgotpw" class="container">

							<%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs}">
								<font style="color: red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color: red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>

							<form class="form-signin" method="post"
								action="<%=request.getContextPath()%>/front-end/mem/mem.do"
								name="form1" enctype="multipart/form-data">
								<div class="row">
									<div class="col-md-6">
										<label for="inputaccount">會員帳號&nbsp;&nbsp;<font
											style="color: red">*</font></label> <font style="color: red"><span
											id="showthesame"></span></font>
											<span id="checkid" > </span>
										<p>
											<input type="text" style="ime-mode:active"  id="inputaccount" name="mem_id"
												class="form-control" placeholder="Account" autofocus
												value="<%= (memVO==null)? "" : memVO.getMem_id()%>" autocomplete="off">
												
										</p>
										<label for="pw">會員密碼&nbsp;&nbsp;<font
											style="color: red">*</font></label>
										<p>
											<input type="password" id="pw" name="pw" class="form-control"
												placeholder="Password" value="">
										</p>
										<label for="ckpw">確認密碼&nbsp;&nbsp;<font
											style="color: red">*</font></label> <span id="showError"></span>
										<p>
											<input type="password" name="mem_psw" id="ckpw"
												class="form-control" placeholder="Password"
												value="">
										</p>
										<label for="email">電子信箱&nbsp;&nbsp;<font
											style="color: red">*</font></label>
										<p>
											<input type="email" style="ime-mode:active" name="mem_mail" id="email"
												class="form-control" autofocus placeholder="E-mail"
												value="<%= (memVO==null)? "" : memVO.getMem_mail()%>">
										</p>
									</div>
									<div class="col-md-1"></div>

									<div class="col-md-5">
										<div id="showphoto"></div>
																				
										<label class="btn btn-info" id="imgLabel">
										<i class="fa fa-upload fa-2x" aria-hidden="true"></i>&nbsp;&nbsp;上傳個人頭貼
																			<br>
									
										<input type="file" class="custom-file-input" alert="點擊更換大頭貼" name="mem_pic" size="45" id="mem_pic"
											accept="image/*">
											<img src="<%=imgSrc%>"
											name="mem_pic" id="img1" style="width: 100%">
										</label>
									</div>
								</div>
						</div>
						<hr class="my-4">
						<div class="row">
							<div class="col-md-12">
								<p class="sex">
									<label for="male"><input type="radio" id="male"
										name="mem_sex" value="1" ${(memVO.mem_sex == 1)?'checked':''} checked>&nbsp;<i
										class="fa fa-male fa-2x" aria-hidden="true" checked></i>&nbsp;男生</label>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label for="female"><input
										type="radio" id="female" name="mem_sex" value="2"
										${(memVO.mem_sex == 2)?'checked':''}>&nbsp;<i
										class="fa fa-female fa-2x" aria-hidden="true"></i>&nbsp;女生</label>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</p>
								<label for="inputname">姓名&nbsp;&nbsp;<font
											style="color: red">*</font></label>
								<p>
									<input type="text" name="mem_name" id="inputname"
										class="form-control" placeholder="Name" autofocus
										value="<%= (memVO==null)? "" : memVO.getMem_name()%>">
								</p>
								<label for="inputDate">生日&nbsp;&nbsp;<font
											style="color: red">*</font></label>
								<p>
									<input  name="mem_bd" id="f_date1"
										class="form-control" autofocus>
								</p>
								<label for="cellphone">手機&nbsp;&nbsp;<font
											style="color: red">*</font></label>
								<p>
									<input type="tel" name="mem_phone" id="cellphone"
										pattern="[0-9]{6,20}" class="form-control" autofocus
										placeholder="Phone"
										value="<%= (memVO==null)? "" : memVO.getMem_phone()%>">
								</p>
								<div class="my-style-selector ">
										<label for="Address">地址&nbsp;&nbsp;<font
											style="color: red">*</font></label>
								<p>
			 						<td><input name="mem_add" id="address" value="<%= (memVO==null)? "桃園市平鎮區中央路300號" : memVO.getMem_add()%>"
			  							class="twaddress form-control" /></td>
									<span style="color: #cc2357; font-size: 18px"></span>	
								</p>
								</div>

								<div style="height: 50px"></div>
						<button type="button" id="magic" class="btn btn-primary">神奇小按鈕</button>
								<hr class="my-4">
								<input type="hidden" name="action" value="insert"> <input
									type="hidden" name="imgSrc" value="<%=imgSrc%>" id="imgSrc">
								<button class="btn btn-lg btn-info btn-block text-uppercase"
									type="submit" value="送出新增">送出</button>
							</div>
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
	</div>

       

<%--------------註冊成功後彈窗 -----------------------------------------%>
	<c:if test="${not empty message}">
	
		<script type="text/javascript">

                //alert範例
                swal("已寄出新密碼", "請至您的信箱查收新的登入密碼", "success").then(function(){
                    $(location).attr('href', '<%=request.getContextPath()%>/front-end/index.jsp');

                })


    	</script>
	</c:if>
	
	<!--------------------------------------------------底部--------------------------------------------->

<%@ include file="/HeaderFooter/footer.jsp" %>    
<!-------------- 註冊頁要用的，必須放header.jsp底下，否則會被header.jsp的css蓋掉某些想呈現的--------------->
<link href="<%=request.getContextPath()%>/css/mem.style.css" rel="stylesheet" type="text/css">	
</body>

<script type="text/javascript">

    
		//驗證帳號重複
		$(document).ready(function() {
		    $("#inputaccount").keyup(function(){
		        var addId = $("#inputaccount").val();     
		        $.ajax({
		        	//servlet網址
					url:"mem.do",
					//要傳送的資料，要map格式
					data:{mem_id:addId, action:'ajaxCheckId'},
					type: "POST",
					//資料格式為json，避免登入的session時間到
					dataType: "json",
					success: function(data){
						showFriends(data);
					}
				});
				
		       	//將servlet連線資料庫後
				function showFriends(data){
		       		//data為傳回來的json格式
					var checkAns = data["checkAns"];					
					if(addId.length === 0){		       			
		       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-times-circle' style='color:red'>&nbsp<font style='color:red;font-weight:Bold;font-size:small'>不能為空</font>");
					}else if(addId.length < 3){
		       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-times-circle' style='color:red'>&nbsp<font style='color:red;font-weight:Bold;font-size:small'>太短</font>");
					}else if(checkAns === "ok"){
		       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-check-circle-o fa-1x' style='color:green'>");
		       		}else{
		       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-times-circle' style='color:red'>&nbsp<font style='color:red;font-weight:Bold;font-size:small'>帳號已經存在!</font>");
		       		}
				}
			});	
		});
    
		//驗證帳號重複
		$(document).ready(function() {
		    $("#inputaccount").blur(function(){
		    	 var addId = $("#inputaccount").val();
			        $.ajax({
			        	//servlet網址
						url:"mem.do",
						//要傳送的資料，要map格式，第一組為編號，第二組為action的值，給servlet的if判斷
						data:{mem_id:addId, action:'ajaxCheckId'},
						type: "POST",
						//資料格式為json，避免登入的session時間到，跳出登陸視窗時，列印出登入原始碼文字在第二下拉選單
						dataType: "json",
						success: function(data){
							showFriends(data);
						}
					});
					
					function showFriends(data){
						var checkAns = data["checkAns"];
						if(addId.length === 0){
			       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-times-circle' style='color:red'></i>&nbsp<font style='color:red;font-weight:Bold;font-size:small'>不能為空</font>");
						}else if(addId.length < 3){
			       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-times-circle' style='color:red'>&nbsp<font style='color:red;font-weight:Bold;font-size:small'>太短</font>");
						}else if(checkAns === "ok"){
			       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-check-circle-o fa-1x' style='color:green'>");
			       		}else{
			       			$("#checkid").html("&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-times-circle' style='color:red'>&nbsp<font style='color:red;font-weight:Bold;font-size:small'>帳號已經存在!</font>");
			       		}
					}
				});	
			 });

    var upInput1 = document.getElementById("mem_pic");
    var img1 = document.getElementById("img1");
    var imgSrc = document.getElementById("imgSrc");

    function frontView() {
        var selectFile = upInput1.files[0];
        var url = URL.createObjectURL(selectFile);
        img1.src = url;
        imgSrc.value = url;
    }
   document.getElementById("mem_pic").onchange = frontView;
    

	//前端密碼和確認密碼CK
	function ckthesame(e) {
		let pw = document.getElementById("pw");
		let ckwp = document.getElementById("ckpw");
		let showError = document.getElementById("showError");

		if (pw.value === ckwp.value && (ckwp.value != ""&&pw.value!="")) {
			
			showError.innerHTML = "<font style='color:green;font-weight:Bold;font-size:small'>確認OK!</font>";
		}else if(ckwp.value === ""&&pw.value===""){
			showError.innerHTML = "";
		}else{
			
			showError.innerHTML = "<font style='color:red;font-weight:Bold;font-size:small'>密碼不一致</font>";
		    
		}
	}
	document.getElementById("ckpw").onkeyup = ckthesame;
	document.getElementById("pw").onchange = ckthesame;
	
	
	//神奇小按鈕
	$(document).ready(function(){
		$('#magic').click(function(){
			$('#mem_id').val('andy');
			$('#pw').val('123456');
			$('#ckpw').val('123456');
			$('#email').val('da105g5@gmail.com');
			$('#inputname').val('peng');
			$('#cellphone').val('0911111111');

		});
	});

	</script>
	
	



<% 
  java.sql.Date mem_bd = null;
  try {
	  mem_bd = memVO.getMem_bd();
   } catch (Exception e) {
	   mem_bd = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
  
  #imgLabel{
  	max-width:200px;
  }
</style>

<script type="text/javascript">

	
	<%@ include file="address.file" %>
	


    $.datetimepicker.setLocale('zh');
    $('#f_date1').datetimepicker({
	    theme: '',              //theme: 'dark',
	    timepicker:false,       //timepicker:true,
	    step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	    format:'Y-m-d',         //format:'Y-m-d H:i:s',
	 	value: '<%=mem_bd%>', // value:   new Date(),
       //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
       //startDate:	            '2017/07/10',  // 起始日
       //minDate:               '-1970-01-01', // 去除今日(不含)之前
       //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
    });
        
        
</script>

</html>
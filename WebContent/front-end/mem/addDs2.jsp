<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.diveshop.model.*"%>
<%@ page import="com.dspic.model.*"%>

<%
	DiveshopVO diveshopVO = (DiveshopVO) request.getAttribute("diveshopVO");
	
%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>

<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCvS_7XU7xlsw0cWtSRBGBe6dPblebLkHM&callback=initMap"
	async defer>
</script>


<style>

img {
	width: 200px;
	hieght: 200px;
}
</style>


<%-------------peng寫的，sweetalert2用 -----------------------------------------%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
        <!--引用jQuery-->
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
<!--引用SweetAlert2.js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>

</head>

<body onload="initMap()">
	<!--====================導覽頁===========================  -->
	<header>
	<%@ include file="/HeaderFooter/header.jsp"%>
	</header>
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
                        <h2 class="card-title text-center"><i class="fa fa-edit"></i>註冊潛店</h2>
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
                        
                            <form class="form-signin" method="post" action="<%=request.getContextPath()%>/diveshop/diveshop.do" name="form1" enctype="multipart/form-data">
		                                <div class="row">
		                                    <div class="col-md-12">
		                                    <label for="ds_name">潛店名稱<font style="color: red">*</font></label>
		                                <p>
		                                    <input type="text" name="ds_name" id="ds_name" class="form-control" placeholder="Name" autofocus value="<%=(diveshopVO == null) ? "" : diveshopVO.getDs_name()%>">
		                                </p>
		                                <label for="brcid">營業登記證號<font style="color: red">*</font></label>
		                                <p>
		                                    <input type="text" name="brcid" id="brcid" class="form-control" placeholder="證號" autofocus value="<%=(diveshopVO == null) ? "" : diveshopVO.getBrcid()%>">
		                                </p>
		                                <label for="tel">電話<font style="color: red">*</font></label>
		                                <p>
		                                    <input type="tel" name="tel" id="tel"  class="form-control" autofocus placeholder="Phone" value="<%=(diveshopVO == null) ? "" : diveshopVO.getTel()%>">
		                                </p>
		                                
		                                <label for="address">地址<font style="color: red">*</font></label>
		                                <div class="row">
		                                    <div class="col-md-6" >  
		                                        <input  name="address" id="address"  class="form-control twaddress"  value="<%=(diveshopVO == null) ? "桃園市中壢區中央路300號" : diveshopVO.getAddress()%>">
		                                        <input type="button" id="submit" value="取得緯經度">
		                                    
		                                    </div>
		                                    <div class="form-control"  style="height:0px;padding-bottom:50%" id="map">  </div>
                                    		
                                    	</div>
                                        <br>
                                        <label for="ds_latlng">潛店緯經度&nbsp;&nbsp;<font style="color: red">*</font></label>
                                        <p>
                                            <input type="text" id="ds_latlng" name="ds_latlng" class="form-control" placeholder="" autofocus value="<%=(diveshopVO == null) ? "32.21" : diveshopVO.getDs_latlng()%>">
                                        </p>
                                        
                                    </div>    

                                </div>
                        </div>
                        <hr class="my-4">
                        <div class="row">
                            <div class="col-md-12">
                                 <label for="dsaccount">帳號&nbsp;&nbsp;<font style="color: red">*</font></label>
                                        <p>
                                            <input type="text" id="dsaccount" name="dsaccount" class="form-control" placeholder="Account" autofocus value="<%=(diveshopVO == null) ? "" : diveshopVO.getDsaccount()%>">
                                        </p>
                                        <label for="dspaw">密碼&nbsp;&nbsp;<font style="color: red">*</font></label>
                                        <p>
                                            <input type="password" id="dspaw" name="dspaw" class="form-control" placeholder="Password" value="<%=(diveshopVO == null) ? "" : diveshopVO.getDspaw()%>">
                                        </p>
                                        <label for="dsmail">信箱&nbsp;&nbsp;<font style="color: red">*</font></label>
                                        <p>
                                            <input type="email" name="dsmail" id="dsmail" class="form-control" autofocus placeholder="E-mail" value="<%=(diveshopVO == null) ? "" : diveshopVO.getDsmail()%>">
                                        </p>
                                <label for="dsinfo">潛店介紹<font style="color: red">*</font></label>
                                <textarea class="md-textarea form-control" id="dsinfo" name="dsinfo" placeholder="介紹潛店的特色資訊" rows="5" width=100% value="<%=(diveshopVO == null) ? "" : diveshopVO.getDsinfo()%>"></textarea>
                                 
                                <label  for="addPics">潛店圖片&nbsp;&nbsp;<font style="color: red">*</font></label>
                                        <label class="btn btn-info" style="width: 100%">
                                        <p class="imgBox">點擊上傳
                                            <input type="file" name="addPics" id="addPics" class="custom-file-input form-control uploadBox" alert="點擊上傳圖片" autofocus  multiple>
                                        </p>
                                        </label>
                                
                                <div style="height:50px"></div>
                               
                                   <button type="button" id="magic" class="btn btn-primary">神奇小按鈕</button>
                               
                                <hr class="my-4">。
                                <input type="hidden" name="ds_state" size="45" value="<%=(diveshopVO == null) ? "未審核" : diveshopVO.getDs_state()%>" />
                                <input type="hidden" name="ds_ascore" size="45"
					value="<%=(diveshopVO == null) ? "00" : diveshopVO.getDs_ascore()%>" />
				<input type="hidden" name="ds_rep_no" size="45"
					value="<%=(diveshopVO == null) ? "00" : diveshopVO.getDs_rep_no()%>" />
                                <input type="hidden" name="action" value="insert">
                                <button class="btn btn-lg btn-info btn-block text-uppercase" type="submit">送出</button>
                            </div>
                        </div>
                        
        		<script>
			
		<%@ include file="address.file" %>
			
		$(".uploadBox").change(function(e) {
				var url = null;
				var img = null;
				var length = e.target.files.length; //上傳幾個檔案
				for (let i = 0; i < length; i++) {
					url = URL.createObjectURL(e.target.files[i]);
					img = $("<img>").attr("src", url);
					$("<img>").attr('style',  'width: 100%');
					$(".imgBox").append(img);
				}
			});
		</script>

		<script>
		
			function initMap() {
				var map = new google.maps.Map(document.getElementById('map'), {
					zoom : 15,
					center : {
						lat : 24.967841,
						lng : 121.191830
					}
				});
				var geocoder = new google.maps.Geocoder();
				
				document.getElementById('submit').addEventListener('click',
						function() {
							geocodeAddress(geocoder, map);
						});
			}
			
			function geocodeAddress(geocoder, resultsMap) {	
				var ds_latlng = document.getElementById('ds_latlng');
				var ds_name = document.getElementById('ds_name').value;
		        var address = document.getElementById('address').value;
		        var marker = null;
		        var infowindow = new google.maps.InfoWindow({
		            content: '<h5>' + ds_name + '</h5>'
		          });
		        geocoder.geocode({'address': address}, function(results, status) {
		          if (status === 'OK') {
		            resultsMap.setCenter(results[0].geometry.location);
		            marker = new google.maps.Marker({
		              map: resultsMap,
		              position: results[0].geometry.location,
		              title:ds_name
		            });
		            ds_latlng.value =results[0].geometry.location;
		            
		            var a = -1;
		            marker.addListener('click',function(){
		            	a = a * -1;
		                if(a > 0){
		                  infowindow.open(map, marker);
		                }else{
		                  infowindow.close();
		                }
		              });
		            
		            marker.addListener('click',function(){
		                if(marker.getAnimation()==null){
		                  marker.setAnimation(google.maps.Animation.BOUNCE);
		                }else{
		                  marker.setAnimation(null);
		                }
		              });
		          } else {
		            alert('Geocode was not successful for the following reason: ' + status);
		          }
		        });
		      }
			
			
			//神奇小按鈕
			$(document).ready(function(){
				$('#magic').click(function(){
					$('#ds_name').val('黃色潛水艇');
					$('#brcid').val('123456');
					$('#dsmail').val('da105g5@gmail.com');
					$('#tel').val('02-2222222');
					$('#dsaccount').val('peng');
					$('#dspaw').val('123456');
					$('#dsmail').val('da105g5@gmail.com');
					$('#dsinfo').val('擁有專業教練');
				});
			});
			
						
		</script>                
                        
                        
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
	</div>



<%--------------peng改的，註冊成功後彈窗 -----------------------------------------%>
	<c:if test="${not empty message}">
	
		<script type="text/javascript">

                //alert範例
                swal("註冊成功", "請待驗證後，至您的信箱查收信後登入", "success").then(function(){
                    $(location).attr('href', '<%=request.getContextPath()%>/front-end/index.jsp');

                })


    	</script>
	</c:if>
<!--------------------------------------------------底部--------------------------------------------->

<%@ include file="/HeaderFooter/footer.jsp" %>

</body>



</html>
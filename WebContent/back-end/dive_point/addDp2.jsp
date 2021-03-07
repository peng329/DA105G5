<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<style>
.pic {
	width: 300px;
}
</style>

</head>
<body>
	<!-- 	<img class="pic" src="http://localhost:8081/DA105G5/DBGifReader2.do?dp_no=DP000003&dp_pic=dp_pic1"> -->
	<!-- 	<img class="pic" src="http://localhost:8081/DA105G5/DBGifReader2.do?dp_no=DP000003&dp_pic=dp_pic2"> -->
	<!-- 	<img class="pic" src="http://localhost:8081/DA105G5/DBGifReader2.do?dp_no=DP000003&dp_pic=dp_pic3"> -->
	<!-- 	<img class="pic" src="http://localhost:8081/DA105G5/DBGifReader2.do?dp_no=DP000003&dp_pic=dp_pic4"> -->
	<div id="preview"></div>
	<div>
		<input id="dp_no" type="test" value="DP000003">
		<button id="test">Test</button>
	</div>

	<script>
		var data = {
			action : 'getAllDp'
		}
		$.ajax({
			url : '/DA105G5/dive_point/dp.do',
			type : 'GET',
			data : data,
			dataType : 'json',
			error : function(xhr) {
				console.log(xhr);
			},
			success : function(res) {
				console.log(1);
				console.log(res);
				// 		        res = JSON.parse(res);
				console.log(res[6].dp_no);
			}
		});
		console.log(2);

		$('#test').on(
				'click',
				function() {
					var data = {
						action : 'getOne',
						dp_no : $('#dp_no').val()
					}
					$.ajax({
						url : '/DA105G5/dive_point/dp.do',
						type : 'GET',
						data : data,
						dataType : 'json',
						error : function(xhr) {
							alert('AJAX Error');
							// 			      console.log(xhr.responseText);
						},
						success : function(res) {
							if (res.dp_no) {
								$('#preview').empty();
								for (var i = 1; i <= 4; i++) {
									$('#preview').append(
											'<img class="pic" src="http://localhost:8081/DA105G5/LocPic4.do?dp_no='
													+ res.dp_no
													+ '&dp_pic=dp_pic' + i
													+ '">');
								}
							}
						}
					});
				});
	</script>
</body>
</html>
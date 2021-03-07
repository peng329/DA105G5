<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Bubble up index</title>



<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
        <!--引用jQuery-->
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
<!--引用SweetAlert2.js-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>


</head>


<body>


<%--------------權限開通後彈窗 -----------------------------------------%>
	
	
		<script type="text/javascript">

                //alert範例
                swal("權限已經開通", "請至首頁登入", "success").then(function(){
                    $(location).attr('href', '<%=request.getContextPath()%>/front-end/mem/memLogin.jsp');

                })


    	</script>

</body>


</html>
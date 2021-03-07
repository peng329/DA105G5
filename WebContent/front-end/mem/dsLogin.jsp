

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html lang="zh-Hant-TW">

<head>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>Bubble up index</title>

<script src="<%=request.getContextPath()%>/kit.fontawesome.com/e218ab780d.js"></script>

    <style type="text/css">
    #memLogin {
        border: 2px solid lightblue;
        text-align: center;

        max-height: 500px;
        max-width: 400px;
    }

    h1 {
        margin: auto;
    }

    #loginRow {
        max-height: 90px;
        max-width: 396px;
        background-color: paleturquoise;
    }
    </style>
</head>

<body>
    <header>
  
<%@ include file="/HeaderFooter/header.jsp" %>

  </header>
    <!------------------------------------------------- 登入頁面 ---------------------------------------------->
    <div class="container" id="memLogin">
        <div class="row" id="#loginRow">
            <h1><i class="fa fa-lock" aria-hidden="true"></i> Login</h1>
        </div><br /><br />
        <form name="login" action="" method="post">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fa fa-user-circle-o" aria-hidden="true"></i></span>
                </div>
                <input type="text" name="mem_id" class="form-control" placeholder="帳號" style="ime-mode:active"/>
            </div><br />
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fa fa-key" aria-hidden="true"></i></span>
                </div>
                <input type="Password" name="mem_psw" class="form-control" placeholder="密碼" />
            </div><br />
            <span >
                           <%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs}">
										<font size="3" style="color: red">${errorMsgs}</font>
										<hr style="color: red">
							</c:if>
	
            </span>

            <br />
            <button type="submit" class="btn btn-success" onclick="dsLogin()";><span class="glyphicon glyphicon-off"></span> 潛店登入</button>
            <br>
            <br />
            <center>
                <div style="border:1px solid black;height:1px;width:300px;"></div>
            </center><br />
            <div class="footer">
                <p>Don't have an Account! <a href='<%=request.getContextPath()%>/front-end/mem/addDs.jsp'> 潛店註冊</a>
<!--                     <p>Forgot <a href="addStore.html">Password?</a></p> -->
            </div>
        </form>
    </div>

<!--------------------------------------------------底部--------------------------------------------->

<%@ include file="/HeaderFooter/footer.jsp" %>
</body>

<script language="javascript">

function dsLogin(){
    document.login.action="<%=request.getContextPath()%>/dsLoginhandler";
    document.login.submit();
}

</script>
</html>
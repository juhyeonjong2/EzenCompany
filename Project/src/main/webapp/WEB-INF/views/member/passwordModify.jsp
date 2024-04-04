<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
    <link href="<%=request.getContextPath()%>/resources/css/member.css" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
    <script>
    	function changePw(){
    		
			let check = false;
			let errorPw = $("#errorPw");
			let valuePw = $(".rePassword").val();
			
			const regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$/; // 대소문자, 특문, 숫자 최소 한개씩 포함 8~20자 
			let regRs = regex.test(valuePw);
			
			if(valuePw == ""){
				errorPw.text("필수입력입니다");
				errorPw.css("color", "red");
				check = false;
			}else if(!regRs){
				errorPw.text("영문 대소문자, 숫자, 특수문자 포함 8~20자입니다.");
				errorPw.css("color", "red");
				check = false;
			} else {
				errorPw.text("사용가능합니다.");
				errorPw.css("color", "green");
				check = true;
			}
    			
    		let rePassword = $(".rePassword").val();
    		let rePassword2 = $(".rePassword2").val();
    		if(rePassword == rePassword2 && check){
    			$(".changePwfrm").submit();
    		}else{
    			alert("새 비밀번호확인이 일치하지 않거나 올바른양식이 아닙니다.");
    		}
    	}
    </script>
</head>
<body>
  <div class="inner clearfix">

      <div class="mainDiv">
        <!--위에 큰 아이콘-->
        <div>
          <br> 
          <img src="<%=request.getContextPath()%>/resources/icon/mainUser.png" class="loginImg">
        </div>

        <form action="<%=request.getContextPath()%>/changePwOk" method="Post" class="changePwfrm">
          <div class="loginFormTop">
            <div class="textBox">
              <img src="<%=request.getContextPath()%>/resources/icon/lock.png">
              <input type="password" class="inputText rePassword" placeholder="새 비밀번호" name="rePassword">
              <div class="Line1"></div>
              <span id="errorPw"></span>
            </div>
            <div>
                <img src="<%=request.getContextPath()%>/resources/icon/lock.png">
                <input type="password" class="inputText rePassword2" placeholder="새 비밀번호 확인" name="rePassword2">
                <div class="Line1"></div>
            </div>
          </div> <!--loginFormTop-->
  
          <div class="submitBox">
            <input type="button" class="LoginButton" value="비밀번호 변경" onclick="changePw()">
            <input type="hidden" value="${shortUrl}" name="url">
          </div>
        </form>
      </div> <!--메인의 파란박스-->

    </div> <!--이너-->


</body>
</html>
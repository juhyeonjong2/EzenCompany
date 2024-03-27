<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>회원가입</title>
	<link href="resources/css/member.css" rel="stylesheet">
	<script src="resources/js/jquery-3.7.1.min.js"></script>
	<script>
		function checkID(){
			let text = $('.inputTextButton').val();
			$.ajax({
				url:"checkID.do",
				data : {text : text},
				success:function(data){
				  if(data == "true"){
					alert("사용할 수 있는 아이디");
				  }else{
					alert("아이디가 중복됨");
				  }
				}
				
			});
		}

		//출처: https://velog.io/@yu_oolong/SpringFramework-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0feat.-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A9%94%EC%9D%BC
		//인증번호 받기 클릭 시
		$(".joinButton").click(function() {
	    	const email = $("#email").val(); //사용자가 입력한 이메일 값 얻어오기 
	    	//짧은경로를 가공해 email을 얻는 dao를 호출함
	    	
	    	//Ajax로 전송
	        $.ajax({
	        	url : './EmailAuth', //컨트롤러에서 사용할 경로(수정필요)
	        	data : {email : email},
	        	type : 'POST',
	        	dataType : 'json',
	        	success : function(result) {
	        		code = result; //밖에서 인증번호와 맞는지 비교하기 위해서 만듬
	        		alert("인증 코드가 입력하신 이메일로 전송 되었습니다.");
	       		}
	        }); //End Ajax
	    });
	</script>
</head>
<body>
    <div class="inner clearfix">
        <div class="mainDiv">

          <div>
            <br>
            <img src="resources/icon/mainUser.png" class="loginImg">
          </div>
  
          <form action="joinOk" method="post">
            <div class="joinFormTop">
              <div class="textBox">
                <img src="resources/icon/user.png">
                <input type="text" class="inputTextButton" placeholder="아이디" name="mid">
                <input type="button" value="중복확인" class="joinButton" onclick="checkID()">
                <div class="Line2"></div>
              </div>

              <div class="textBox">
                <img src="resources/icon/lock.png">
                <input type="password" class="inputText" placeholder="비밀번호" name="mpassword">
                <div class="Line1"></div>
              </div>

              <div class="textBox">
                <img src="resources/icon/lock.png">
                <input type="password" class="inputText" placeholder="비밀번호확인" name="checkpassword">
                <div class="Line1"></div>
              </div>

              <div>
                <img src="resources/icon/email.png" class="iconImg">
                <input type="password" class="inputTextButton" placeholder="인증번호 확인">
                    <input type="button" value="인증번호 받기" class="joinButton">
                <div class="Line2"></div>
              </div>
            </div> <!--joinFormTop-->
    
            <div class="submitBox">
              <input type="submit" class="LoginButton" value="회원가입">
            </div>
          </form>
  
        </div> <!--메인의 파란박스-->
      </div> <!--이너-->
</body>
</html>
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
					//인증성공시 버튼클릭 가능
					$(".LoginButton").attr("disabled", false);
				  }else{
					alert("아이디가 중복됨");
				  }
				}
				
			});
		}

		//출처: https://velog.io/@yu_oolong/SpringFramework-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0feat.-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A9%94%EC%9D%BC
		//인증번호 받기 클릭 시
		$(".joinButton").click(function() {
			
			let url = $(".shortUrl").val();
			let email = "";
			
			//Ajax로 email 반환
		    $.ajax({
		    	url : 'getEmail2',
		    	data : {url : url},
		    	type : 'POST',
		    	async: false,
		    	success : function(result) {
		    		email = result;
		   		}
		    });
	    	
	    	//Ajax로 전송
	        $.ajax({
	        	url : 'certSend', //컨트롤러에서 사용할 경로(수정필요)
	        	data : {email : email},
	        	type : 'POST',
	        	dataType : 'json',
	        	success : function(result) {
	        		alert("인증 코드가 입력하신 이메일로 전송 되었습니다.");
	       		}
	        }); //End Ajax
	    });
		
		//회원가입 버튼을 누른경우
		function checkJoin(){
			//1.비번을 가져와서 비번확인과 일치하는지 확인
			let pw = $(".password");
			let pw2 = $(".password2");
			
			if(pw == pw2){
				//2.ajax를 통해 인증번호적은걸 확인
				let url = $(".shortUrl").val();
				let email = "";
				
				//Ajax로 email 반환
			    $.ajax({
			    	url : 'getEmail2',
			    	data : {url : url},
			    	type : 'POST',
			    	async: false,
			    	success : function(result) {
			    		email = result;
			   		}
			    });
				
				let certNum = $(".certNum");
				
			    $.ajax({
			    	url : 'checkCert',
			    	data : {certNum : certNum, email : email},
			    	type : 'POST',
			    	async: false,
			    	success : function(result) {
			    		if(result == true){
			    			$("joinOk").submit();
			    		}else{
			    			alert("인증번호가 일치하지 않습니다");
			    		}
			   		}
			    });
			}else{
				alert("비밀번호확인이 일치하지 않습니다");
			}
		}
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
                <input type="password" class="password" placeholder="비밀번호" name="mpassword">
                <div class="Line1"></div>
              </div>

              <div class="textBox">
                <img src="resources/icon/lock.png">
                <input type="password" class="password2" placeholder="비밀번호확인" name="checkpassword">
                <div class="Line1"></div>
              </div>

              <div>
                <img src="resources/icon/email.png" class="iconImg">
                <input type="password" class="certNum" placeholder="인증번호 확인">
                    <input type="button" value="인증번호 받기" class="joinButton">
                    <input type="hidden" value="${shortUrl}" class="shortUrl" name="shortUrl">
                <div class="Line2"></div>
              </div>
            </div> <!--joinFormTop-->
    
            <div class="submitBox">
              <input type="button" class="LoginButton" value="회원가입" onclick="checkJoin()">
            </div>
          </form>
  
        </div> <!--메인의 파란박스-->
      </div> <!--이너-->
</body>
</html>
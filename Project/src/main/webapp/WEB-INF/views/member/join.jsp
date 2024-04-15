<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>회원가입</title>
	<link href="<%=request.getContextPath()%>/resources/css/member.css" rel="stylesheet">
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
	<script>
		function checkID(){
			let text = $('.inputTextButton').val();
			$.ajax({
				url:"<%=request.getContextPath()%>/checkID",
				data : {text : text},
				success:function(data){
				  if(data == "true"){
					alert("사용할 수 있는 아이디");
					//인증성공시 버튼클릭 가능
					//$(".LoginButton").attr("disabled", false);
				  }else{
					alert("아이디가 중복됨");
					console.log(data);
				  }
				}
				
			});
		}
	
		//출처: https://velog.io/@yu_oolong/SpringFramework-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0feat.-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A9%94%EC%9D%BC
		//인증번호 받기 클릭 시
		function getCert() {
			let url = $(".shortUrl").val();
			let email = "";
			//Ajax로 email 반환
		     $.ajax({
		    	url : '<%=request.getContextPath()%>/getEmail',
		    	data : {url : url},
		    	type : 'POST',
		    	async: false,
		    	success : function(result) {
		    		email = result;
		   		}
		    });
	    	
	    	//Ajax로 전송
	        $.ajax({
	        	url : '<%=request.getContextPath()%>/certSend', //컨트롤러에서 사용할 경로(수정필요)
	        	data : {email : email},
	        	type : 'POST',
	        	success : function(result) {
	        		alert("인증 코드가 입력하신 이메일로 전송 되었습니다.");
	       		}
	        });  //End Ajax
	    };
		
		//회원가입 버튼을 누른경우
		function checkJoin(){
			
			let check1 = false;
			let check2 = false;
			let errorId = $("#errorId");
			let errorPw = $("#errorPw");
			let valuePw = $(".password").val();
			let valueId = $(".id").val();
			
			// 대소문자, 특문, 숫자 최소 한개씩 포함 8~20자
			const regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,20}$/; 
			let regRs = regex.test(valuePw);
			
			//영문,숫자만 가능한 5~12자리
			const regex2 =  /^[A-Za-z0-9]{5,12}$/;
			let regRs2 = regex2.test(valueId);
			
			if(valuePw == ""){
				errorPw.text("필수입력입니다");
				errorPw.css("color", "red");
				check1 = false;
			}else if(!regRs){
				errorPw.text("영문 대소문자, 숫자, 특수문자 포함 8~20자입니다.");
				errorPw.css("color", "red");
				check1 = false;
			} else {
				errorPw.text("사용가능합니다.");
				errorPw.css("color", "green");
				check1 = true;
			}
			
			if(valueId == ""){
				errorId.text("필수입력입니다");
				errorId.css("color", "red");
				check2 = false;
			}else if(!regRs2){
				errorId.text("영문, 숫자만 가능한 5~12자리입니다.");
				errorId.css("color", "red");
				check2 = false;
			} else {
				errorId.text("사용가능합니다.");
				errorId.css("color", "green");
				check2 = true;
			}

			//비번을 가져와서 비번확인과 일치하는지 확인
			let pw = $(".password").val();
			let pw2 = $(".password2").val();
			if(pw == pw2 && check1 && check2){
				//2.ajax를 통해 인증번호적은걸 확인
				let url = $(".shortUrl").val();
				let email = "";
				
				//Ajax로 email 반환
			    $.ajax({
			    	url : '<%=request.getContextPath()%>/getEmail',
			    	data : {url : url},
			    	type : 'POST',
			    	async: false,
			    	success : function(result) {
			    		email = result;
			   		}
			    });
				
				let certNum = $(".certNum").val();
				
			    $.ajax({
			    	url : '<%=request.getContextPath()%>/checkCert',
			    	data : {certNum : certNum, email : email, valueId : valueId, valuePw : valuePw},
			    	type : 'POST',
			    	async: false,
			    	success : function(result) {
			    		console.log(result);
			    		if(result == "true"){
			    			$(".joinfrm").submit();
			    		}else if(result == "false"){
			    			alert("인증번호가 일치하지 않습니다");
			    		}else{
			    			alert("양식에 맞게 작성해주세요");
			    		}
			   		}
			    });
			}else{
				alert("비밀번호가 일치하지 않거나 올바른 양식이 아닙니다");
			}
		}
	</script>
</head>
<body>
    <div class="inner clearfix">
        <div class="mainDiv">

          <div>
            <br>
            <img src="<%=request.getContextPath()%>/resources/icon/mainUser.png" class="loginImg">
          </div>
  
          <form action="<%=request.getContextPath()%>/joinOk" method="post" class="joinfrm">
            <div class="joinFormTop">
              <div class="textBox">
                <img src="<%=request.getContextPath()%>/resources/icon/user.png">
                <input type="text" class="inputTextButton id" placeholder="아이디" name="mid">
                <input type="button" value="중복확인" class="joinButton" onclick="checkID()">
                <div class="Line2"></div>
                <span id="errorId"></span>
              </div>

              <div class="textBox">
                <img src="<%=request.getContextPath()%>/resources/icon/lock.png">
                <input type="password" class="password inputText" placeholder="비밀번호" name="mpassword">
                <div class="Line1"></div>
                <span id="errorPw"></span>
              </div>

              <div class="textBox">
                <img src="<%=request.getContextPath()%>/resources/icon/lock.png">
                <input type="password" class="password2 inputText" placeholder="비밀번호확인" name="checkpassword">
                <div class="Line1"></div>
              </div>

              <div>
                <img src="<%=request.getContextPath()%>/resources/icon/email.png" class="iconImg">
                <input type="password" class="certNum inputTextButton" placeholder="인증번호 확인">
                    <input type="button" value="인증번호 받기" class="joinButton" onclick="getCert()">
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
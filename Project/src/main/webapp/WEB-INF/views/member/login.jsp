<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link href="resources/css/member.css" rel="stylesheet">
</head>
<body>
  <div class="inner clearfix">

      <div class="mainDiv">
        <!--위에 큰 아이콘-->
        <div>
          <br>
          <img src="resources/icon/mainUser.png" class="loginImg">
        </div>
        
        <form action="#">
          <div class="loginFormTop">
            <div class="textBox">
              <img src="resources/icon/user.png">
              <input type="text" class="inputText" placeholder="아이디" name="username">
              <div class="Line1"></div>
            </div>
            <div>
              <img src="resources/icon/lock.png">
              <input type="password" class="inputText" placeholder="비밀번호" name="password">
              <div class="Line1"></div>
            </div>
          </div> <!--loginFormTop-->
  
          <div class="submitBox">
            <input type="submit" class="LoginButton" value="로그인">
            <br>
            <a class="ForgotButton" href="searchPW">비밀번호를 잊으셨나요?</a>
            <a class="ForgotButton" href="join">회원가입(임시)</a>
          </div>
        </form>
      </div> <!--메인의 파란박스-->

    </div> <!--이너-->
</body>
</html>
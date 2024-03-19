<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link href="/resources/css/member.css" type="text/css" rel="stylesheet"><!-- 경로 수정 필요 -->
</head>
<body>
  <div class="inner clearfix">

      <div class="mainDiv">
        <!--위에 큰 아이콘-->
        <div>
          <br> <!--마진이 잘 안들어가서 위에 br을 만들고 br값(21)을 뺴줌-->
          <img src="<%=request.getContextPath()%>/icon/mainUser.png" class="loginImg">
        </div>

        <!--아이콘 무료다운 링크(피그마에서도 추출가능함)-->
        <!--https://icons8.com/icons-->
        <form action="#">
          <div class="loginFormTop">
            <div class="textBox">
              <img src="<%=request.getContextPath()%>/icon/user.png">
              <input type="text" class="inputText" placeholder="아이디">
              <div class="Line1"></div>
            </div>
            <div>
              <img src="<%=request.getContextPath()%>/icon/lock.png">
              <input type="password" class="inputText" placeholder="비밀번호">
              <div class="Line1"></div>
            </div>
          </div> <!--loginFormTop-->
  
          <div class="submitBox">
            <input type="submit" class="LoginButton" value="로그인">
            <br>
            <a class="ForgotButton" href="#">비밀번호를 잊으셨나요?</a>
          </div>
        </form>
      </div> <!--메인의 파란박스-->

    </div> <!--이너-->


</body>
</html>
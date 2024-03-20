<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
    <link href="resources/css/member.css" rel="stylesheet">
</head>
<body>
  <div class="inner clearfix">

      <div class="mainDiv">
        <!--위에 큰 아이콘-->
        <div>
          <br> <!--마진이 잘 안들어가서 위에 br을 만들고 br값(21)을 뺴줌-->
          <img src="resources/icon/mainUser.png" class="loginImg">
        </div>

        <form action="#">
          <div class="loginFormTop">
            <div class="textBox">
              <img src="resources/icon/lock.png">
              <input type="password" class="inputText" placeholder="새 비밀번호">
              <div class="Line1"></div>
            </div>
            <div>
                <img src="resources/icon/lock.png">
                <input type="password" class="inputText" placeholder="새 비밀번호 확인">
                <div class="Line1"></div>
            </div>
          </div> <!--loginFormTop-->
  
          <div class="submitBox">
            <input type="submit" class="LoginButton" value="비밀번호 변경">
          </div>
        </form>
      </div> <!--메인의 파란박스-->

    </div> <!--이너-->


</body>
</html>
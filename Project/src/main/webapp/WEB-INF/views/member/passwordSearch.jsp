<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기</title>
    <link href="resources/css/member.css" rel="stylesheet">
    <script src="resources/js/jquery-3.7.1.min.js"></script>
</head>
<script>
	//비밀번호 변경 클릭 시
	function pwSearch() {
		
		let mid = $(".inputText").val();
		
		//Ajax로 메일발송
	    $.ajax({
	    	url : '<%=request.getContextPath()%>/sendEmail',
	    	data : {mid : mid},
	    	success : function(result) {
	    		if(result == "true"){
	    			alert("비밀번호 수정 링크를 발송했습니다.");
	    		}else{
	    			alert("메일발송에 실패하였습니다.");
	    		}
	   		}
	    });
	};
</script>
<body>
  <div class="inner clearfix">

      <div class="mainDiv">
        <!--위에 큰 아이콘-->
        <div>
          <br>
          <img src="resources/icon/mainUser.png" class="loginImg">
        </div>

          <div class="loginFormTop">
            <div class="textBox">
              <img src="resources/icon/user.png">
              <input type="text" class="inputText" placeholder="아이디">
              <div class="Line1"></div>
            </div>
          </div> <!--loginFormTop-->
  
          <div class="submitBox">
            <input type="submit" class="LoginButton" value="이메일로 비밀번호 변경 링크 발송" onclick="pwSearch()">
            <a class="LoginButton" href="login">로그인으로 돌아가기</a>
          </div>
      </div> <!--메인의 파란박스-->

    </div> <!--이너-->


</body>
</html>
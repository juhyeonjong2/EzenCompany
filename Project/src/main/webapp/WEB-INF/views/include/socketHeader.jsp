<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/resources/js/sockjs.min.js"></script>
<script>
// 전역변수 설정
var socket  = null;
$(document).ready(function(){
    // 웹소켓 연결
    var sock = new SockJS("/echo");
    socket = sock;

    // 데이터를 전달 받았을때 
    sock.onmessage = onMessage; // toast 생성

});

// toast생성 및 추가
function onMessage(evt){
    var data = evt.data;
    // toast
    let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
    toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
    toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
    toast += "<span aria-hidden='true'>&times;</span></button>";
    toast += "</div> <div class='toast-body'>" + data + "</div></div>";
    $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
    $(".toast").toast({"animation": true, "autohide": false});
    $('.toast').toast('show');
};	
</script>
</head>
<body>
	<div id="msgStack"></div>
	11
</body>
</html>
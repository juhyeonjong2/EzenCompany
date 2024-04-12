<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="<%=request.getContextPath()%>/resources/js/sockjs.min.js"></script>
</head>
<body>
	<script>
	//전역변수 설정
	var socket  = null;
	$(document).ready(function(){

	    // 웹소켓 연결
	    var sock = new SockJS("/echo");
	    socket = sock;
	    
	    //데이터를 전달 받았을때 
		sock.onmessage = onMessage;	    

    });
	
	function onMessage(evt){
		var evt = evt.data;
	    let data = evt.split(',');
	    let type = data[0]; //채팅,댓글
	    let target = data[1]; //받은 사람 사용할지는 모름
	    let content = data[2];//내용
	    let url = data[3]; //사용할지는 모름
	    let profile = $('.cImg').attr("src");
	    if(type == "채팅"){
	    	//에이작스 통신으로 채팅방 안이면 아래실행 아니면 토스트 넣기
	    	
	    	
	    	
	    	
	    	let html = '<div class="chatting_other_msg">'
	        		 + '<div class="chatting_profile">'
	        		 + '<img src="'
	        		 +  profile
	        		 + '" width="30" height="30" alt="Profile" class="rounded-circle">'
	        		 + '</div> <div class="msg">'
	        		 + content
	        		 + '</div> </div>'
       		$('.chatting_room').append(html);
	   	    $('#chatBox').scrollTop($("#chatBox")[0].scrollHeight);
	   	    
	    //디비에서 컬럼 만들어서 업데이트
	    }else if(type == "댓글"){
	    
	    }else{
	    	console.log("오류");
	    }
	}
		
		
	    //여기서 알림 작업
	    
	</script>
</body>
</html>
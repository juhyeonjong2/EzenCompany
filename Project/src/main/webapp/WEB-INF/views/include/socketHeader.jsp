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
	
	  //토스트 알림 세팅
	  function fillWidth(elem, timer, limit) {
	   		if (!timer) { timer = 3000; }	
	   		if (!limit) { limit = 100; }
	   		var width = 1;
	   		var id = setInterval(frame, timer / 100);
	   			function frame() {
	   			if (width >= limit) {
	   				clearInterval(id);
	   			} else {
	   				width++;
	   				elem.style.width = width + '%';
	   			}
	   		}
	   	};
	
	function onMessage(evt){
		var evt = evt.data;
	    let data = evt.split(',');
	    let type = data[0]; //채팅,댓글
	    let target = data[1]; //받은 사람 사용할지는 모름
	    let content = data[2];//내용
	    let myName = data[3]; //보낸사람
	    let profile = $('.cImg').attr("src");
	    if(type == "채팅"){
	    	//에이작스 통신으로 채팅방 안이면 아래실행 아니면 토스트 넣기
	    	$.ajax({
            url: "<%=request.getContextPath()%>/chatting/checkLink",
            success:function(data){
	               	if(data == "true"){
	               		//채팅창 안이라면
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
	               	}else{
	               		$("#toastBox").empty();
	               		
	               		let toast = "<div id='toast'><span>"+ myName + " : " + content + "</span></div>";
	               		let noti = "<span class='badge bg-success badge-number' id='chatBt'>!</span>"
	               		
	               	    $("#toastBox").append(toast);
	               		$(".chatNoti").append(noti);
	               		
	               	    $("#toast").toast({"animation": true, "autohide": false});
	               	    $('#toast').toast('show');
	               	    
	               	 	$("#chatBt").toast({"animation": true, "autohide": false});
	               	    $('#chatBt').toast('show');
	               	}//else
                } //success
            });// ajax끝
	    }else if(type == "블로그댓글"){
	    	$("#toastBox").empty();
       		
       		let toast = "<div id='replyBox'><span>"+ myName + " : 님이 블로그에 댓글을 남기셨습니다.</span></div>";
       		let noti = "<span class='badge bg-primary badge-number' id='replyBt'>!</span>"
       		
       	    $("#toastBox").append(toast);
       		$(".notiNoti").append(noti);
       		
       	    $("#replyBox").toast({"animation": true, "autohide": false});
       	    $('#replyBox').toast('show');
       	    
       	 	$("#replyBt").toast({"animation": true, "autohide": false});
       	    $('#replyBt').toast('show');
	    }else if(type == "게시글댓글"){
	    	$("#toastBox").empty();
       		
       		let toast = "<div id='replyBox'><span>"+ myName + " : 님이 게시글에 댓글을 남기셨습니다.</span></div>";
       		let noti = "<span class='badge bg-primary badge-number' id='replyBt'>!</span>"
       		
       	    $("#toastBox").append(toast);
       		$(".notiNoti").append(noti);
       		
       	    $("#replyBox").toast({"animation": true, "autohide": false});
       	    $('#replyBox').toast('show');
       	    
       	 	$("#replyBt").toast({"animation": true, "autohide": false});
       	    $('#replyBt').toast('show');
	    }else{
	    	console.log("오류");
	    }
	}
	</script>
	<div id='toastBox'></div>
</body>
</html>
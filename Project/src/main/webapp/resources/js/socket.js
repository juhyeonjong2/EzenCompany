//전역변수 설정
var socket  = null;
$(document).ready(function(){

    // 웹소켓 연결
    var sock = new SockJS("/echo");
    socket = sock;
    
    //데이터를 전달 받았을때 
	sock.onmessage = onMessage;
    
	//채팅창이 열렸을 때 나에게 메세지가 온 경우
	function onMessage(evt){
	    var evt = evt.data;
	    let data = evt.split(',');
	    let type = data[0]; //채팅,댓글
	    let target = data[1]; //받은 사람 사용할지는 모름
	    let content = data[2];//내용
	    let url = data[3]; //사용할지는 모름
		if(type == "채팅"){
			let html = '<div class="chatting_other_msg">'
         		 + '<div class="chatting_profile">'
         		 + '<img src="'
         		 +  profile
         		 + '" width="30" height="30" alt="Profile" class="rounded-circle">'
         		 + '</div> <div class="msg">'
         		 + content
         		 + '</div> </div>'
        	$('.chatting_room').append(html);	
		}
	    
	}//메세지가 온 경우
    

function onMessage(evt){
	console.log(121212);
	var evt1 = evt.data;
    let data = evt.split(',');
    let type = data[0]; //채팅,댓글
    if(type == "채팅"){
    console.log("오1류");
    	onChat(evt);
    }else if(type == "댓글"){
    
    }else{
    console.log("오류");
    }
}
	
	
    //여기서 알림 작업
    
    });


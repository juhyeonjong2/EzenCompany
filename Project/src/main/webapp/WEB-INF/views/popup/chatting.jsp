<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<section class="chatting_popup" id="chattingPopup">
	 <!-- 채팅 팝업(사람 목록) -->
	 <div class="modal fade" id="chattingModal" tabindex="-1">
	   <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	     <div class="modal-content">
	       <div class="modal-header">
	         <h5 class="modal-title">대화 상대</h5>
	         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	       </div>
	       <div class="modal-body">  
	         <div class="container-fluid chatting_member">
	           <div class="accordion chatttingList"> 
			<!-- 자바스크립트로 그려짐 -->
             </div>
           </div>
         </div>
       </div>
     </div>
   </div>
	
	 <!-- 채팅 룸 팝업 -->
	 <div class="modal fade" id="chattingRoomModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header d-flex flex-column">
              <div class="align-self-start">
                <a href="#" class="link-secondary" data-bs-target="#chattingModal" data-bs-toggle="modal">&lt; 뒤로가기</a>  
              </div>
              
              
              <div class="align-self-start d-flex mt-2">
                <img src="<%=request.getContextPath()%>/resources/img/MemberIcon.png" width="50" height="50" alt="Profile" class="rounded-circle cImg">
                <div class="d-flex flex-column ms-2">      
                  <span class="fw-bold user_name cName">주현종</span>
                  <span class="user_position cOption">사원</span>
                </div>
              </div>
              
              
            </div>
            <div class="modal-body">  
              <div class="container-fluid chatting_room">
              
              
                <div class="chatting_other_msg">
                  <div class="chatting_profile">
                    <img src="<%=request.getContextPath()%>/resources/img/profile-img.jpg" width="30" height="30" alt="Profile" class="rounded-circle">
                  </div>
                  <div class="msg">
                    안녕하세요 반갑습니다 
                    저는 새로 입사한 주현종입니다
                    잘 부탁드려요
                  </div>
                </div>
                
                
                
                <div class="chatting_my_msg">
                  <div class="msg">
                    네 안녕하세요 모르는게 있다면 
                    편하게 물어보세요
                  </div>
                </div>
                
   
                

              </div>
            </div>
            <div class="modal-footer d-flex align-items-center justify-content-center">
                <input type="text" class="form-control" placeholder="메세지 입력.." id="messageinput">
            </div>
          </div>
        </div>
      </div>
</section>
<script>
	// 채팅팝업 연 경우 기본 데이터 주입
(function() {
    const chattingPopup = document.getElementById('chattingPopup')
    if (chattingPopup) {
    	//채팅 팝업을 연 경우
    	chattingPopup.addEventListener('show.bs.modal', event => 
        {
        	
            $.ajax({
            	url: "<%=request.getContextPath()%>/chatting/chattingList",
            	success:function(list){
            		//재생성 방지를 위해 실행될때마다 비워줌	
			        $('.chatttingList').empty();
			        //여기서 1차 반복문
					for(let i = 0; i<list.length; i++){
						$.ajax({
							url: "<%=request.getContextPath()%>/chatting/detaleList",
							async: false,
			            	success:function(detaleList){
							let html = '<h2 class="accordion-header">'
									 + '<button class="accordion-button" type="button" data-bs-toggle="collapse"' 
									 + 'data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">'
									 + list[i].value
									 + '</button></h2>'
									 + '<div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">'
					                 + '<div class="accordion-body '
					                 +  list[i].aidx
					                 +  '">'
			                         + '</span>'
			                       	 + '</div>'
			                     	 + '</div>'
							$('.chatttingList').append(html);
			                   		 
		                     //2차 반복문
		                     for(let j =0; j<detaleList.length; j++){
		                    	 if(list[i].aidx == detaleList[j].aidx){
		                    		 let html2 = '<div class="chatting_user d-flex m-2 t-1 mdata" data-bs-target="#chattingRoomModal"'
		                    		 		   + 'data-bs-toggle="modal" data-bs-mno="'
		                    		 		   + detaleList[j].mno
		                    		 		   + '" data-bs-value="'
		                    		 		   + list[i].value
		                    		 		   + '" data-bs-img="MemberIcon.png" data-bs-name="'
		                    		 		   + detaleList[j].mname
		                    		 		   + '">' 
					                 	 	   + '<div class="d-flex">'
			                    			   +'<img src="'
							                   + "<%=request.getContextPath()%>"
							                   + '/resources/img/MemberIcon.png" width="50" height="50" alt="Profile" class="rounded-circle Profile">'
							                   + '<div class="d-flex flex-column ms-2">'
			                    		  	   + '<span class="fw-bold user_name">'
			                    			   + detaleList[j].mname
							                   + '</span>'
						                       + '<span class="user_position">'
						                       + list[i].value
						                   	   + '</div>'
						                   	   + '</div>'
						                   	   + '</div>'
						                      $('.'+list[i].aidx).append(html2);
						                   	   
				                              //타인번호로 정보 가져옴(프사)
				                              $.ajax({
				                              	 url: "<%=request.getContextPath()%>/chatting/getProfile",
				                             	 data: {anotherMno : detaleList[j].mno},
				                             	 async: false,
				                             	 success:function(profile){
				                             		 //해당 사원이 프로필 사진이 존재한다면
				                             		if(profile != null && profile != ""){
				                             			let img = "<%=request.getContextPath()%>/resources/upload/"+profile
				                             			$('.Profile').attr("src", img);
				                             			$('.mdata').attr("data-bs-img", profile);
				                             		}
				                             	 }
				                              });//프로필 사진 ajax 
						                   	   
						                   	   
		                    	 } 
		                     }
			                
		            	}//success
					});// 2번째 ajax
				}
           	} //success
           });// 1번째 ajax
            
            
        });// 채팅팝업 마무리
    }
  })();

	
	
	
	//채팅 룸 팝업!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	(function() {

    // 채팅 팝업(대화창) - 대화 상대의 mno or id를 넘겨받음.
    const attributeAddModal = document.getElementById('chattingRoomModal')
    if (attributeAddModal) {
        attributeAddModal.addEventListener('show.bs.modal', event => 
        {
        	const button = event.relatedTarget;
            const mno = button.getAttribute('data-bs-mno');
            const value = button.getAttribute('data-bs-value');
            const name = button.getAttribute('data-bs-name');
            const img = button.getAttribute('data-bs-img');
            $(".cName").text(name);
            $(".cOption").text(value);
            
            //이미지가 없는 경우
            let profile ="";
            if(img == "MemberIcon.png"){
            	profile = "<%=request.getContextPath()%>/resources/img/"+img
            }else{
            	profile = "<%=request.getContextPath()%>/resources/upload/"+img
            }
            $('.cImg').attr("src", profile);
            
            $.ajax({
            	url: "<%=request.getContextPath()%>/chatting/chattingStart",
            	data: {anotherMno : mno},
            	success:function(data){
            		$('.chatting_room').empty();
            		for(let i =0; i<data.length; i++){
            			//전체 반복하면서 채팅이 null이거나 빈문자열이 아닌경우
            			if(data[i].chat != null && data[i].chat != ""){
            				if(data[i].mno == mno){
            					
            					//내가 아닌 다른사람이 채팅을 친 경우
            					let html = '<div class="chatting_other_msg">'
            	                  		 + '<div class="chatting_profile">'
            	                  		 + '<img src="'
            	                  		 +  profile
            	                  		 + '" width="30" height="30" alt="Profile" class="rounded-circle">'
            	                  		 + '</div> <div class="msg">'
            	                  		 + data[i].chat
            	                  		 + '</div> </div>'
            	                 $('.chatting_room').append(html);		 
            				}else{
            					
            					//내가 채팅을 친 경우
            					let html = '<div class="chatting_my_msg">'
		       	                  		 + '<div class="msg">'
		       	                  		 + data[i].chat
		       	                  		 + '</div> </div>'
       	                		 $('.chatting_room').append(html);
            				}
            				
            			}
            		} //전체 데이터 반복문	
            	}//success
            });// ajax끝
            
            
            
            
            //웹소켓 작업
            //채팅 서버 주소
            let url = "ws://localhost:8080/ezencompany/chatserver";
            //웹 소켓
            let ws;
                 
     		// 연결
    	   	ws = new WebSocket(url);
    	   	// 소켓 이벤트 매핑
    	   	ws.onopen = function (evt) {
    	   		console.log('서버 연결 성공');	   				
    	   		// 현재 사용자가 입장했다고 서버에게 통지(유저명 전달)
    	   		// -> 1#유저명
    			ws.send('1#' + name + '#'); //이건 서버 이벤트가 아닌 메소드이다(send는)
    		};
            
            //메세지를 보낸경우
    		ws.onmessage = function (evt) {
    			let index = evt.data.indexOf("#", 2);
    			let no = evt.data.substring(0, 1); 
    			let user = evt.data.substring(2, index);
    			let txt = evt.data.substring(index + 1);
    			if (no == '1') {
    				console.log("접속");
    			} else if (no == '2') {
    				console.log("메세지");
    			} else if (no == '3') {
    				console.log("타인 접속");
    			}
    			//$('#list').scrollTop($('#list').prop('scrollHeight'));
    		};
    		
    		ws.onclose = function (evt) {
    			console.log('소켓이 닫힙니다.');
    		};

    		ws.onerror = function (evt) {
    			console.log(evt.data);
    		};
    		
    		
    		
    		$('#messageinput').on('keydown', function(key) {
    			if (key.keyCode == 13) {
    				let chat = $(this).val();
    				ws.send('2#' + mno + '#' + chat); //서버에게
    				//내가 채팅을 친 경우
					let html = '<div class="chatting_my_msg">'
   	                  		 + '<div class="msg">'
   	                  		 + chat
   	                  		 + '</div> </div>'
               		$('.chatting_room').append(html);
					$('#messageinput').val('');
    			}
    		});
    		
    		//모달창이 닫히는 경우 소켓을 끊어줌
    		$('#chattingRoomModal').on('hidden.bs.modal', function (e) { 
    			console.log("eee");
    			ws.close();
    		});
 
        });   
    }
  })();
		
	
</script>
</body>
</html>
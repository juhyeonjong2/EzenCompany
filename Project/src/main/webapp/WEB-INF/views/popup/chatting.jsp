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
            <div class="modal-body" id="chatBox">  
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
//채팅팝업 연 경우 기본 데이터 주입
(function() {
    const chattingPopup = document.getElementById('chattingPopup')
    if (chattingPopup) {
    	//채팅 팝업을 연 경우
    	chattingPopup.addEventListener('show.bs.modal', event => 
        {
        	$("#chatBt").remove();
        	$("#toast").remove();
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
		                    	 if(list[i].aidx == detaleList[j].aidx && detaleList[j].mid != null){
		                    		 let html2 = '<div class="chatting_user d-flex m-2 t-1 mdata" data-bs-target="#chattingRoomModal"'
		                    		 		   + 'data-bs-toggle="modal" data-bs-mno="'
		                    		 		   + detaleList[j].mno
		                    		 		   + '" data-bs-value="'
		                    		 		   + list[i].value
		                    		 		   + '" data-bs-mid="'
		                    		 		   + detaleList[j].mid
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
    	
        attributeAddModal.addEventListener('show.bs.modal', openChat); 
        
        	function openChat(){
        		const button = event.relatedTarget;
                const mno = button.getAttribute('data-bs-mno');
                const value = button.getAttribute('data-bs-value');
                const name = button.getAttribute('data-bs-name');
                const img = button.getAttribute('data-bs-img');
                const mid = button.getAttribute('data-bs-mid');
                let myName = "";
                $(".cName").text(name);
                $(".cOption").text(value);
                //디비에 link 업데이트
                $.ajax({
                	url: "<%=request.getContextPath()%>/chatting/linkStart",
                	success:function(data){
                		if(data == "true"){
                			console.log("연결됨");
                		}
                	}
                });// ajax끝
                
                //나의 이름 구하기
                $.ajax({
                	url: "<%=request.getContextPath()%>/chatting/get",
                	async: false,
                	success:function(name){
                		myName = name;
                	}
                });// ajax끝
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
                		$('#chatBox').scrollTop($("#chatBox")[0].scrollHeight);
                	}//success
                });// ajax끝
                
        		//엔터키를 누르면 db저장 후 웹소켓으로 데이터를 보낸다(수정필요)
        		$('#messageinput').on('keyup', function(key) {
        			if (key.keyCode == 13) {
        				let chat = $(this).val();
        				$.ajax({
        	            	url: "<%=request.getContextPath()%>/chatting/sendChat",
        	            	data: {anotherMno : mno, chat : chat},
        	            	async: false,
        	            	success:function(data){
        	            		if(data == "true"){
									//알림 보내기
        	            			$.ajax({
        	        	            	url: "<%=request.getContextPath()%>/notification/chatNoti",
        	        	            	data: {targetMno : mno},
        	        	            	success:function(data){
        	        	            		//어차피 웹소켓 알림 보낼 때 표시되니 필요 없을 듯
        	        	            		//$(".notiNoti").empty();
        	        	            		//let noti = "<span class='badge bg-primary badge-number' id='replyBt'>!</span>";
        	        	            		//$(".notiNoti").append(noti);
        	        	            	}
        	            			});

        	            			socket.send("채팅,"+mid+","+chat+","+myName);
        	            			//나의 채팅 그려주기
        	    					let html = '<div class="chatting_my_msg">'
        	       	                  		 + '<div class="msg">'
        	       	                  		 + chat
        	       	                  		 + '</div> </div>'
        	                   		$('.chatting_room').append(html);
        	    					$('#messageinput').val('');
        	    					$('#chatBox').scrollTop($("#chatBox")[0].scrollHeight);
        	            		}
        	            	}
        	            }); //ajax
        			}
        		}); //엔터키
        	} //openChat
    }
    
    //모달이 닫힐때마다 이벤트를 삭제시키고 다시생성해서 여러번 반복되는걸 막음
    attributeAddModal.addEventListener('hidden.bs.modal', endChat); 
	function endChat(){
		//ajax에서 연결을 업데이트
		$.ajax({
            url: "<%=request.getContextPath()%>/chatting/linkEnd",
            success:function(data){
	               	if(data == "true"){
	               		console.log("연결끊김");
	               	}
                }
            });// ajax끝
		
		attributeAddModal.removeEventListener('show.bs.modal', openChat);
		attributeAddModal.addEventListener('show.bs.modal', openChat); 
	}
	
  })();
	
</script>
</body>
</html>
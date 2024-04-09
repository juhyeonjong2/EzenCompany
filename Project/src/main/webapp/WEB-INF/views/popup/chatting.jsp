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
                <img src="<%=request.getContextPath()%>/resources/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                <div class="d-flex flex-column ms-2">      
                  <span class="fw-bold user_name">주현종</span>
                  <span class="user_position">사원</span>
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
                  <div class="chatting_profile">
                    <img src="<%=request.getContextPath()%>/resources/img/messages-2.jpg" width="30" height="30" alt="Profile" class="rounded-circle">
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
            		console.log(list);
            		//재생성 방지를 위해 실행될때마다 비워줌	
			        $('.chatttingList').empty();
			        //여기서 1차 반복문
					for(let i = 0; i<list.length; i++){
						$.ajax({
							url: "<%=request.getContextPath()%>/chatting/detaleList",
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
			                   		 
		                     //for문
		                     for(let j =0; j<detaleList.length; j++){
		                    	 if(list[i].aidx == detaleList[j].aidx){
		                    		 let html2 = '<div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal"'
		                    		 		   + 'data-bs-toggle="modal" data-bs-mno="'
		                    		 		   + detaleList[j].mno
		                    		 		   + '">' 
					                 	 	   + '<div class="d-flex">'
			                    			   +'<img src="'
							                   + "<%=request.getContextPath()%>"
							                   + '/resources/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">'
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
            console.log(mno);
            $.ajax({
            	url: "<%=request.getContextPath()%>/chatting/chattingStart",
            	data: {amno : mno},
            	success:function(data){
            		console.log(data);
            	}//success
            });// ajax끝
            
            
            
            
        });   
    }
  })();
	
	
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="chatting_popup">
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
                <div class="accordion"> 
                  <div class="accordion-item">
                    <h2 class="accordion-header">
                      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                       프로그래밍 팀
                      </button>
                    </h2>
                    <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">
                      <div class="accordion-body">
                        <!-- chatting_user div가 한묶음-->
                        <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="1">
                          <div class="d-flex">
                            <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                            <div class="d-flex flex-column ms-2">      
                                <span class="fw-bold user_name">홍길동</span>
                                <span class="user_position">사원</span>
                            </div>
                          </div>
                        </div>
        
                        <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="2">
                          <div class="d-flex">
                            <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                            <div class="d-flex flex-column ms-2">      
                                <span class="fw-bold user_name">김길동</span>
                                <span class="user_position">과장</span>
                            </div>
                          </div>
                        </div>
        
                        <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="3">
                          <div class="d-flex">
                            <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                            <div class="d-flex flex-column ms-2">      
                                <span class="fw-bold user_name">고길동</span>
                                <span class="user_position">부장</span>
                            </div>
                          </div>
                        </div>

                      </div>
                    </div>
                  </div>
                  <div class="accordion-item">
                    <h2 class="accordion-header">
                      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                        디자인 팀
                      </button>
                    </h2>
                    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
                      <div class="accordion-body">
                        <!-- chatting_user div가 한묶음-->
                        <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="1">
                          <div class="d-flex">
                            <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                            <div class="d-flex flex-column ms-2">      
                                <span class="fw-bold user_name">홍길동</span>
                                <span class="user_position">사원</span>
                            </div>
                          </div>
                        </div>
        
                        <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="2">
                          <div class="d-flex">
                            <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                            <div class="d-flex flex-column ms-2">      
                                <span class="fw-bold user_name">김길동</span>
                                <span class="user_position">과장</span>
                            </div>
                          </div>
                        </div>
        
                        <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="3">
                          <div class="d-flex">
                            <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                            <div class="d-flex flex-column ms-2">      
                                <span class="fw-bold user_name">고길동</span>
                                <span class="user_position">부장</span>
                            </div>
                          </div>
                        </div>

                      </div>
                    </div>
                  </div>
                  <div class="accordion-item">
                    <h2 class="accordion-header">
                      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                        기획 팀
                      </button>
                    </h2>
                    <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse">
                      <div class="accordion-body">
                          <!-- chatting_user div가 한묶음-->
                          <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="1">
                            <div class="d-flex">
                              <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                              <div class="d-flex flex-column ms-2">      
                                  <span class="fw-bold user_name">홍길동</span>
                                  <span class="user_position">사원</span>
                              </div>
                            </div>
                          </div>
          
                          <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="2">
                            <div class="d-flex">
                              <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                              <div class="d-flex flex-column ms-2">      
                                  <span class="fw-bold user_name">김길동</span>
                                  <span class="user_position">과장</span>
                              </div>
                            </div>
                          </div>
          
                          <div class="chatting_user d-flex m-2 t-1" data-bs-target="#chattingRoomModal" data-bs-toggle="modal" data-bs-mno="3">
                            <div class="d-flex">
                              <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
                              <div class="d-flex flex-column ms-2">      
                                  <span class="fw-bold user_name">고길동</span>
                                  <span class="user_position">부장</span>
                              </div>
                            </div>
                          </div>
                      </div>
                    </div>
                  </div>
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
                <img src="../assets/img/profile-img.jpg" width="50" height="50" alt="Profile" class="rounded-circle">
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
                    <img src="../assets/img/profile-img.jpg" width="30" height="30" alt="Profile" class="rounded-circle">
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
                    <img src="../assets/img/messages-2.jpg" width="30" height="30" alt="Profile" class="rounded-circle">
                  </div>
                </div>
                <div class="chatting_other_msg">
                  <div class="chatting_profile">
                    <img src="../assets/img/profile-img.jpg" width="30" height="30" alt="Profile" class="rounded-circle">
                  </div>
                  <div class="msg">
                    명령·규칙 또는 처분이 헌법이나 법률에 위반되는 여부가 재판의 전제가 된 경우에는 대법원은 이를 최종적으로 심사할 권한을 가진다.
                  </div>
                </div>
                <div class="chatting_my_msg">
                  <div class="msg">
                    모든 국민은 근로의 의무를 진다. 국가는 근로의 의무의 내용과 조건을 민주주의원칙에 따라 법률로 정한다.
                  </div>
                  <div class="chatting_profile">
                    <img src="../assets/img/messages-2.jpg" width="30" height="30" alt="Profile" class="rounded-circle">
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
</body>
</html>
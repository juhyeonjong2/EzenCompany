<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View</title>

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

 <!-- Vendor CSS Files -->
 <link href="<%=request.getContextPath()%>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/remixicon/remixicon.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/resources/css/ezReply.css" rel="stylesheet">

  <!-- Predefined Script -->
  <script src="https://cdn.tiny.cloud/1/3bif3ntggq2j5i7kj5tgxwt4wodd6se5f35dq4qu2s1aj007/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>
    
    <!-- ======= Header - Board ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">
    <div class="d-flex align-items-center justify-content-between">
      <a href="home.html" class="logo d-flex align-items-center">
        <i class="bx bx-world"></i> <!-- boxicons-->
        <span class="d-none d-lg-block">EzenCompany</span>
      </a>
      <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">
      <ul class="d-flex align-items-center">

        <!-- 관리자가 아니라면 보이지 않음.-->
        <li class="nav-item">
            <a class="nav-link nav-icon" href="../admin/home.html">
              <i class="bi bi-key"></i>
            </a>
        </li><!-- End Admin Icon-->

        <li class="nav-item dropdown">
          <a class="nav-link nav-icon" href="#" data-bs-toggle="dropdown">
            <i class="bi bi-bell"></i>
            <span class="badge bg-primary badge-number">3</span>
          </a><!-- End Notification Icon -->

          <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications">
            <li class="dropdown-header" style="width: 200px;">
              알림
            </li>

            <li><hr class="dropdown-divider"></li>

            <li class="notification-item">
              <i class="bi bi-exclamation-circle text-warning"></i>
              <div>
                <p><a href='#'>이길동</a>님이 블로그에 댓글을 남기셧습니다.</p>
              </div>
            </li>

            <li><hr class="dropdown-divider"></li>

            <li class="notification-item">
              <i class="bi bi-exclamation-circle text-warning"></i>
              <div>
                <p><a href='#'>박길동</a>님이 게시글에 댓글을 남기셧습니다.</p>
              </div>
            </li>

            <li>
              <hr class="dropdown-divider">
            </li>

            <li class="notification-item">
              <i class="bi bi-exclamation-circle text-warning"></i>
              <div>
                <p><a href='#'>박길동</a>님이 회원님을 언급하셨습니다.</p>
              </div>
            </li>

          </ul><!-- End Notification Dropdown Items -->
        </li><!-- End Notification Nav -->

        <li class="nav-item">
          <a class="nav-link nav-icon" href="#" data-bs-toggle="modal" data-bs-target="#chattingModal">
            <i class="bi bi-chat-left-text"></i>
            <span class="badge bg-success badge-number">3</span>
          </a>
        </li><!-- End Messages Icon -->
        
        <li class="nav-item">
            <a class="nav-link nav-icon search-bar-toggle" href="../board/home.html">
              <i class="bi bi-grid"></i>
            </a>
        </li><!-- End Board Icon-->

        <li class="nav-item">
            <a class="nav-link nav-icon search-bar-toggle" href="../blog/home.html">
              <i class="bi bi-house-door"></i>
            </a>
        </li><!-- End Home Icon-->

      </ul>
    </nav><!-- End Icons Navigation -->

  </header><!-- End Header -->

  <!-- ======= Sidebar - Board======= -->
    <aside id="sidebar" class="sidebar">
      <div class="board_tree_frame mt-5 ms-4 w-75 rounded overflow-y-auto">
        <div class="list-group">
          <a href="list.html" class="list-group-item list-group-item-action active" aria-current="true">
            <i class="bi bi-file-text"></i>
            <span>게시판 1</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            <i class="bi bi-file-text"></i>
            <span>게시판 2</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            <i class="bi bi-file-text"></i>
            <span>게시판 3</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            <i class="bi bi-file-text"></i>
            <span>게시판 4</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            <i class="bi bi-file-text"></i>
            <span>게시판 5</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            <i class="bi bi-file-text"></i>
            <span>게시판 6</span>
          </a>
          <a href="#" class="list-group-item list-group-item-action">
            <i class="bi bi-file-text"></i>
            <span>게시판 7</span>
          </a>
          
        </div>
      </div>
  
    </aside><!-- End Sidebar-->

  <main id="main" class="main">
    <div class="pagetitle container-md mb-3">
      <h1>게시판 1</h1>
    </div><!-- End Page Title -->

    <section class="section container-md">
      <div class="blog_frame">
          <div class="blog_title">
                <div class="d-flex justify-content-center mb-3" >
                  <h2 class="mt-3">${vo.btitle}</h2>
                </div>
                <div class="d-flex justify-content-start ms-5">
                  <img width="25" height="25" src="<%=request.getContextPath()%>/resources/icon/user.png" alt="user_profile">
                  <span class="ms-1">${vo.mid}</span>
                  <span class="ms-2">${vo.bdate}</span>
                </div>      
          </div>

          <div class="blog_top_sep">
            <hr class="mt-0 mb-0 mx-5 w-auto ">
            <div class="d-flex justify-content-end">
              <div class="dropdown me-5">
                  <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    첨부 파일
                  </button>
                  <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="#">첨부파일1.pdf</a></li>
                    <li><a class="dropdown-item" href="#">첨부파일2.png</a></li>
                    <li><a class="dropdown-item" href="#">첨부파일3.pptx</a></li>
                  </ul>
                </div>
            </div>
          </div>

          <div class="blog_content mt-2 mx-5 w-auto">
          	<p>${vo.bcontent}</p>
          </div>

          <!--분할바-->
          <div class="blog_bottom_sep">
            <hr class="mt-2 mx-1 w-auto ">
          </div>

          <div class="blog_reply reply d-flex flex-column">
              <div class="reply_top">
                <div class="ms-3">
                  전체 댓글 <span>4개</span>
                </div>
                <hr class="ms-3 me-1 w-auto border border-secondary border-2 opacity-30">
              </div>
              <div class="reply_body ms-3 me-3">
                <ul class="ezReply" id="blog_reply">
                  <li>
                    <div class="reply_node pb-1 d-flex">
                       <div class="col-1 pt-2">
                          <div class="reply_author">
                            <p class="text-center m-0 t-0">고길동 :</p>
                          </div>
                          <div class="reply_author_me">
                            <!--<p class="text-center m-0 t-0">(작성자)</p>-->
                          </div>
                       </div>
                       <div class="col pt-2">
                          <div class="reply_content">
                            저번 회의에서 저런방식 말고 다른방식으로 하자고 부장님께서 말씀하셨습니다. 그래서 아마 3번줄부터 25번줄은 제거하는게 좋아보여요. <br>
                            <br>
                            그리고 Post.VO 에서 8번줄에 있는 pnum 필드는 왜 만든건가요? 어디서 사용하는지 잘 모르겠어요 <br>
                            점심시간이 되면 제가 찾아갈테니 알려주실 수 있나요? <br>
                            <br>
                            @홍길동
                          </div>
                          <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
                          <div class="d-flex">
                            <div class="col">
                                <!-- 답장 버튼-->
                                <button class="btn btn-secondary btn-sm ms-1">답글</button>
                            </div>
                            <div class="col d-flex justify-content-end ">
                              <!-- 삭제/수정 버튼-->
                              <!--<button class="btn btn-warning btn-sm me-2">수정</button>
                              <button class="btn btn-danger btn-sm me-2">삭제</button>-->
                            </div>
                            
                          </div>
                       </div>
                    </div>
                    <ul>
                        <li>
                          <div class="reply_node pb-1 d-flex">
                            <div class="col-1 pt-2">
                               <div class="reply_author">
                                 <p class="text-center m-0 t-0">홍길동 :</p>
                               </div>
                               <div class="reply_author_me">
                                 <p class="text-center m-0 t-0 text-info-emphasis">(작성자)</p>
                               </div>
                            </div>
                            <div class="col pt-2">
                               <div class="reply_content">
                                 네 알겠습니다.
                               </div>
                               <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
                               <div class="d-flex">
                                 <div class="col">
                                     <!-- 답장 버튼-->
                                     <button class="btn btn-secondary btn-sm ms-1">답글</button>
                                 </div>
                                 <div class="col d-flex justify-content-end ">
                                   <!-- 삭제/수정 버튼-->
                                   <button class="btn btn-warning btn-sm me-2">수정</button>
                                   <button class="btn btn-danger btn-sm me-2">삭제</button>
                                 </div>
                                 
                               </div>
                            </div>
                         </div>
                          <ul>
                            <li>
                              <div class="reply_node pb-1 d-flex">
                                <div class="col-1 pt-2">
                                   <div class="reply_author">
                                     <p class="text-center m-0 t-0">고길동 :</p>
                                   </div>
                                   <div class="reply_author_me">
                                     <!--<p class="text-center m-0 t-0 text-info-emphasis">(작성자)</p>-->
                                   </div>
                                </div>
                                <div class="col pt-2">
                                   <div class="reply_content">
                                     지금 갑니다.
                                   </div>
                                   <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
                                   <div class="d-flex">
                                     <div class="col">
                                         <!-- 답장 버튼-->
                                         <button class="btn btn-secondary btn-sm ms-1">답글</button>
                                     </div>
                                     <div class="col d-flex justify-content-end ">
                                       <!-- 삭제/수정 버튼-->
                                       <!--<button class="btn btn-warning btn-sm me-2">수정</button>
                                       <button class="btn btn-danger btn-sm me-2">삭제</button>-->
                                     </div>
                                     
                                   </div>
                                </div>
                             </div>
                              <ul>
                              </ul>
                          </li>
                        </ul>
                        </li>
                    </ul>
                  </li>
                  <li>
                    <div class="reply_node pb-1 d-flex">
                      <div class="col-1 pt-2">
                         <div class="reply_author">
                           <p class="text-center m-0 t-0">김길동 :</p>
                         </div>
                         <div class="reply_author_me">
                           <!--<p class="text-center m-0 t-0">(작성자)</p>-->
                         </div>
                      </div>
                      <div class="col pt-2">
                         <div class="reply_content">
                          고생하셨습니다.
                         </div>
                         <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
                         <div class="d-flex">
                           <div class="col">
                               <!-- 답장 버튼-->
                               <button class="btn btn-secondary btn-sm ms-1">답글</button>
                           </div>
                           <div class="col d-flex justify-content-end ">
                             <!-- 삭제/수정 버튼-->
                             <!--<button class="btn btn-warning btn-sm me-2">수정</button>
                             <button class="btn btn-danger btn-sm me-2">삭제</button>-->
                           </div>
                           
                         </div>
                      </div>
                    </div>
                    <ul>
                      <li>
                        <div class="reply_node pb-1 d-flex">
                          <div class="col-1 pt-2">
                             <div class="reply_author">
                               <p class="text-center m-0 t-0">홍길동 :</p>
                             </div>
                             <div class="reply_author_me">
                               <p class="text-center m-0 t-0">(작성자)</p>
                             </div>
                          </div>
                          <div class="col pt-2">
                             <div class="reply_content">
                              감사합니다.
                             </div>
                             <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
                             <div class="d-flex">
                               <div class="col">
                                   <!-- 답장 버튼-->
                                   <button class="btn btn-secondary btn-sm ms-1">답글</button>
                               </div>
                               <div class="col d-flex justify-content-end ">
                                 <!-- 삭제/수정 버튼-->
                                 <button class="btn btn-warning btn-sm me-2">수정</button>
                                 <button class="btn btn-danger btn-sm me-2">삭제</button>
                               </div> 
                             </div>
                          </div>
                        </div>
                        <ul>
                        </ul>
                      </li>
                      <li>
                        <div class="reply_node pb-1 d-flex">
                          <div class="col-1 pt-2">
                             <div class="reply_author">
                               <p class="text-center m-0 t-0">한만석 :</p>
                             </div>
                             <div class="reply_author_me">
                               <!--<p class="text-center m-0 t-0">(작성자)</p>-->
                             </div>
                          </div>
                          <div class="col pt-2">
                             <div class="reply_content">
                              고생하셨습니다.
                             </div>
                             <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
                             <div class="d-flex">
                               <div class="col">
                                   <!-- 답장 버튼-->
                                   <button class="btn btn-secondary btn-sm ms-1">답글</button>
                               </div>
                               <div class="col d-flex justify-content-end ">
                                 <!-- 삭제/수정 버튼-->
                                 <!--<button class="btn btn-warning btn-sm me-2">수정</button>
                                 <button class="btn btn-danger btn-sm me-2">삭제</button>-->
                               </div> 
                             </div>
                          </div>
                        </div>
                        <ul>
                        </ul>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
              <div class="reply_input ms-3">
                <div class="form-floating">
                  <input type="text" class="form-control" placeholder="Leave a comment here" id="replyinput" style="width:98%;">
                  <label for="replyinput">댓글을 입력하세요</label>
                </div>
              </div>

          </div>
      </div>
      <div class="blog_buttons mt-3 d-flex justify-content-end">
          <a class="btn btn-primary me-2" role="button" href="modify.do?bno=${vo.bno}">수정</a>
          <button class="btn btn-danger me-2" data-bs-toggle="modal" data-bs-target="#boardRemoveModal">삭제</button>
          <a class="btn btn-secondary" role="button" href="list.do?bindex=${param.bindex}">목록</a>
      </div>
      
    </section>

    <section class="popup">
      <!-- 게시글 삭제 팝업-->
      <div class="modal fade" id="boardRemoveModal" tabindex="-1" data-bs-backdrop="false">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title"></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="delete.do" method="post">
            
            <div class="modal-body">
              게시글을 삭제하시겠습니까?
            </div>
            <div class="modal-footer d-flex align-items-center justify-content-center">
              <button type="button" class="btn btn-danger" data-bs-dismiss="modal" onclick="document.getElementById('frm').submit();">삭제</button>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>
            </form>
          </div>
        </div>
      </div><!-- End Disabled Backdrop Modal-->
	<form action="delete.do" method="post" id="frm">
		<input type="hidden" name="bno" value="${vo.bno }"> 
	</form>
     
    </section>
  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
  <footer id="footer" class="footer">
    <div class="copyright">
      &copy; Copyright <strong><span>EzenCompany</span></strong>. All Rights Reserved
    </div>
    <div class="credits">
      <!-- All the links in the footer should remain intact. -->
      <!-- You can delete the links only if you purchased the pro version. -->
      <!-- Licensing information: https://bootstrapmade.com/license/ -->
      <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
      <!-- Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a> -->
    </div>
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>


    <!-- Vendor JS Files -->
    <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>


    <!-- Template Main JS File -->
    <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/board/write.js"></script>

    <!-- Last JS-->
    <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>


</body>
</html>
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
    
   		<!-- header -->
	<%@ include file="../include/boardHeader.jsp"%>
	<!-- sidebar -->
	<%@ include file="../include/boardSidebar.jsp"%>
      

 

 

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
             <c:if test="${not empty files}">
					  		<div class="dropdown me-5">
					        	<button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">첨부 파일</button>
						        <ul class="dropdown-menu">
						        <c:forEach var="file" items="${files}">
									<li>
										<a class="dropdown-item" href='<c:url value="/board/download/${file.bfno}"/>'>
										${file.bforeignname}
										</a>
									</li>
			          			</c:forEach>
			          				        </ul>
					      	</div>
				      	</c:if>
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
                <ul class="ezReply" id="reply_root">
                
                </ul>
                </div>
					<div class="ezReply_input ms-3 pb-2">
						<div class="d-flex">
					  		<textarea placeholder="댓글을 입력하세요.." onInput="ezReply_onInput(event)" id="ezReply_root_value"></textarea>
					  		<button class="ms-1 me-1 btn btn-secondary" onClick="ezReply_rootSubmit(this)">댓글 등록</button>
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

	 <!-- popup -->
		<%@ include file="../popup/blog.jsp"%>
		
		<!-- catting popup -->
		<%@ include file="../popup/chatting.jsp"%>
	  </main><!-- End #main -->
	  
	  <!-- footer -->
	  <%@ include file="../include/footer.jsp"%>
	
	  <!-- Last -->
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
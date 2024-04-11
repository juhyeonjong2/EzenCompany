<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>modify</title>

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
    <div class="pagetitle container-md">
      <h1>게시글 수정</h1>
    </div><!-- End Page Title -->
	<form action="modify.do" method="post">
	  <input type="hidden" name="bno" value="${vo.bno}">
    <section class="section container-md">
      <div class="input-group mb-3">
        <input type="text" class="form-control"  placeholder="${vo.btitle}">
      </div>
      <div class="input-group mb-3">
        <label class="input-group-text" for="formFileMultiple" >파일첨부</label>
        <input type="file" class="form-control"  id="formFileMultiple" multiple>
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
      <div class="col-auto mb-3">
        <!--tinymce 영역-->
        <textarea id="editor_board_content" name="bcontent">${vo.bcontent}</textarea>
      </div>
      <div class="blog_buttons mt-3 d-flex justify-content-end">
      	<button class="btn btn-primary me-2">수정</button>
      </div>
    </section>
  </main><!-- End #main -->
	</form>
	
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
    <script src="<%=request.getContextPath()%>/resources/js/board/modify.js"></script>

    <!-- Last JS-->
    <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
     <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
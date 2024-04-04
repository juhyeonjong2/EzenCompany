<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

	<!-- Google Fonts -->
	<link href="https://fonts.gstatic.com" rel="preconnect">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
	
	<!-- Vendor CSS Files --> <!-- request.get이거 적는이유 -> 이게 없으면 컨트롤러간 이동할 경우 상대경로라서 경로를 못찾게 된다(/member/resources이런느낌인듯) -->
	<link href="<%=request.getContextPath()%>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/remixicon/remixicon.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">

	<!-- Template Main CSS File -->
	<link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/ezReply.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/chatting.css" rel="stylesheet">
  
	<!-- Predefined Script -->
	<script src="https://cdn.tiny.cloud/1/3bif3ntggq2j5i7kj5tgxwt4wodd6se5f35dq4qu2s1aj007/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/vendor/zTree/js/jquery.ztree.all.js"></script>
	
	
</head>
<body>
	<!-- header -->
	<%@ include file="../include/blogHeader.jsp"%>
	<!-- sidebar -->
	<%@ include file="../include/blogSidebar.jsp"%>

  <main id="main" class="main">
  	
		<section class="section container-md">
		<form action="writeOk" method="post">
	      <div class="input-group mb-3">
	        <input type="text" class="form-control"  placeholder="제목을 입력하세요.." name="title">
	      </div>
	      <div class="input-group mb-3">
	        <label class="input-group-text" for="formFileMultiple" >파일첨부</label>
	        <input type="file" class="form-control"  id="formFileMultiple" multiple>
	      </div>
	      <div class="col-auto mb-3 d-flex" >
	        <div class="col d-flex justify-content-start">
	          <div>
	            <select class="form-select" aria-label="Default select example">
	              <option selected value="1">기본</option>
	              <option value="2">스프링 일지</option>
	              <option value="3">임시</option>
	              <option value="4">임시/임시 폴더</option>
	            </select>
	          </div>
	          <div class="m-1">
	            폴더 추가하기
	          </div>
	          <div class="m-1">
	            <a class="link-dark" href="#" data-bs-toggle="modal" data-bs-target="#folderAddModal">
	              <i class="bi bi-plus-square"></i>
	            </a>  
	          </div>
	        </div>
	        <div class="col d-flex justify-content-end">
	          <div class="form-check form-switch form-check-reverse">
	            <label class="form-check-label" for="flexSwitchCheckDefault">비공개</label>
	            <button class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault"></button>
	          </div>
	        </div>
	      </div>
	      <div class="col-auto mb-3">
	        <!--tinymce 영역-->
	        <textarea id="editor_blog_content" name="content"></textarea>
	      </div>
	      <div class="blog_buttons mt-3 d-flex justify-content-end">
	        <button class="btn btn-primary me-2">등록</button>
	      </div>
	      </form>
	    </section>
	
	
	<!-- popup -->
	<%@ include file="../popup/blogWrite.jsp"%>
	
	<!-- catting popup -->
	<%@ include file="../popup/chatting.jsp"%>
  </main><!-- End #main -->
  
  <!-- footer -->
  <%@ include file="../include/footer.jsp"%>

  <!-- Last -->
  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Template Main JS File -->
  <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/blog/base.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/blog/write.js"></script>

  <!-- Last JS-->
  <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
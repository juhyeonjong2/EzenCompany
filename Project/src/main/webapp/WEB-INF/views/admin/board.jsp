<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 목록</title>

	<!-- Google Fonts -->
	<link href="https://fonts.gstatic.com" rel="preconnect">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

	<!-- Vendor CSS Files -->
	<link href="<%=request.getContextPath()%>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/remixicon/remixicon.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/simple-datatables/style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/vendor/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
	
	<!-- Template Main CSS File -->
	<link href="<%=request.getContextPath()%>/resources/css/style_admin.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/chatting.css" rel="stylesheet">
	
  	<!-- Predefined Script -->
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/vendor/zTree/js/jquery.ztree.all.js"></script>

</head>
<body>
	<%@ include file="../include/adminHeader.jsp"%>
	<%@ include file="../include/adminSidebar.jsp"%>

	<main id="main" class="main">
		<section class="section">
		  <div class="row">
		    <div class="col-lg-12">
		      <div class="card">
		        <div class="card-body">
		          <div class="d-flex justify-content-between align-items-center">
		              <h5 class="card-title"><b>게시판 목록</b></h5>
		              <button type="button" class="btn btn-primary h-50" data-bs-toggle="modal" data-bs-target="#boardAddModal">+ 게시판 추가</button>
		          </div>
		
		          <!-- Table with stripped rows -->
		          <table class="table datatable">
		            <thead>
		              <tr>
		                <th>게시판 이름</th>
		                <th>읽기 권한</th>
		                <th>쓰기 권한</th>
		                <th>&nbsp;</th>
		              </tr>
		            </thead>
		            <tbody>
		            <tr>
		              <td>공지사항</td>
		              <td>
		                  <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="All">모두</button>
		              </td>
		              <td>
		                  <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Admin Only">없음</button>
		              </td>
		              <td>
		                <a class="link-dark" href="#" onclick="return false;" data-bs-toggle="modal" data-bs-target="#boardEditModal" data-bs-no="1">
		                  <i class="bi bi-three-dots-vertical"></i>
		                </a>
		              </td>
		            </tr>
		
		            <tr>
		                <td>자유 게시판</td>
		                <td>
		                    <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="All">모두</button>
		                </td>
		                <td>
		                    <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top"  title="All">모두</button>
		                </td>
		                <td>
		                  <a class="link-dark" href="#" onclick="return false;" data-bs-toggle="modal" data-bs-target="#boardEditModal" data-bs-no="2">
		                    <i class="bi bi-three-dots-vertical"></i>
		                  </a>
		                </td>
		            </tr>
		              
		            <tr>
		              <td>아트팀 게시판</td>
		              <td>
		                  <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top"  title="2D그래픽, 3D그래픽, 배경">직무</button>
		                  <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top"  title="그래픽 디자이너">직군</button>
		                  <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top"  title="대표이사, 상무, 이사, 부장, 과장, 대리, 사원">직위</button>
		              </td>
		              <td>
		                  <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top"  title="2D그래픽, 3D그래픽, 배경">직무</button>
		              </td>
		              <td>
		                <a class="link-dark" href="#" onclick="return false;" data-bs-toggle="modal" data-bs-target="#boardEditModal" data-bs-no="3">
		                  <i class="bi bi-three-dots-vertical"></i>
		                </a>
		              </td>
		            </tr>
		
		            </tbody>
		          </table>
		          <!-- End Table with stripped rows -->
		
		        </div>
		      </div>
		
		    </div>
		  </div>
		</section>

		<!-- popup -->
		<%@ include file="../popup/adminBoard.jsp"%>
		
		<!-- catting popup -->
		<%@ include file="../popup/chatting.jsp"%>
	</main><!-- End #main -->

	<!-- Last -->
	<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

    <!-- Vendor JS Files -->
    <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>

    <!-- Template Main JS File -->
    <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/admin/board.js"></script>

    <!-- Last JS-->
    <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script> 
  

</body>
</html>

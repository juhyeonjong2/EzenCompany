<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>속성</title>

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
	<link href="<%=request.getContextPath()%>/resources/css/style_admin.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/chatting.css" rel="stylesheet">

	<!-- Predefined Script -->
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>
	<%@ include file="../include/adminHeader.jsp"%>
	<%@ include file="../include/adminSidebar.jsp"%>
	
	<main id="main" class="main">
		<div class="pagetitle">
		    <h1>분류</h1>
		</div><!-- End Page Title -->
	    
		<section class="section">
		  <div class="row row-cols-1 row-cols-md-5 g-1">
		  	<c:forEach var="category" items="${categorys}">
				<div class="col">
				  <div class="card text-bg-primary m-1">
				    <a class="link-light" href="<%=request.getContextPath()%>/admin/attributes/${category.cidx}">
				    	<p class="text-center m-5 fs-5">
				    	${category.value}
				    	</p>
			    	</a>
				  </div>
				</div>  		
			</c:forEach>
		  </div>
		</section>

		<!-- catting popup -->
		<%@ include file="../popup/chatting.jsp"%>
	
	</main><!-- End #main -->

	<!-- Last -->
	<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

    <!-- Vendor JS Files -->
    <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>


    <!-- Template Main JS File -->
    <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
</body>
</html>
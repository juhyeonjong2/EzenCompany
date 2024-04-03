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
<%-- 	<link href="<%=request.getContextPath()%>/resources/vendor/simple-datatables/style.css" rel="stylesheet"> --%>
 
	<!-- Template Main CSS File -->
	<link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/ezReply.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/chatting.css" rel="stylesheet">
  
	<!-- Predefined Script -->
	<script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/vendor/zTree/js/jquery.ztree.all.js"></script>
	
	
</head>
<body>
	<%@ include file="../include/blogHeader.jsp"%>
	<%@ include file="../include/blogSidebar.jsp"%>

  <main id="main" class="main">
    <section class="section">
    	
    </section>

    <section class="popup">
		<%--  <%@ include file="../popup/employeeRegistration.jsp"%> --%> 
	</section>
	
	<section class="chatting_popup">
		  <%@ include file="../popup/chatting.jsp"%>
	</section>

  </main><!-- End #main -->
  
  <%@ include file="../include/footer.jsp"%>

  <!-- Last -->
  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>

  <!-- Template Main JS File -->
  <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/admin/employee.js"></script>

  <!-- Last JS-->
  <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
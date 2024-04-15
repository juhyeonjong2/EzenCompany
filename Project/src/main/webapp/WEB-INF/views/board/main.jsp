<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
  <link href="<%=request.getContextPath()%>/resources/css/chatting.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/resources/css/noti.css" rel="stylesheet">

  <!-- Predefined Script -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>

	<%@ include file="../include/boardHeader.jsp"%>
	<%@ include file="../include/boardSidebar.jsp"%>
	<!-- catting popup -->
	<%@ include file="../include/socketHeader.jsp"%>
	<%@ include file="../popup/chatting.jsp"%>
	
	<main id="main" class="main">
    <section class="section">
      <div class="row row-cols-1 row-cols-md-3 g-1">
      
		 <!-- 반복문 시작 -->
		 <c:forEach var="boardType" items="${boardType}">
	        <div class="col">
	            <div class="card m-1">
	              <div class="card-body boardType">
	                <div class="d-flex justify-content-between">
	                  <h5 class="card-title">${boardType.btname}</h5>
	                  <span class="m-3"><a href="list.do?bindex=${boardType.bindex}">+ 더보기</a></span>
	                </div>
	                <hr class="m-0">
	                <table class="table">
	                  <tbody>
	                  
	                  <!-- 반복문 -->
	                  <c:forEach var="board" items="${board}">
	                  <c:if test="${boardType.bindex eq board.bindex}">
	                    <tr>
	                      <td>
	                          <a href="list.do?bno=${board.bno}">
	                            <p class="mini_table_title">${board.btitle}</p>
	                          </a>
	                      </td>
	                      <td class="text-end">${board.bdate}</td>
	                    </tr>
	                   </c:if>
	                   </c:forEach>
	                  <!-- 반복문 -->
	                 
	                  </tbody>
	                </table>
	              </div>
	          </div>
	        </div>
		</c:forEach>
        
        
      </div>
    </section>
    <!-- 이 위치에 채팅팝업이 오면 될거같음 -->
  </main><!-- End #main -->
  
  <!-- footer -->
  <%@ include file="../include/footer.jsp"%>

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>


    <!-- Vendor JS Files -->
    <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>


    <!-- Template Main JS File -->
    <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/board/board.js"></script>


    <!-- Last JS-->
    <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>
</body>
</html>
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

  <!-- Predefined Script -->
  <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>

</head>
<body>

	<%@ include file="../include/boardHeader.jsp"%>
	<%@ include file="../include/boardSidebar.jsp"%>

	<main id="main" class="main">
    <section class="section">
      <div class="row row-cols-1 row-cols-md-3 g-1">

		<!-- 이걸 반복문을 사용하면 될 듯하다-->
		<!-- 
			해야할 것
			로그인 한 사람의 사원옵션을 가져와서 
			reader에 걸치는 경우를 name만 가져와서 뿌려야함 json.name
			모든 게시글옵션  where reader->'$."1(cidx전부만큼 반복)"' = 로그인한 사람의 cidx[i] aidx(cidx와 맞는 aidx)
			 -> 이걸 전부 나열해 준다 
			 -> 여기에 걸치는 bindex를(외래키) 구해 그걸 가진 board의 최신 게시글 5개를 긁어온다 
			+ 더보기 눌러서 들어가면 나열한 정보에 맞는 게시글만 보여야 한다
			게시판은 관리자가 생성할 수 있으므로 vo를 사용하는것은 금지한다
			
			문제점 : json타입으로 가져온게 좀 이상하게 가져와져서 내일 멘토님에게 값만 잘 구해내는법 or 만약 더 편한 db설계가 있다면 물어볼 예정 
			 		 
		 -->
		 <div>
		 	    <%--1차반복문 사용(분류 나열) --%>
                <c:forEach var="json" items="${json}">
	                <div class="row mb-1">
	                  <div>
	                    <label class="col-sm-4 col-form-label">${json.b}</label>
	                    <div class="col">
	                      <select class="form-select" aria-label="Default select example" name="${json.a}">
	                      
	                      <%-- 2차반복문으로 속성과 분류의 코드를 비교하고 맞는것만 나열--%>

	                      			<option>${json.a}</option>

	                        
	                      </select>
	                    </div>
	                  </div>
	                </div>
                </c:forEach>
		 </div>
		 <!--  -->
        <div class="col">
            <div class="card m-1">
              <div class="card-body">
                <div class="d-flex justify-content-between">
                  <h5 class="card-title">게시판 제목1</h5>
                  <span class="m-3"><a href="list.html">+ 더보기</a></span>
                </div>
                <hr class="m-0">
                <table class="table">
                  <tbody>
                    <tr>
                      <td>
                          <a href="#">
                            <p class="mini_table_title">제목11111111111111111111111111111111111111111111111111111</p>
                          </a>
                      </td>
                      <td class="text-end">2024.03.29</td>
                    </tr>
                    <tr>
                      <td>
                          <a href="#">
                            <p class="mini_table_title">제목2</p>
                          </a>
                      </td>
                      <td class="text-end">2024.03.29</td>
                    </tr>
                    <tr>
                      <td>
                          <a href="#">
                            <p class="mini_table_title">제목3</p>
                          </a>
                      </td>
                      <td class="text-end">2024.03.29</td>
                    </tr>
                    <tr>
                      <td>
                          <a href="#">
                            <p class="mini_table_title">제목4</p>
                          </a>
                      </td>
                      <td class="text-end">2024.03.29</td>
                    </tr>
                    <tr>
                      <td>
                          <a href="#">
                            <p class="mini_table_title">제목5</p>
                          </a>
                      </td>
                      <td class="text-end">2024.03.29</td>
                    </tr>
                  
                  </tbody>
                </table>
              </div>
          </div>
        </div>

        
        
      </div>
    </section>
    <!-- 이 위치에 채팅팝업이 오면 될거같음 -->
  </main><!-- End #main -->
  
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
    <script src="<%=request.getContextPath()%>/resources/js/board/board.js"></script>


    <!-- Last JS-->
    <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>
</body>
</html>
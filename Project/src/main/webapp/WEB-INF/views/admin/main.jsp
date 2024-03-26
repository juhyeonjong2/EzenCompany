<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!-- 우선순위 1.계정리스트를 전부 들고온다  (컨트롤러에서 모달에 넣은 뒤 for문 돌린다)
    		   2. 회원가입하지 않은 직원은 메일발송으로 나오게 한다
    		   3. 사원 등록로직을 작성한다
    		   4. 메일발송 로직을 작성한다 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

 <!-- Vendor CSS Files --> <!-- request.get이거 적는이유 -> 이게 없으면 컨트롤러간 이동할 경우 상대경로라서 경로를 못찾게 된다(/member/resources이런느낌인듯) -->
 <link href="<%=request.getContextPath()%>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/remixicon/remixicon.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/simple-datatables/style.css" rel="stylesheet">
 
  <!-- Template Main CSS File -->
  <link href="<%=request.getContextPath()%>/resources/css/style_admin.css" rel="stylesheet">
  

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
                  <h5 class="card-title"><b>사원 목록</b></h5>
                  <button type="button" class="btn btn-primary h-50" data-bs-toggle="modal" data-bs-target="#employeeAddModal" >+ 사원 등록</button>
                  <!--<p>Add lightweight datatables to your project with using the <a href="https://github.com/fiduswriter/Simple-DataTables" target="_blank">Simple DataTables</a> library. Just add <code>.datatable</code> class name to any table you wish to conver to a datatable. Check for <a href="https://fiduswriter.github.io/simple-datatables/demos/" target="_blank">more examples</a>.</p>-->
              </div>

              <!-- Table with stripped rows -->
              <table class="table datatable">
                <thead>
                  <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>권한</th>
                    <th>상태</th>
                    <th data-type="date" data-format="YYYY/DD/MM">Start Date</th>
                    <th>&nbsp;</th>
                  </tr>
                </thead>
                <tbody>
                <c:forEach var="vo" items="${list}">
                  <tr>
                      <td>${vo.mid}</td>
                      <td>${vo.mname}</td>
                      <td>${vo.email}</td>
                      <td>${vo.authority}</td>
                      <td><img src="<%=request.getContextPath()%>/resources/icon/active.png" alt="active"></td>
                      <td>${vo.joindate}</td>
                      <td>
                        <a class="link-dark" href="#" onclick="return false;" data-bs-toggle="modal" data-bs-target="#employeeDetailModal" data-bs-mno="1">
                          <i class="bi bi-three-dots-vertical"></i>
                        </a>
                      </td>
                   </tr>
                  </c:forEach>
				<!-- tr을 반복문 사용하면 됨 -->
                  
                </tbody>
              </table>
              <!-- End Table with stripped rows -->

            </div>
          </div>

        </div>
      </div>
    </section>

    <section class="popup">
		  <%@ include file="../popup/employeeRegistration.jsp"%>
	</section>

  </main><!-- End #main -->

  <!-- Last -->
  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>

  <!-- Template Main JS File -->
  <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/admin/employee.js"></script>

  <!-- Last JS-->
  <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
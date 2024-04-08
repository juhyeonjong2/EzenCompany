<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

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
          <a class="nav-link nav-icon" href="#">
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
          <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
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
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
                <h5 class="card-title"><b>게시판 1</b></h5>  <!-- 여기부분도 DB 받아야됨 -->
                <a class="btn btn-primary h-50" href="write.do?bindex=${param.bindex}" role="button">+ 글쓰기</a>
                
            </div>

            <!-- Table with stripped rows -->
            <table class="table datatable">
              <thead>
                <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th data-type="date" data-format="YYYY/DD/MM">날짜</th>
                  <th>작성자</th>
                  <th>조회</th>
                </tr>
              </thead>
              <tbody>
             	<c:forEach items="${list }" var="vo">
	 			<tr>
	 				<td>${vo.bno}</td>
	 				<td><a href='view.do?bno=${vo.bno}'>${vo.btitle}</a></td>
	 				<td>${vo.bdate}</td>
	 				<td>${vo.mid}</td>
	 				<td>${vo.bno}</td>
	 			 <!--  	<td>${vo.bhit} 몰라 물어봐야딩--> 
	 			</tr>
	 			
	 			<!--  	 <tr>
                  <td>10</td>
                  <td><a href="view.html">글제목 입니다.</a></td>
                  <td>2024.03.29</td>
                  <td>홍길동</td>
                  <td>9999</td>
                </tr>  -->
	 		</c:forEach>
              </tbody>
            </table>
            <!-- End Table with stripped rows -->

          </div>
        </div>

      </div>
    </div>
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
    <script src="<%=request.getContextPath()%>/resources/js/board/list.js"></script>


    <!-- Last JS-->
    <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
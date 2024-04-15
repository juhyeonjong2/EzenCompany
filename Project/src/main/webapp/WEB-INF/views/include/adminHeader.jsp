<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	  <!-- ======= Header - Admin ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">
    <div class="d-flex align-items-center justify-content-between">
      <a href="home.html" class="logo d-flex align-items-center">
        <i class="bi bi-wrench"></i>
        <span class="d-none d-lg-block">Admin</span>
      </a>
      <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">
      <ul class="d-flex align-items-center">
        <li class="nav-item">
            <a class="nav-link nav-icon" href="<%=request.getContextPath()%>/admin/home">
              <i class="bi bi-key"></i>
            </a>
        </li><!-- End Admin Icon-->

		<!-- 알림 -->
        <%@ include file="../include/notification.jsp"%>

        <li class="nav-item">
          <a class="nav-link nav-icon chatNoti" href="#" data-bs-toggle="modal" data-bs-target="#chattingModal">
            <i class="bi bi-chat-left-text"></i>
          </a>
        </li><!-- End Messages Icon -->
        
        <li class="nav-item">
            <a class="nav-link nav-icon search-bar-toggle" href="<%=request.getContextPath()%>/board/home">
              <i class="bi bi-grid"></i>
            </a>
        </li><!-- End Board Icon-->

        <li class="nav-item">
            <a class="nav-link nav-icon search-bar-toggle" href="<%=request.getContextPath()%>/blog/home">
              <i class="bi bi-house-door"></i>
            </a>
        </li><!-- End Home Icon-->

      </ul>
    </nav><!-- End Icons Navigation -->

  </header><!-- End Header -->
</body>
</html>
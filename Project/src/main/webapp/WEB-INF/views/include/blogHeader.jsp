<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
  <!-- ======= Header - Blog ======= -->
<header id="header" class="header fixed-top d-flex align-items-center">
  <div class="d-flex align-items-center justify-content-between">
    <a href="<%=request.getContextPath()%>/blog/home" class="logo d-flex align-items-center">
      <i class="bi bi-house-door"></i>
      <span class="d-none d-lg-block">Home</span>
    </a>
    <i class="bi bi-list toggle-sidebar-btn"></i>
  </div><!-- End Logo -->

  <nav class="header-nav ms-auto">
    <ul class="d-flex align-items-center">
<% if(request.isUserInRole("ROLE_ADMIN")){ %>
      <!-- 관리자가 아니라면 보이지 않음.-->
      <li class="nav-item">
          <a class="nav-link nav-icon" href="<%=request.getContextPath()%>/admin/home">
            <i class="bi bi-key"></i>
          </a>
      </li><!-- End Admin Icon-->
<% } %>

		<!-- 알림 -->
        <%@ include file="../include/notification.jsp"%>


      <li class="nav-item">
        <a class="nav-link nav-icon" href="#" data-bs-toggle="modal" data-bs-target="#chattingModal">
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
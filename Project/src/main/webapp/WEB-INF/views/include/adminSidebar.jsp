<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	  <!-- ======= Sidebar - Admin ======= -->
  <aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
            <a class="nav-link collapsed" href="<%=request.getContextPath()%>/admin/home">
                <i class="bi bi-people"></i>
                <span>사원 목록</span>
            </a>
        </li><!-- End Employee Page Nav  -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="<%=request.getContextPath()%>/admin/board">
                <i class="bi bi-columns-gap"></i>
                <span>게시판 목록</span>
            </a>
        </li><!-- End board list Page Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="<%=request.getContextPath()%>/admin/category">
                <i class="bi bi-layers"></i>
                <span>분류</span>
            </a>
        </li><!-- End category  Page Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="<%=request.getContextPath()%>/admin/attribute">
                <i class="bi bi-puzzle"></i>
                <span>속성</span>
            </a>
        </li><!-- End attribute Page Nav -->

<!--    <li class="nav-heading">Pages</li> -->
    </ul>

  </aside><!-- End Sidebar-->
</body>
</html>
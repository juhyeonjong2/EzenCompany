<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
 <!-- ======= Sidebar ======= -->
 <aside id="sidebar" class="sidebar">
   <ul class="sidebar-nav" id="sidebar-nav">
       <li class="nav-item">
           <a class="nav-link collapsed" href="<%=request.getContextPath()%>/blog/home">
               <i class="bi bi-house-door"></i>
               <span>홈 화면</span>
           </a>
       </li><!-- End Employee Page Nav  -->

       <li class="nav-item">
         <a class="nav-link collapsed" href="<%=request.getContextPath()%>/blog/write">
           <i class="bi bi-journal-plus"></i>
             <span>블로그 등록</span>
         </a>
       </li><!-- End board list Page Nav -->

       <li class="nav-item">
         <a class="nav-link collapsed" href="<%=request.getContextPath() %>/logout">
           <i class="bi bi-person-dash"></i>
             <span>로그아웃</span>
         </a>
       </li><!-- End category  Page Nav -->
   </ul>

   <div class="blog_tree_frame mt-5 ms-4 w-75 rounded overflow-y-auto">
     <ul id="blog_tree" class="ztree"></ul>
   </div>

 </aside><!-- End Sidebar-->
</body>
</html>
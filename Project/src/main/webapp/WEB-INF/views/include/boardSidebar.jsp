<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	  <!-- ======= Sidebar - Board======= -->
    <aside id="sidebar" class="sidebar">
      <div class="board_tree_frame mt-5 ms-4 w-75 rounded overflow-y-auto">
        <div class="list-group">
        
        <!-- 반복문 시작 -->
        <c:forEach var="boardType" items="${boardType}">
          <a href="list.do?bindex=${boardType.bindex}" class="list-group-item list-group-item-action active" aria-current="true">
            <i class="bi bi-file-text"></i>
            <span>${boardType.btname}</span>
          </a>
        </c:forEach>
        <!-- 반복문 끝 -->
        </div>
      </div>
  
    </aside><!-- End Sidebar-->
</body>
</html>
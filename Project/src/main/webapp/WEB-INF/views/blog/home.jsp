<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	<link href="<%=request.getContextPath()%>/resources/vendor/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
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
	<!-- header -->
	<%@ include file="../include/blogHeader.jsp"%>
	<!-- sidebar -->
	<%@ include file="../include/blogSidebar.jsp"%>

  <main id="main" class="main">
  	<input type="hidden" id="inputMno" value="${mno}"> 
  	<input type="hidden" id="inputBno" value="${bno}">
  
	<div class="pagetitle container-md">
		<h1 class="mb-3">${blogSubject}</h1>
	    <div class="accordion" id="accordionExample">
			<c:if test="${not empty retiredEmployees}">
			    <div class="accordion-item">
			      <h2 class="accordion-header">
			        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
			         	 퇴사자 블로그
			        </button>
			      </h2>
					<div id="collapseOne" class="accordion-collapse collapse">
						<div class="accordion-body">
							 <c:forEach var="user" items="${retiredEmployees}">
							 	    <a href="<%=request.getContextPath()%>/blog/other/${user.mno}">${user.mname}</a>
							 </c:forEach>
						</div>
					</div>
				</div>
	    	</c:if>
			<c:forEach var="entry" items="${blogUsers}">
				<div class="accordion-item">
	        		<h2 class="accordion-header">
	          			<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
							${entry.key} 블로그
	          			</button>
          			</h2>
          			<div id="collapseTwo" class="accordion-collapse collapse show">
			          <div class="accordion-body">
			          	<c:forEach var="user" items="${entry.value}">
			          		 <a href="<%=request.getContextPath()%>/blog/other/${user.mno}">${user.mname}</a>
			          	</c:forEach>
			          </div>
		          </div>
	          </div>
			</c:forEach>
		</div>
	</div> 
	<!-- End Page Title -->
    
 	<section class="section container-md">
	 	<c:choose>
			<c:when test="${empty vo}">
				블로그를 작성하세요.
			</c:when>
	    	<c:otherwise>
				<div class="blog_frame">
					<div class="blog_title">
					           <div class="d-flex justify-content-center mb-3" >
					             <h2 class="mt-3">${vo.bgtitle}</h2>
					      </div>
					      <div class="d-flex justify-content-start ms-5">
					        <img width="25" height="25" src="${profileImage}" alt="user_profile">
					        <span class="ms-1">${writer}</span>
					        <span class="ms-2">${vo.bgdate}</span>
					      </div>      
					</div>
					
					<div class="blog_top_sep">
					  <hr class="mt-0 mb-0 mx-5 w-auto ">
					  <div class="d-flex justify-content-end">
					  	<c:if test="${not empty files}">
					  		<div class="dropdown me-5">
					        	<button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">첨부 파일</button>
						        <ul class="dropdown-menu">
						        <c:forEach var="file" items="${files}">
									<li>
										<a class="dropdown-item" href='<c:url value="/blog/download/${file.bgfno}"/>'>
										${file.bgforeignname}
										</a>
									</li>
			          			</c:forEach>
						        </ul>
					      	</div>
				      	</c:if>
					  </div>
					</div>
					
					<div class="blog_content mt-2 mx-5 w-auto">
					  ${vo.bgcontent}
					</div>
					
					<!--분할바-->
					<div class="blog_bottom_sep">
					  <hr class="mt-2 mx-1 w-auto ">
					</div>
					
					<div class="blog_reply reply d-flex flex-column">
					    <div class="reply_top">
							<div class="ms-3">
							   	전체 댓글 <span id="ezReply_comment_count">4</span>개
							</div>
							<hr class="ms-3 me-1 w-auto border border-secondary border-2 opacity-30">
					    </div>
					    
				    	<div class="reply_body ms-3 me-3">
					      	<ul class="ezReply" id="reply_root">
					      	<!--  reply  -->
					        </ul>
				  		</div>
				  		
				  		<div class="ezReply_input ms-3 pb-2">
				    		<div class="d-flex">
				    			<textarea placeholder="댓글을 입력하세요.." onInput="ezReply_onInput(event)" id="ezReply_root_value"></textarea>
				    			<button class="ms-1 me-1 btn btn-secondary" onClick="ezReply_rootSubmit(this)">댓글 등록</button>
			        		</div>
				       	</div>
				       	
				   </div> <!--  blog_reply -->
				</div>
				
				<div class="blog_buttons mt-3 d-flex justify-content-end">
					<a class="btn btn-primary me-2" role="button" href="modify.html">수정</a>
					<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#blogRemoveModal">삭제</button>
				</div>
				
				
				<div clsas="blog_search mt-5">
					<div class="input-group mb-3 mt-3 w-25 mx-auto">
					  <input type="text" class="form-control" placeholder="검색.." aria-label="blog search">
					  <span class="input-group-text m-0">
					    <a href="search.html" onClick="alert('검색!'); return true;">
					        <i class="bi bi-search"></i>
					      </a>
					    </span>
					</div>
				</div>
	    	</c:otherwise>
		</c:choose>
    </section>


	<!-- popup -->
	<%@ include file="../popup/blog.jsp"%>
	
	<!-- catting popup -->
	<%@ include file="../popup/chatting.jsp"%>
  </main><!-- End #main -->
  
  <!-- footer -->
  <%@ include file="../include/footer.jsp"%>

  <!-- Last -->
  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script> -->
  <script src="<%=request.getContextPath()%>/resources/js/ezReply.js"></script>
	
  <!-- Template Main JS File -->
  <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/blog/base.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/blog/blog.js"></script>

  <!-- Last JS-->
  <script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
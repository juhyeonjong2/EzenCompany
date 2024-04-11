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
	<!-- 소켓 -->
	<%@ include file="../include/socketHeader.jsp"%>
	<!-- header -->
	<%@ include file="../include/blogHeader.jsp"%>
	<!-- sidebar -->
	<%@ include file="../include/blogSidebar.jsp"%>

  <main id="main" class="main">
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
							 <c:forEach var="vo" items="${retiredEmployees}">
							 	    <a href="<%=request.getContextPath()%>/blog/${vo.blogHome}">${vo.mname}</a>
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
			          		 <a href="<%=request.getContextPath()%>/blog/${user.blogHome}">${user.mname}</a>
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
					    <div class="dropdown me-5">
					        <button class="btn dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
					          첨부 파일
					        </button>
					        <ul class="dropdown-menu">
					          <li><a class="dropdown-item" href="#">첨부파일1.pdf</a></li>
					          <li><a class="dropdown-item" href="#">첨부파일2.png</a></li>
					          <li><a class="dropdown-item" href="#">첨부파일3.pptx</a></li>
					        </ul>
					      </div>
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
				        전체 댓글 <span>4개</span>
				      </div>
				      <hr class="ms-3 me-1 w-auto border border-secondary border-2 opacity-30">
				    </div>
				    <div class="reply_body ms-3 me-3">
				      <ul class="ezReply" id="blog_reply">
				        <li>
				          <div class="reply_node pb-1 d-flex">
				             <div class="col-1 pt-2">
				                <div class="reply_author">
				                  <p class="text-center m-0 t-0">고길동 :</p>
				                </div>
				                <div class="reply_author_me">
				                  <!--<p class="text-center m-0 t-0">(블로그 주인)</p>-->
				              </div>
				           </div>
				           <div class="col pt-2">
				              <div class="reply_content">
				                저번 회의에서 저런방식 말고 다른방식으로 하자고 부장님께서 말씀하셨습니다. 그래서 아마 3번줄부터 25번줄은 제거하는게 좋아보여요. <br>
				                <br>
				                그리고 Post.VO 에서 8번줄에 있는 pnum 필드는 왜 만든건가요? 어디서 사용하는지 잘 모르겠어요 <br>
				                점심시간이 되면 제가 찾아갈테니 알려주실 수 있나요? <br>
				                <br>
				                @홍길동
				              </div>
				              <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
				              <div class="d-flex">
				                <div class="col">
				                    <!-- 답장 버튼-->
				                    <button class="btn btn-secondary btn-sm ms-1">답글</button>
				                </div>
				                <div class="col d-flex justify-content-end ">
				                  <!-- 삭제/수정 버튼-->
				                  <!--<button class="btn btn-warning btn-sm me-2">수정</button>
				                  <button class="btn btn-danger btn-sm me-2">삭제</button>-->
				                </div>
				                
				              </div>
				           </div>
				        </div>
				        <ul>
				            <li>
				              <div class="reply_node pb-1 d-flex">
				                <div class="col-1 pt-2">
				                   <div class="reply_author">
				                     <p class="text-center m-0 t-0">홍길동 :</p>
				                   </div>
				                   <div class="reply_author_me">
				                     <p class="text-center m-0 t-0 text-info-emphasis">(블로그 주인)</p>
				                   </div>
				                </div>
				                <div class="col pt-2">
				                   <div class="reply_content">
				                     네 알겠습니다.
				                   </div>
				                   <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
				                   <div class="d-flex">
				                     <div class="col">
				                         <!-- 답장 버튼-->
				                         <button class="btn btn-secondary btn-sm ms-1">답글</button>
				                     </div>
				                     <div class="col d-flex justify-content-end ">
				                       <!-- 삭제/수정 버튼-->
				                       <button class="btn btn-warning btn-sm me-2">수정</button>
				                       <button class="btn btn-danger btn-sm me-2">삭제</button>
				                     </div>
				                     
				                   </div>
				                </div>
				             </div>
				              <ul>
				                <li>
				                  <div class="reply_node pb-1 d-flex">
				                    <div class="col-1 pt-2">
				                       <div class="reply_author">
				                         <p class="text-center m-0 t-0">고길동 :</p>
				                       </div>
				                       <div class="reply_author_me">
				                         <!--<p class="text-center m-0 t-0 text-info-emphasis">(블로그 주인)</p>-->
				                       </div>
				                    </div>
				                    <div class="col pt-2">
				                       <div class="reply_content">
				                         지금 갑니다.
				                       </div>
				                       <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
				                       <div class="d-flex">
				                         <div class="col">
				                             <!-- 답장 버튼-->
				                             <button class="btn btn-secondary btn-sm ms-1">답글</button>
				                         </div>
				                         <div class="col d-flex justify-content-end ">
				                           <!-- 삭제/수정 버튼-->
				                           <!--<button class="btn btn-warning btn-sm me-2">수정</button>
				                           <button class="btn btn-danger btn-sm me-2">삭제</button>-->
				                         </div>
				                         
				                       </div>
				                    </div>
				                 </div>
				                  <ul>
				                  </ul>
				              </li>
				            </ul>
				            </li>
				        </ul>
				      </li>
				      <li>
				        <div class="reply_node pb-1 d-flex">
				          <div class="col-1 pt-2">
				             <div class="reply_author">
				               <p class="text-center m-0 t-0">김길동 :</p>
				             </div>
				             <div class="reply_author_me">
				               <!--<p class="text-center m-0 t-0">(블로그 주인)</p>-->
				             </div>
				          </div>
				          <div class="col pt-2">
				             <div class="reply_content">
				              고생하셨습니다.
				             </div>
				             <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
				             <div class="d-flex">
				               <div class="col">
				                   <!-- 답장 버튼-->
				                   <button class="btn btn-secondary btn-sm ms-1">답글</button>
				               </div>
				               <div class="col d-flex justify-content-end ">
				                 <!-- 삭제/수정 버튼-->
				                 <!--<button class="btn btn-warning btn-sm me-2">수정</button>
				                 <button class="btn btn-danger btn-sm me-2">삭제</button>-->
				               </div>
				               
				             </div>
				          </div>
				        </div>
				        <ul>
				          <li>
				            <div class="reply_node pb-1 d-flex">
				              <div class="col-1 pt-2">
				                 <div class="reply_author">
				                   <p class="text-center m-0 t-0">홍길동 :</p>
				                 </div>
				                 <div class="reply_author_me">
				                   <p class="text-center m-0 t-0">(블로그 주인)</p>
				                 </div>
				              </div>
				              <div class="col pt-2">
				                 <div class="reply_content">
				                  감사합니다.
				                 </div>
				                 <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
				                 <div class="d-flex">
				                   <div class="col">
				                       <!-- 답장 버튼-->
				                       <button class="btn btn-secondary btn-sm ms-1">답글</button>
				                   </div>
				                   <div class="col d-flex justify-content-end ">
				                     <!-- 삭제/수정 버튼-->
				                     <button class="btn btn-warning btn-sm me-2">수정</button>
				                     <button class="btn btn-danger btn-sm me-2">삭제</button>
				                   </div> 
				                 </div>
				              </div>
				            </div>
				            <ul>
				            </ul>
				          </li>
				          <li>
				            <div class="reply_node pb-1 d-flex">
				              <div class="col-1 pt-2">
				                 <div class="reply_author">
				                   <p class="text-center m-0 t-0">한만석 :</p>
				                 </div>
				                 <div class="reply_author_me">
				                   <!--<p class="text-center m-0 t-0">(블로그 주인)</p>-->
				                 </div>
				              </div>
				              <div class="col pt-2">
				                 <div class="reply_content">
				                  고생하셨습니다.
				                 </div>
				                 <div><span class="fs-6 text-body-tertiary">2024.03.19</span></div>
				                 <div class="d-flex">
				                   <div class="col">
				                       <!-- 답장 버튼-->
				                       <button class="btn btn-secondary btn-sm ms-1">답글</button>
				                   </div>
				                   <div class="col d-flex justify-content-end ">
				                     <!-- 삭제/수정 버튼-->
				                     <!--<button class="btn btn-warning btn-sm me-2">수정</button>
				                     <button class="btn btn-danger btn-sm me-2">삭제</button>-->
				                   </div> 
				                 </div>
				              </div>
				            </div>
				            <ul>
				            </ul>
				          </li>
				        </ul>
				      </li>
				    </ul>
				  </div>
				  <div class="reply_input ms-3">
				    <div class="form-floating">
				      <input type="text" class="form-control" placeholder="Leave a comment here" id="replyinput" style="width:98%;">
				           <label for="replyinput">댓글을 입력하세요</label>
				         </div>
				       </div>
				
				   </div>
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

  <!-- Template Main JS File -->
  <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/blog/base.js"></script>
  <script src="<%=request.getContextPath()%>/resources/js/blog/blog.js"></script>

  <!-- Last JS-->
  <!--<script src="<%=request.getContextPath()%>/resources/js/chatting.js"></script>-->
  <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
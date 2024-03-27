<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<!-- 사원 등록 팝업 -->
      <div class="modal fade" id="employeeAddModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
          
          <form action="registration" method="post">
            <div class="modal-header">
              <h5 class="modal-title">사원 등록</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">  
              <div class="container-fluid">
                <div class="row mb-1">
                  <div>
                    <label for="join_inputName" class="col-sm-4 col-form-label">이름</label>
                    <div class="col">
                      <input type="text" class="form-control" id="join_inputName" name="name">
                    </div>
                  </div>
                </div>
                <div class="row mb-1">
                  <div>
                    <label for="join_inputEmail" class="col-sm-4 col-form-label">이메일</label>
                    <div class="col">
                      <input type="email" class="form-control" id="join_inputEmail" name="email">
                    </div>
                  </div>
                </div>
                <hr>
                
                <%--1차반복문 사용(분류 나열) --%>
                <c:forEach var="cate" items="${cate}">
	                <div class="row mb-1">
	                  <div>
	                    <label class="col-sm-4 col-form-label">${cate.value}</label>
	                    <div class="col">
	                      <select class="form-select" aria-label="Default select example" name="${cate.code}">
	                      
	                      <%-- 2차반복문으로 속성과 분류의 코드를 비교하고 맞는것만 나열--%>
	                      	<c:forEach var="attr" items="${attr}">
	                      		<c:if test="${cate.code eq attr.code}">
	                      			<option value="${attr.otkey}">${attr.value}</option>
	                      		</c:if>
	                        </c:forEach>
	                        
	                      </select>
	                    </div>
	                  </div>
	                </div>
                </c:forEach>
                        
              </div>
             </div>
             <div class="modal-footer d-flex align-items-center justify-content-center">
               <button type="submit" class="btn btn-warning btn-lg" data-bs-dismiss="modal">사원 추가</button>
             </div>
            </form> <%--form태그 --%>
          </div>
        </div>
      </div><!-- End Basic Modal-->
  
       <!-- 사원 정보 보기 팝업-->
      <div class="modal fade" id="employeeDetailModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">사원 정보</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
  
            <div class="modal-body">
                <div class="container-fluid">
                  <div class="row">
                    <!-- 좌측 고정 정보-->
                    <div class="col-lg-6">
  
                      <div class="row mb-1 d-flex align-items-center justify-content-center">
                        <div class="card border w-50">
                          <div class="card-body profile-card p-0 pt-4 d-flex flex-column align-items-center">
                            <img src="<%=request.getContextPath()%>/resources/img/profile-img.jpg" alt="Profile" class="rounded-circle">
                            <a class="align-self-end" href="#" onclick="()=>{}"><i class="bi bi-camera"></i></a>
                          </div>
                        </div>  
                      </div>
  
                      <div class="row mb-1">
                        <div>
                          <label for="inputId" class="col-sm-4 col-form-label">아이디</label>
                          <div class="col">
                            <input type="text" class="form-control" id="info_inputId" value="UnityPugh" disabled >
                          </div>
                        </div>
                      </div>
                    
                      <div class="row mb-1">
                        <div>
                          <label for="inputName" class="col-sm-4 col-form-label">이름</label>
                          <div class="col">
                            <input type="text" class="form-control" id="info_inputName" value="Unity Pugh">
                          </div>
                        </div>
                      </div>
        
                        <div class="row mb-1">
                          <div>
                            <label for="info_inputEmail" class="col-sm-4 col-form-label">이메일</label>
                            <div class="col">
                              <input type="email" class="form-control" id="info_inputEmail" value="UnityPugh@abc.com">
                            </div>
                          </div>
                        </div>
        
                        <div class="row mb-1">
                          <div>
                            <label for="info_inputPhone" class="col-sm-4 col-form-label">전화번호</label>
                            <div class="col">
                              <input type="tel" class="form-control" id="info_inputPhone" value="010-0000-0000">
                            </div>
                          </div>
                        </div>
        
                        <div class="row mb-1">
                          <div>
                            <label for="info_inputDate" class="col-sm-4 col-form-label">가입일</label>
                            <div class="col">
                              <input type="text" class="form-control" id="info_inputDate" value="2024.03.17" disabled >
                            </div>
                          </div>
                        </div>
  
                        <div class="row mb-1">
                          <div>
                            <label class="col-sm-4 col-form-label">권한</label>
                            <div class="col">
                              <select class="form-select" aria-label="Default select example">
                                <option selected>ROLE_USER</option>
                                <option value="1">ROLE_USER</option>
                                <option value="2">ROLE_ADMIN</option>
                              </select>
                            </div>
                          </div>
                        </div>
  
                        <div class="row mb-1">
                          <div>
                            <label class="col-sm-4 col-form-label">상태</label>
                            <div class="col">
                              <select class="form-select" aria-label="Default select example">
                                <option selected>Active</option>
                                <option value="1">Active</option>
                                <option value="2">Inactive</option>
                              </select>
                            </div>
                          </div>
                        </div>
                    </div>
  
                    <!-- 우측 변동 정보-->
                    <div class="col-lg-6">
                      
                      <div class="row mb-1">
                        <div>
                          <label class="col-sm-4 col-form-label">부서</label>
                          <div class="col">
                            <select class="form-select" aria-label="Default select example">
                              <option selected>개발</option>
                              <option value="1">?</option>
                              <option value="2">?</option>
                              <option value="3">?</option>
                            </select>
                          </div>
                        </div>
                      </div>
  
                      <div class="row mb-1">
                        <div>
                          <label class="col-sm-4 col-form-label">직위</label>
                          <div class="col">
                            <select class="form-select" aria-label="Default select example">
                              <option selected>사원</option>
                              <option value="1">?</option>
                              <option value="2">?</option>
                              <option value="3">?</option>
                            </select>
                          </div>
                        </div>
                      </div>
  
                      <div class="row mb-1">
                        <div>
                          <label class="col-sm-4 col-form-label">직무</label>
                          <div class="col">
                            <select class="form-select" aria-label="Default select example">
                              <option selected>웹개발</option>
                              <option value="1">?</option>
                              <option value="2">?</option>
                              <option value="3">?</option>
                            </select>
                          </div>
                        </div>
                      </div>
  
                      <div class="row mb-1">
                        <div>
                          <label class="col-sm-4 col-form-label">직책</label>
                          <div class="col">
                            <select class="form-select" aria-label="Default select example">
                              <option selected>개발팀장</option>
                              <option value="1">?</option>
                              <option value="2">?</option>
                              <option value="3">?</option>
                            </select>
                          </div>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                </div>
            </div>
            <div class="modal-footer d-flex align-items-center justify-content-center">
              <button type="button" class="btn btn-danger btn-lg"  onclick="openMultiModal('employeeRemoveModal')">탈퇴</button>
              <button type="button" class="btn btn-warning btn-lg" onclick="openMultiModal('employeeEditModal')">수정</button>
            </div>
          </div>
        </div>
      </div><!-- End Modal Dialog Scrollable-->
  
      <!-- 탈퇴 확인 모달-->
      <div class="modal fade" id="employeeRemoveModal" tabindex="-1" data-bs-backdrop="false">
        <div class="modal-dialog modal-dialog-bottom">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">탈퇴 확인</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              정말로 탈퇴 처리 하시겠습니까?
            </div>
            <div class="modal-footer d-flex align-items-center justify-content-center">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
              <button type="button" class="btn btn-danger" data-bs-dismiss="modal" onclick="closeMultiModal(()=>{},'employeeDetailModal')">탈퇴</button>
            </div>
          </div>
        </div>
      </div><!-- End Disabled Backdrop Modal-->
  
       <!-- 수정 확인 모달-->
       <div class="modal fade" id="employeeEditModal" tabindex="-1" data-bs-backdrop="false">
        <div class="modal-dialog modal-dialog-bottom">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">수정 확인</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              정말로 정보를 수정 하시겠습니까?
            </div>
            <div class="modal-footer d-flex align-items-center justify-content-center">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
              <button type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="closeMultiModal(()=>{},'employeeDetailModal')">수정</button>
            </div>
          </div>
        </div>
      </div><!-- End Disabled Backdrop Modal-->
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

 <section class="popup">
	
	<!-- 분류 추가 팝업 -->
	<div class="modal fade" id="categoryAddModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">분류 추가</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
			<form action="<%=request.getContextPath()%>/admin/category/write" method="post">
			 <div class="modal-body">  
			   <div class="container-fluid">
			     <div class="row mb-1">
			       <div>
			         <label for="category_inputCode" class="col-sm-4 col-form-label">코드</label>
			         <div class="col">
			           <input type="text" class="form-control" id="category_inputCode" required name="code">
			         </div>
			       </div>
			     </div>
			     <div class="row mb-1">
			       <div>
			         <label for="category_inputValue" class="col-sm-4 col-form-label">값</label>
			         <div class="col">
			           <input type="text" class="form-control" id="category_inputValue" required name="value">
			         </div>
			       </div>
			     </div>
			     <div class="row mb-1">
			       <div>
			         <label class="col-sm-4 col-form-label">블로그 표시</label>
			         <div class="col">
			           <select class="form-select" aria-label="Default select example" required name="blogView">
			             <option selected value="0">FALSE</option>
			             <option value="1">TRUE</option>
			           </select>
			         </div>
			       </div>
			     </div>
			   </div>
			</div>
			<div class="modal-footer d-flex align-items-center justify-content-center">
				<button type="button" class="btn btn-secondary btn-lg" data-bs-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-warning btn-lg" data-bs-dismiss="modal">추가</button>
			</div>
			</form>
		</div>
	  </div>
	</div>
	  
	 <!-- 분류 정보 보기 팝업-->
	<div class="modal fade" id="categoryDetailModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">분류 정보</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <form action="<%=request.getContextPath()%>/admin/category/modify" method="post">
	      <div class="modal-body">
	      	  <input type="hidden" id="categoryDetail_cidx" name="cidx" value=""> 
	          <div class="container-fluid">
	            <div class="row mb-1">
	              <div>
	                <label for="categoryDetail_code" class="col-sm-4 col-form-label">코드</label>
	                <div class="col">
	                  <input type="text" class="form-control" id="categoryDetail_code" value="" name="code">
	                </div>
	              </div>
	            </div>
	            <div class="row mb-1">
	              <div>
	                <label for="categoryDetail_value" class="col-sm-4 col-form-label">값</label>
	                <div class="col">
	                  <input type="text" class="form-control" id="categoryDetail_value" value="" name="value">
	                </div>
	              </div>
	            </div>
	            <div class="row mb-1">
	              <div>
	                <label class="col-sm-4 col-form-label">블로그 표시</label>
	                <div class="col">
	                 	<select class="form-select" aria-label="Default select example" id="categoryDetail_blogView" name="blogView">
			             <option value="0">FALSE</option>
			             <option value="1">TRUE</option>
			           </select>
	                </div>
	              </div>
	            </div>
	          </div>
	      </div>
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-danger btn-lg"  onclick="openMultiModal('categoryRemoveModal')">삭제</button>
	        <button type="submit" class="btn btn-warning btn-lg" data-bs-dismiss="modal">수정</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	  
	<!-- 분류 삭제 확인 모달-->
	<div class="modal fade" id="categoryRemoveModal" tabindex="-1" data-bs-backdrop="false">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">삭제 확인</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <form action="<%=request.getContextPath()%>/admin/category/remove" method="post">
	      <input type="hidden" id="categoryRemoveModal_cidx" name="cidx" value=""> 
	      <div class="modal-body">
	        정말 삭제 하시겠습니까?
	      </div>
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="submit" class="btn btn-danger" data-bs-dismiss="modal" onclick="closeMultiModal(()=>{},'categoryDetailModal')">삭제</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
</section>
	 
</body>
</html>
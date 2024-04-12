<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<section class="popup">
	
	<!-- 속성 추가 팝업 -->
	<div class="modal fade" id="attributeAddModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">속성 추가</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
			<form action="<%=request.getContextPath()%>/admin/attribute/write" method="post">
			    <div class="modal-body">  
			      <input type="hidden" id="attributeAdd_cidx" name="cidx" value="">
			      <div class="container-fluid">
			        <div class="row mb-1">
			          <div>
			            <label for="attributeAdd_key" class="col-sm-4 col-form-label">키</label>
			            <div class="col">
			              <input type="text" class="form-control" id="attributeAdd_key" required name="otkey">
			            </div>
			          </div>
			        </div>
			        
			        <div class="row mb-1">
			          <div>
			            <label for="attributeAdd_value" class="col-sm-4 col-form-label">값</label>
			            <div class="col">
			              <input type="text" class="form-control" id="attributeAdd_value" required name="value">
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
	  
	<!-- 속성 정보 보기 팝업-->
	<div class="modal fade" id="attributeDetailModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">속성 정보</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      
	      <form action="<%=request.getContextPath()%>/admin/attribute/modify" method="post">
	      	<div class="modal-body">
		      	<input type="hidden" id="attributeDetail_cidx" name="cidx" value=""> 
				<input type="hidden" id="attributeDetail_aidx" name="aidx" value="">
	          <div class="container-fluid">
	          
	            <div class="row mb-1">
	              <div>
	                <label for="attributeDetail_key" class="col-sm-4 col-form-label">키</label>
	                <div class="col">
	                  <input type="text" class="form-control" id="attributeDetail_key" value="" name="otkey">
	                </div>
	              </div>
	            </div>
	            
	            <div class="row mb-1">
	              <div>
	                <label for="attributeDetail_value" class="col-sm-4 col-form-label">값</label>
	                <div class="col">
	                  <input type="text" class="form-control" id="attributeDetail_value" value="" name="value">
	                </div>
	              </div>
	            </div>
	          </div>
	      </div>
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-danger btn-lg"  onclick="openMultiModal('attributeRemoveModal')">삭제</button>
	        <button type="submit" class="btn btn-warning btn-lg" data-bs-dismiss="modal">수정</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	  
	<!-- 탈퇴 확인 모달-->
	<div class="modal fade" id="attributeRemoveModal" tabindex="-1" data-bs-backdrop="false">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">삭제 확인</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <form action="<%=request.getContextPath()%>/admin/attribute/remove" method="post">
				<input type="hidden" id="attributeRemove_aidx" name="aidx" value="">
				<div class="modal-body">
				 		 정말 삭제 하시겠습니까?
				</div>
				<div class="modal-footer d-flex align-items-center justify-content-center">
				  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				  <button type="submit" class="btn btn-danger" data-bs-dismiss="modal" onclick="closeMultiModal(()=>{},'attributeDetailModal')">삭제</button>
				</div>
	      </form>
	    </div>
	  </div>
	</div>
	
</section>
	
</body>
</html>
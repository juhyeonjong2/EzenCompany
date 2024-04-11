<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<section class="popup">
	 <!-- 게시판 추가 팝업 -->
	 <div class="modal fade" id="boardAddModal" tabindex="-1">
	   <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	     <div class="modal-content">
	       <div class="modal-header">
	         <h5 class="modal-title">게시판 추가</h5>
	         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	       </div>
	
	       <div class="modal-body">  
	         <div class="container-fluid">
	           <div class="row mb-1">
	             <div>
	               <label for="board_inputName" class="col-sm-4 col-form-label">게시판 이름</label>
	               <div class="col">
	                 <input type="text" class="form-control" id="board_inputName">
	               </div>
	             </div>
	           </div>
	           <div class="row mb-1">
	             <div>
	               <div class="d-flex justify-content-between">
	                 <label for="board_reader" class="col-sm-4 col-form-label">읽기 권한</label>
	                 <a class="link-dark pt-2 ms-1" href="#" onclick="openMultiModal('boardAddCategoryModal', { 'data-bs-tree' : 'tree_reader'}); return false;">
	                   <i class="bi bi-plus-square "></i>
	                 </a>                      
	               </div>
	               <div class="col border authority_frame overflow-y-auto">
	                 <ul id="tree_reader" class="ztree"></ul>
	               </div>
	             </div>
	           </div>
	           <div class="row mb-1">
	             <div>
	               <div class="d-flex justify-content-between">
	                 <label for="board_reader" class="col-sm-4 col-form-label">쓰기 권한</label>
	                 <a class="link-dark pt-2 ms-1" href="#" onclick="openMultiModal('boardAddCategoryModal', { 'data-bs-tree' : 'tree_writer'}); return false;">
	                   <i class="bi bi-plus-square "></i>
	                 </a>   
	               </div>
	               <div class="col border authority_frame overflow-y-auto">
	                 <ul id="tree_writer" class="ztree"></ul>
	               </div>
	             </div>
	           </div>
	           
	         </div>
	       </div>
	       <div class="modal-footer d-flex align-items-center justify-content-center">
	         <button type="button" class="btn btn-warning btn-lg" data-bs-dismiss="modal" onclick="insertBoard()">추가</button>
	       </div>
	     </div>
	   </div>
	 </div>
	 
	 <!-- 게시판 정보 보기 팝업-->
	<div class="modal fade" id="boardEditModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">게시판 정보</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	
	      <div class="modal-body">
	          <div class="container-fluid">
	            <div class="row mb-1">
	              <div>
	                <label for="board_inputEditName" class="col-sm-4 col-form-label">게시판 이름</label>
	                <div class="col">
	                  <input type="text" class="form-control" id="board_inputEditName">
	                </div>
	              </div>
	            </div>
	            <div class="row mb-1">
	              <div>
	                <div class="d-flex justify-content-between">
	                  <label for="board_reader" class="col-sm-4 col-form-label">읽기 권한</label>
	                  <a class="link-dark pt-2 ms-1" href="#" onclick="openMultiModal('boardAddCategoryModal', { 'data-bs-tree' : 'tree_edit_reader'}); return false;">
	                    <i class="bi bi-plus-square "></i>
	                  </a>                      
	                </div>
	                <div class="col border authority_frame overflow-y-auto">
	                  <ul id="tree_edit_reader" class="ztree"></ul>
	                </div>
	              </div>
	            </div>
	            <div class="row mb-1">
	              <div>
	                <div class="d-flex justify-content-between">
	                  <label for="board_reader" class="col-sm-4 col-form-label">쓰기 권한</label>
	                  <a class="link-dark pt-2 ms-1" href="#" onclick="openMultiModal('boardAddCategoryModal', { 'data-bs-tree' : 'tree_edit_writer'}); return false;">
	                    <i class="bi bi-plus-square "></i>
	                  </a>   
	                </div>
	                <div class="col border authority_frame overflow-y-auto">
	                  <ul id="tree_edit_writer" class="ztree"></ul>
	                </div>
	              </div>
	            </div>
	          </div>
	      </div>
	
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-danger btn-lg"  onclick="openMultiModal('boardRemoveModal')">삭제</button>
	        <button type="button" class="btn btn-warning btn-lg" data-bs-dismiss="modal" onclick="editBoard()">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
	 
	<!-- 게시판 삭제 확인 모달-->
	<div class="modal fade" id="boardRemoveModal" tabindex="-1" data-bs-backdrop="false">
	  <div class="modal-dialog modal-dialog-bottom">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">삭제 확인</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        정말 삭제 하시겠습니까?
	      </div>
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" onclick="closeMultiModal(()=>{},'boardEditModal')">삭제</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- 분류 추가 팝업 -->
	<div class="modal fade" id="boardAddCategoryModal" tabindex="-1" data-bs-backdrop="false">
	  <div class="modal-dialog modal-dialog-centered modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">분류 추가</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <input type="hidden" class="tree_name_input" value="">
	        <div class="container-fluid">
	        </div>
	      </div>
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="insertCategory(this)">확인</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- 속성 추가 팝업-->
	<div class="modal fade" id="boardAddAttributeModal" tabindex="-1" data-bs-backdrop="false">
	  <div class="modal-dialog modal-dialog-centered modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">속성 추가</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <input type="hidden" class="tree_name_input" value="">
	        <input type="hidden" class="parent_tree_id_input" value="">
	        <input type="hidden" class="category_code_input" value="">
	        <div class="container-fluid">
	        </div>
	      </div>
	      <div class="modal-footer d-flex align-items-center justify-content-center">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="insertAttribute(this)">확인</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</section>

</body>
</html>
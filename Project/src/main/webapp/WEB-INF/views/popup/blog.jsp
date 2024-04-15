<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<section class="popup">
	<!-- 블로그 삭제 팝업-->
      <div class="modal fade" id="blogRemoveModal" tabindex="-1" data-bs-backdrop="false">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title"></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="<%=request.getContextPath()%>/blog/remove" method="post">
            	<input type="hidden" id="blogRemove_bgno" name="bgno" value="">            
				<div class="modal-body">
				  현재 블로그를 삭제하시겠습니까?
				</div>
				<div class="modal-footer d-flex align-items-center justify-content-center">
				  <button type="submit" class="btn btn-danger" data-bs-dismiss="modal">삭제</button>
				  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				</div>
			</form>
          </div>
        </div>
      </div><!-- End Disabled Backdrop Modal-->
</section>

</body>
</html>
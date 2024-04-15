<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
    <section class="popup">
      <!-- 폴더 추가 팝업 -->
      <div class="modal fade" id="folderAddModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">폴더 추가</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">  
              <div class="container-fluid">
                <div class="row mb-1">
                  <div>
                    <label class="col-sm-4 col-form-label">부모 폴더</label>
                    <div class="col">
                      <select class="form-select" aria-label="Default select example" id="folderAdd_parent">
                        <option selected value="0">없음</option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="row mb-1">
                  <div>
                    <label for="folder_inputName" class="col-sm-4 col-form-label">폴더</label>
                    <div class="col">
                      <input type="text" class="form-control" id="folder_inputName" name="folder">
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
            <div class="modal-footer d-flex align-items-center justify-content-center">
              <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onClick="insertFolder()">추가</button>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>
          </div>
        </div>
      </div><!-- End Basic Modal-->
    </section>
</body>
</html>
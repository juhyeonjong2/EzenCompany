<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

 <!-- Vendor CSS Files --> <!-- request.get이거 적는이유 -> 이게 없으면 컨트롤러간 이동할 경우 상대경로라서 경로를 못찾게 된다(/member/resources이런느낌인듯) -->
 <link href="<%=request.getContextPath()%>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/remixicon/remixicon.css" rel="stylesheet">
 <link href="<%=request.getContextPath()%>/resources/vendor/simple-datatables/style.css" rel="stylesheet">
 
  <!-- Template Main CSS File -->
  <link href="<%=request.getContextPath()%>/resources/css/style_admin.css" rel="stylesheet">
  <script src="<%=request.getContextPath()%>/resources/js/jquery-3.7.1.min.js"></script>
  <script>
	//썸네일 생성 함수
	function thumbnail(event,obj){
	  const file = event.target.files[0];
	  const img = $(obj).prev().prev();
	  const reader = new FileReader();
	  reader.onload = (e) => {
	    	$(img).attr("src", e.target?.result );
	  };
	  reader.readAsDataURL(file);
	}
  </script>
</head>
<body>
	<%@ include file="../include/adminHeader.jsp"%>
	<%@ include file="../include/adminSidebar.jsp"%>

  <main id="main" class="main">
      
    <section class="section">
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center">
                  <h5 class="card-title"><b>사원 목록</b></h5>
                  <button type="button" class="btn btn-primary h-50" data-bs-toggle="modal" data-bs-target="#employeeAddModal" >+ 사원 등록</button>
                  <!--<p>Add lightweight datatables to your project with using the <a href="https://github.com/fiduswriter/Simple-DataTables" target="_blank">Simple DataTables</a> library. Just add <code>.datatable</code> class name to any table you wish to conver to a datatable. Check for <a href="https://fiduswriter.github.io/simple-datatables/demos/" target="_blank">more examples</a>.</p>-->
              </div>

              <!-- Table with stripped rows -->
              <table class="table datatable">
                <thead>
                  <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>권한</th>
                    <th>상태</th>
                    <th data-type="date" data-format="YYYY/DD/MM">Start Date</th>
                    <th>&nbsp;</th>
                  </tr>
                </thead>
                <tbody>
                <c:forEach var="vo" items="${list}">
                  <tr>
                  	  <c:choose>
                  		<%--아이디가 null이거나 빈문자열일 경우 --%>
                  		<c:when test="${vo.mid eq null || vo.mid == ''}">
                  			<td style="color:#4154f1" onclick="aa()"><i class="bx bx-mail-send"> 재전송</td>
                  		</c:when>
                  		
                  		<%--아이디가 null이아니면서 빈문자열이 아닌경우 --%>
                  		<c:when test="${vo.mid ne null && vo.mid ne ''}">
                  			<td>${vo.mid}</td>
                  		</c:when>
                  	  </c:choose>
                  	   
                      <td>${vo.mname}</td>
                      <td>${vo.email}</td>
                      <td>${vo.authority}</td>

                      <c:choose>
                      	<%--아이디가 null이거나 빈문자열이거나 회원탈퇴한 경우(enabled가 1이 아닌경우) --%>
                  		<c:when test="${vo.mid eq null || vo.mid == '' || vo.enabled ne 1}">
                  			<td><img src="<%=request.getContextPath()%>/resources/icon/inactive.png" alt="inactive"></td>
                  		</c:when>
                  		
                  		<%--아이디가 null이아니면서 빈문자열이 아니면서 회원탈퇴한 경우가 아닌경우 --%>
                  		<c:when test="${vo.mid ne null && vo.mid ne '' && vo.enabled eq 1}">
                  			<td><img src="<%=request.getContextPath()%>/resources/icon/active.png" alt="active"></td> 
                  		</c:when>
                      </c:choose>
                      
                      <td>${vo.joindate}</td>
                      <td>
                        <a class="link-dark" href="#" onclick="return false;" data-bs-toggle="modal" data-bs-target="#employeeDetailModal" data-bs-email="${vo.email}">
                          <i class="bi bi-three-dots-vertical"></i>
                        </a>
                      </td>
                   </tr>
                  </c:forEach>
				<!-- tr을 반복문 사용하면 됨 -->
                  
                </tbody>
              </table>
              <!-- End Table with stripped rows -->

            </div>
          </div>

        </div>
      </div>
    </section>

    <section class="popup">
		  <%@ include file="../popup/employeeRegistration.jsp"%>
	</section>

  </main><!-- End #main -->

  <!-- Last -->
  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="<%=request.getContextPath()%>/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/vendor/simple-datatables/simple-datatables.js"></script>

  <!-- Template Main JS File -->
  <script src="<%=request.getContextPath()%>/resources/js/main.js"></script>

  <!-- 가독성이 안좋아서 일단 여기서 작성후 잘 된다면 js파일로 이동예정 -->
  <script>
  (			
		    function() {
		    "use strict";
		   /**
		   * Easy selector helper function
		   */
		   const select = (el, all = false) => {
		    el = el.trim()
		    if (all) {
		      return [...document.querySelectorAll(el)]
		    } else {
		      return document.querySelector(el)
		    }
		  }

		    /**
		     * Initiate Datatables
		     */
		    const datatables = select('.datatable', true);
		    datatables.forEach(datatable => {
		      new simpleDatatables.DataTable(datatable, {
		        perPageSelect: [5, 10, 15, ["All", -1]],
		        labels:{
		          info: "총 {rows}명"
		        },
		        columns: [{
		            select: 2,
		            sortSequence: ["desc", "asc"]
		          },
		          {
		            select: 3,
		            sortSequence: ["desc"]
		          },
		          {
		            select: 4,
		            cellClass: "green",
		            headerClass: "red"
		          }
		        ]
		      });
		    })

		    // 2. 회원 디테일 정보 팝업
		    const employeeDetailModal = document.getElementById('employeeDetailModal')
		    if (employeeDetailModal) {
		        employeeDetailModal.addEventListener('show.bs.modal', event => 
		        {
		            const button = event.relatedTarget;
		            //클릭한 사람의 이메일을 가져옴
		            const email = button.getAttribute('data-bs-email');
		            //vo안에 이사람의 정보를 전부 가져와서 뿌려준다
		            $.ajax({
		            	url:"<%=request.getContextPath()%>/admin/getMember",
		            	data:{email : email},
		            	async: false,
						success:function(member){
						 $("#info_inputId").attr("value", member.mid);
						 $("#info_inputName").attr("value", member.mname);
						 $("#info_inputEmail").attr("value", member.email);
						 $("#info_inputPhone").attr("value", member.mphone);
						 $("#info_inputDate").attr("value", member.joindate);
						 if(member.authority == "ROLE_ADMIN"){
							 $("#info_authority").val("2").prop("selected",true);
						 }else{
							 $("#info_authority").val("1").prop("selected",true);
						 }
						 if(member.enabled == 0 || member.mid == null || member.mid == ""){
							 $("#info_enabled").val("2").prop("selected",true);
						 }else{
							 $("#info_enabled").val("1").prop("selected",true);
						 }
						 //추가로 이미지도 받아와서 그거 넣어야함
						} //success
		            }); //ajax파트
		            
		        }); //모달 파트    
		    }
		    
			
		  })();
  </script>







  <!-- Last JS-->
  <script src="<%=request.getContextPath()%>/resources/js/tooltips.js"></script>

</body>
</html>
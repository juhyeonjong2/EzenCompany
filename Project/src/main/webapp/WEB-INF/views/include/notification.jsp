<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<li class="nav-item dropdown" >
          <a class="nav-link nav-icon notiNoti" href="#" data-bs-toggle="dropdown" id="notifications">
            <i class="bi bi-bell"></i>
          </a><!-- End Notification Icon -->

          <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications">
            <li class="dropdown-header" style="width: 200px;">
              알림
            </li>

<!-- 한덩이 -->
		<div id="notiBox">
		</div>
<!-- 한덩이 -->

          </ul><!-- End Notification Dropdown Items -->
        </li><!-- End Notification Nav -->
        
<script>
	//화면 열리자 마자 알림이 존재하는지 확인
	$(document).ready(function(){
		$.ajax({
         	url: "<%=request.getContextPath()%>/notification/firstNoti",
         	success:function(list){	
         			$("#replyBt").remove();
         			if(list != null && list.length != 0){
	            		let noti = "<span class='badge bg-primary badge-number' id='replyBt'>!</span>";
	            		$(".notiNoti").append(noti);
         			}
         	}
		});//ajax
	});


	const myDropdown = document.getElementById('notifications')
	myDropdown.addEventListener('show.bs.dropdown', function () {
		
		 $.ajax({
         	url: "<%=request.getContextPath()%>/notification/getNoti",
         	async: false,
         	success:function(list){
         		//재생성 방지를 위해 실행될때마다 비워줌	
			        $('#notiBox').empty();
			        $("#replyBox").remove();
         			if(list == null || list.length == 0){
         				//가져올 값이 없다면
         				$("#replyBt").remove();
         			}
					for(let i = 0; i<list.length; i++){
						if(list[i].code == "bg"){
							let html = '<li><hr class="dropdown-divider"></li>'
								 + '<li class="notification-item">'
								 + '<i class="bi bi-exclamation-circle text-warning"></i>'
								 + '<div><p>'
								 + list[i].myName
								 + '님이 블로그에 댓글을 남기셨습니다.</p>'
								 + '</div></li>'
								 $('#notiBox').append(html);
						}else if(list[i].code == "bo"){
							let html = '<li><hr class="dropdown-divider"></li>'
								 + '<li class="notification-item">'
								 + '<i class="bi bi-exclamation-circle text-warning"></i>'
								 + '<div><p>'
								 + list[i].myName
								 + '님이 게시글에 댓글을 남기셨습니다.</p>'
								 + '</div></li>'
								 $('#notiBox').append(html);
						}else{
							let html = '<li><hr class="dropdown-divider"></li>'
								 + '<li class="notification-item">'
								 + '<i class="bi bi-exclamation-circle text-warning"></i>'
								 + '<div><p>'
								 + list[i].myName
								 + '님이 채팅을 남기셨습니다.</p>'
								 + '</div></li>'
								 $('#notiBox').append(html);
						}
					}
         	} //success
		 }) //ajax
		 
		//닫기 전 한번 더 확인해서 알림이 존재하는지 확인
		$.ajax({
         	url: "<%=request.getContextPath()%>/notification/firstNoti",
         	async: false,
         	success:function(list){	
         			$("#replyBt").remove();
         			if(list != null && list.length != 0){
	            		let noti = "<span class='badge bg-primary badge-number' id='replyBt'>!</span>";
	            		$(".notiNoti").append(noti);
         			}
         	}
		});//ajax
		 
		 
	})//드롭다운
</script>
</body>
</html>
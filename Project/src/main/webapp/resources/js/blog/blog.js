(
    function() 
    {
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
     * Initiate Modals
     */
	  // 삭제 팝업
     const blogRemoveModal = document.getElementById('blogRemoveModal')
     if (blogRemoveModal) 
     {
        blogRemoveModal.addEventListener('show.bs.modal', event => 
        {
             let button = event.relatedTarget;             
             let bgno = button.getAttribute('data-bs-bgno');
             $("#blogRemove_bgno").val(bgno);
    	});
	 }


  })();
  
  
  
/*--------------------------------------------------------------
# Init
--------------------------------------------------------------*/

$(document).ready(function(){

	let replyConfig = {
		create : "/blog/reply/write",
		read :   "/blog/reply/list",
		update : "/blog/reply/modify",
		delete : "/blog/reply/remove",
		writer : "(블로그 주인)",
		parser : function (vo) {
			// 데이터 만들기
			return {
						rno : vo.bgrno,
						prno : vo.bgrpno,
						author: vo.author,
						content :vo.bgrcontent,
						date : vo.bgrdate,
						isEditable : vo.editable,
						isMaster : vo.master,
						isDeleted : vo.delyn =='n' ? false : true 
					};
		} ,
		noti : function (mno, mid){
		console.log(222);
			$.ajax({
	        	url: "/notification/blogNoti",
	        	data: {targetMno : mno},
	        	success:function(data){
	        	console.log(111);
	        		socket.send("블로그댓글,"+mid+",블로그댓글,"+data);
	        	}
			});
		}
	};
	
	ezReply_init(replyConfig, true);
});
  
 

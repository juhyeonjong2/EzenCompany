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
		create : "/ezencompany/blog/reply/write",
		read :   "/ezencompany/blog/reply/list",
		update : "/ezencompany/blog/reply/modify",
		delete : "/ezencompany/blog/reply/remove",
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
		} 
	};
	
	ezReply_init(replyConfig, true);
});
  
 

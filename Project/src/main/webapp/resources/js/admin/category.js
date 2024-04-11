/**
* Template Name: NiceAdmin
* Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
* Updated: Mar 17 2024 with Bootstrap v5.3.3
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/

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
     * Initiate Datatables
     */
     const datatables = select('.datatable', true);
     datatables.forEach(datatable => {new simpleDatatables.DataTable(datatable, { perPageSelect: [5, 10, 15, ["All", -1]], });})
    

    /**
     * Initiate Modals
     */
	  // 분류 수정 팝업 (category 넘겨받기)
     const categoryDetailModal = document.getElementById('categoryDetailModal')
     if (categoryDetailModal) 
     {
        categoryDetailModal.addEventListener('show.bs.modal', event => 
        {
             const button = event.relatedTarget;             
             const category = button.getAttribute('data-bs-category');
             
             $("#categoryDetail_cidx").val(category);
             
             $.ajax(
			{
				url: "/ezencompany/admin/category/info",
				type: "get",
				async:false,
				data : {cidx:category},
				success: function(resData) {
					if(resData != null)
					{
						$("#categoryDetail_cidx").val(resData.cidx);
						$("#categoryDetail_code").val(resData.code);
						$("#categoryDetail_value").val(resData.value);
						$("#categoryDetail_blogView").val(resData.blogView);
					}
				},
				error: function(error) {
		      		alert(error);
		    	}
			});
		
    	});
	 }
	 
	  // 분류 삭제 팝업 (category 찾기)
     const categoryRemoveModal = document.getElementById('categoryRemoveModal')
     if (categoryRemoveModal) 
     {
        categoryRemoveModal.addEventListener('show.bs.modal', event => 
        { 
             // 상위 팝업에 있는 데이터 들고오기.
             let category = $("#categoryDetail_cidx").val();
             $("#categoryRemoveModal_cidx").val(category);
		
    	});
	 }

     
  })();
  
  